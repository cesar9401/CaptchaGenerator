/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cesar31.function;

/**
 *
 * @author cesar31
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(getPrim(123));
    }

    public static int getPrim(int n) {
        int count = 0;
        while (count != 2) {
            int divisor = 1;
            count = 0;
            n++;
            while (divisor <= n) {
                if (n % divisor == 0) {
                    count++;
                }
                divisor++;
            }
        }

        return n;
    }
}
