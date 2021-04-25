package com.cesar31.captchaweb.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author cesar31
 */
public class DBHandler {

    public String readData(String path) {
        String data = "";
        File file = new File(path);
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = br.readLine();
                while (line != null) {
                    data += line + "\n";
                    line = br.readLine();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.out);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return data;
    }
}
