/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.Scanner;

/**
 *
 * @author musang
 */
public class input {
    public static int askforInt(String frase) {
        Scanner teclat = new Scanner(System.in);

        int num = 0;
        boolean llegit = false;

        do {
            System.out.print(frase);
            if (teclat.hasNextInt()) {
                num = teclat.nextInt();
                llegit = true;
            } else {
                System.out.println("Error");
                teclat.nextLine();
            }
        } while (!llegit);

        return num;
    }
    public static int askforInt(String frase,int min , int max) {
        Scanner teclat = new Scanner(System.in);

        int num = 0;
        boolean llegit = false;

        do {
            System.out.print(frase);
            if (teclat.hasNextInt()) {
                num = teclat.nextInt();
                if (num >= min && num <= max) {
                    llegit = true;
                }else{
                    System.out.printf("The value is not between %d and %d: \n", min,max);
                }
            } else {
                System.out.println("Error");
                teclat.nextLine();
            }
        } while (!llegit);

        return num;
    }
    
}
