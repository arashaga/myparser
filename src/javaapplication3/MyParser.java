/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    String str;
    Document doc;
    final static String pattern = "1?\\s*\\W?\\s*([2-9][0-8][0-9])\\s*\\W?\\s*"
            + "([2-9][0-9]{2})\\s*\\W?\\s*([0-9]{4})(\\se?x?t?(\\d*))?";
//    final static String pattern = "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]"
//            + "|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|"
//            + "[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9]"
//            + "[02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";

    MyParser(String url) {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ex) {
            Logger.getLogger(MyParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        str = doc.text();
    }

    /**
     * We use HasSet here to insert unique items
     *
     * @return
     */
    public HashSet<String> getPhoneNumbers() {

        HashSet<String> found = new HashSet<>();
        Pattern pat = Pattern.compile(pattern);
        Matcher matcher = pat.matcher(str);

        while (matcher.find()) {
//            found.add(matcher.group(1));
//             found.add(matcher.group(2));
//              found.add(matcher.group(3));
            found.add(matcher.group(0));

        }
        return found;
    }

    public String getStr() {

        return str;
    }

    public void createExcel() throws FileNotFoundException, IOException {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("Contacts");
        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        wb.write(fileOut);
        fileOut.close();
    }

    public static void main(String[] args) {
        MyParser myP = new MyParser("http://www.nsaconstruction.com/contact.html");
        int count = 0;
        System.out.println(myP.getStr());

        Workbook wb = new HSSFWorkbook();
        Sheet sheet1 = wb.createSheet("Contacts");
        try (FileOutputStream fileOut = new FileOutputStream("workbook.xls")) {
            wb.write(fileOut);
            
            // Create a cell and put a value in it.
            if (myP.getPhoneNumbers() != null) {
                for (String s : myP.getPhoneNumbers()) {
Row row = sheet1.createRow((short) count);
                    Cell cell = row.createCell(count);
                    cell.setCellValue(s);
                    count++;
                    System.out.println(s);
                }
            }

            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        if (myP.getPhoneNumbers() != null) {
            for (String s : myP.getPhoneNumbers()) {

                System.out.println(s);
            }
        }

        System.out.println("\nNo Phone Numbers Found!");
    }
}
