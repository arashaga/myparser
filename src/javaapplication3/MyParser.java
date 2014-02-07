/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author arash
 */
public class MyParser {

    ArrayDeque<String> aDeque;
    private String fileName;
    // String str;
    Document doc;
    final static String pattern = "1?\\s*\\W?\\s*([2-9][0-8][0-9])\\s*\\W?\\s*"
            + "([2-9][0-9]{2})\\s*\\W?\\s*([0-9]{4})(\\se?x?t?(\\d*))?";
//    final static String pattern = "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]"
//            + "|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|"
//            + "[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9]"
//            + "[02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";

    MyParser(String fileName) {

        ArrayDeque<String> aDeque = new ArrayDeque<String>();
        this.fileName = fileName;
    }

    public HashSet<String> extractPhoneNumbers() throws IOException {
        HashSet<String> urls = new HashSet();
        HashSet<String> found = new HashSet();
        Pattern pat = Pattern.compile(pattern);
        urls = readURLFromFile(this.fileName);
        for (String url : urls) {
            try {
                doc = Jsoup.connect(url).get();
                Matcher matcher = pat.matcher(doc.text());
                // aDeque.add(doc.text());
                while (matcher.find()) {
//            found.add(matcher.group(1));
//             found.add(matcher.group(2));
//              found.add(matcher.group(3));
                    found.add(matcher.group(0));

                }
            } catch (IOException ex) {
                Logger.getLogger(MyParser.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return found;
    }

    private HashSet<String> readURLFromFile(String fileName) throws FileNotFoundException, IOException {

        HashSet<String> hsString = new HashSet();

        int lineNumber = 1;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        for (String line; (line = br.readLine()) != null;) {

            if (urlIsMatched(line)) {
                hsString.add(line);
            } else {
                hsString.add("URL in line (" + lineNumber + ") is not well formatted");
            }
            lineNumber++;
        }

        return hsString;
    }

    private boolean urlIsMatched(String s) {
        String regex = "((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\"
                + "w]+@)?[A-Za-z0-9.-]+(:[0-9]+)?|(?:ww‌​w.|[-;:&=\\+\\$,\\"
                + "w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:"
                + "[-\\+=&;%@.\\w_]*)#?‌​(?:[\\w]*))?)";
        String text = s;

        try {
            Pattern patt = Pattern.compile(regex);
            Matcher matcher = patt.matcher(text);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;

        }
    }


//    public void createExcel() throws FileNotFoundException, IOException {
//        Workbook wb = new HSSFWorkbook();
//        Sheet sheet1 = wb.createSheet("Contacts");
//        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
//        wb.write(fileOut);
//        fileOut.close();
//    }

    public static void main(String[] args) {

        String fileName = "urls.txt";
        MyParser myP = new MyParser(fileName);
        short count = 0;

        Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("Contacts");
        Cell cell = null;
        // CreationHelper createHelper = wb.getCreationHelper();
        try (FileOutputStream fileOut = new FileOutputStream("workbook.xls")) {

            // Create a cell and put a value in it.
            if (myP.extractPhoneNumbers() != null) {
                for (String s : myP.extractPhoneNumbers()) {
                    Row row = sheet1.createRow(count);
                    cell = row.createCell(1, Cell.CELL_TYPE_STRING);
                    cell.setCellValue(s);
                    count++;
                    System.out.println(s);
                }
            }
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
