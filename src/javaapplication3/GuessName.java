/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication3;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author arash
 */
public class GuessName
{

    

 //   Scanner scan = new Scanner(System.in);
    String[] name = new String[10];
    int x,length;
    char guess1,guess2,guess3;

    public GuessName()
    {
        name[0] = "MARK";
        name[1] = "CHARLIE";
        name[2] = "MEG";
        name[3] = "KYLE";
        name[4] = "JUSTIN";
        name[5] = "KATARINA";
        name[6] = "JOEL";
        name[7] = "KEVIN";
        name[8] = "MICHAEL";
        name[9] = "JENNA";
       // name[10] = "GREG";
    }

    public void start()
    {
        Random random = new Random();
        this.x = random.nextInt(10);
        this.length = name[this.x].length();
    }
    public static void main(String[] args) {
        GuessName gn = new GuessName();
        
        gn.start();
        
        System.out.println("The name is: "+gn.name[gn.x]+" and the length is: "+ gn.x);
    }
}