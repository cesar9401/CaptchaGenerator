package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.AST;
import com.cesar31.captchaweb.model.Captcha;
import com.cesar31.captchaweb.parser.CaptchaLex;
import com.cesar31.captchaweb.parser.CaptchaParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class DBHandler {

    private Captcha captcha;
    private HashMap<String, AST> scripts;
    
    public DBHandler() {
        this.scripts = new HashMap<>();
    }

    /**
     * Leer de la base de datos
     *
     * @param path
     * @return
     */
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

    /**
     * Escribir en base de datos
     *
     * @param path
     * @param source
     */
    public void writeFile(String path, String source) {
        File file = new File(path);
        try {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.write(source);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public Captcha getCaptcha(String path, String name) {
        path += "script/";
        
        if(getList(path).contains(name)) {
            System.out.println("Contiene " + name);
            String data = readData(path + name);
            parseFileGcic(data);
            
            return this.captcha;
        } else {
            System.out.println("No contiene " + name);
        }
        
        return null;
    }
    
    private void parseFileGcic(String data) {
        CaptchaLex lex = new CaptchaLex(new StringReader(data));
        CaptchaParser parser = new CaptchaParser(lex);
        try {
            this.captcha = (Captcha) parser.parse().value;
            this.scripts = parser.getScripts();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public List<String> getList(String path) {
        List<String> list = new ArrayList<>();
        File file = new File(path);
        list.addAll(Arrays.asList(file.list()));

        return list;
    }
    
    public static void main(String[] args) {
        DBHandler db = new DBHandler();
        List<String> list = db.getList("/home/cesar31/Java/CaptchaGenerator/src/main/webapp/resources/db/script/");
        list.forEach(s -> {
            System.out.println(s);
        });
    }
}
