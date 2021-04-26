package com.cesar31.captchaweb;

import java.util.Scanner;

/**
 *
 * @author cesar31
 */
public class Test {

    public static void main(String[] args) {
        String input = "";
        Scanner s = new Scanner(System.in);
        
//        while (true) {
//            System.out.println("Ingrese un palabra: ");
//            input = s.next();
//
//            if (!input.equals("./exit")) {
//                //getGram(input);
//                getInit(input);
//            } else {
//                break;
//            }
//        }
    }

    public static void getInit(String input) {
        System.out.printf("SMALLER %s params GREATER\n", input);
        System.out.printf("| error %s params GREATER\n", input);
        System.out.printf("| SMALLER error params GREATER\n");
        System.out.printf("| SMALLER %s params error\n", input);
        System.out.printf(";\n\n\n");
        System.out.println("------------------------------------\n\n\n");

    }

    public static void getGram(String input) {
        System.out.printf("SMALLER DIVIDE %s GREATER\n", input);
        System.out.printf("| error DIVIDE %s GREATER\n", input);
        System.out.printf("| SMALLER error %s GREATER\n", input);
        System.out.printf("| SMALLER DIVIDE error GREATER\n");
        System.out.printf("| SMALLER DIVIDE %s error\n", input);
        System.out.printf(";\n\n\n");
        System.out.println("------------------------------------\n\n\n");
    }
}
