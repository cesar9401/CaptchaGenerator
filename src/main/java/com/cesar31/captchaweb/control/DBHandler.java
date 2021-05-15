package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.AST;
import com.cesar31.captchaweb.model.Captcha;
import com.cesar31.captchaweb.model.Component;
import com.cesar31.captchaweb.model.Exit;
import com.cesar31.captchaweb.model.Instruction;
import com.cesar31.captchaweb.model.Param;
import com.cesar31.captchaweb.model.SymbolTable;
import com.cesar31.captchaweb.model.Tag;
import com.cesar31.captchaweb.model.Variable;
import com.cesar31.captchaweb.parser.CaptchaLex;
import com.cesar31.captchaweb.parser.CaptchaParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cesar31
 */
public class DBHandler {

    private Captcha captcha;
    private HashMap<String, AST> scripts;
    private HashMap<Integer, AST> onload;
    private boolean redirect;

    /* Inserts and Alerts */
    private List<String> inserts;
    private List<String> alerts;

    public DBHandler() {
        this.scripts = new HashMap<>();
        this.onload = new HashMap<>();

        this.redirect = false;
        this.inserts = new ArrayList<>();
        this.alerts = new ArrayList<>();
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
        /* entrar a carpeta script */
        path += "script/";

        if (getList(path).contains(name)) {
            // System.out.println("Contiene " + name);
            String data = readData(path + name);
            parseFileGcic(data);

            return this.captcha;
        }

        return null;
    }

    private void parseFileGcic(String data) {
        CaptchaLex lex = new CaptchaLex(new StringReader(data));
        CaptchaParser parser = new CaptchaParser(lex);
        try {
            this.captcha = (Captcha) parser.parse().value;
            this.scripts = parser.getScripts();
            this.onload = parser.getOnloadScripts();
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

    /**
     * Ejecutar proceso segun nombre
     *
     * @param id
     * @param process
     * @param request
     * @param response
     */
    public void executeScript(String id, String process, HttpServletRequest request, HttpServletResponse response) {
        AST ast = this.scripts.get(process);

        if (ast != null) {
            AstOperation operation = new AstOperation();
            operation.setRequest(request);
            operation.setResponse(response);
            operation.getScope().push(process);

            operation.getMain().setProcess(process);
            operation.getMain().setCaptcha(id);

            SymbolTable table = new SymbolTable(id, process);

            for (Instruction i : ast.getInstructions()) {
                Object o = i.run(table, operation);

                if (o != null) {
                    if (o instanceof Exit) {
                        break;
                    }
                }
            }

            /* Si es global, guardar variable en sesion  */
            table.forEach(v -> {
                if (v.isGlobal()) {
                    request.getSession().setAttribute(process + " - " + v.getId(), v);
                }
            });

            // recuperar y guardar tabla para comparar ejecucion
            checkTable(process, operation.getMain(), request, response);

            System.out.println("\nGlobal");
            operation.getMain().forEach(v -> {
                System.out.println(v.toString());
            });

            if (!operation.getInserts().isEmpty()) {
                this.inserts.addAll(operation.getInserts());
                // request.setAttribute("inserts", operation.getInserts());
            }

            if (!operation.getAlerts().isEmpty()) {
                this.alerts.addAll(operation.getAlerts());
                // request.setAttribute("alerts", operation.getAlerts());
            }

            if (operation.isRedirect()) {
                this.redirect = true;
            }
        } else {
            System.out.println("No existe: " + process);
        }
    }

    /**
     * Ejecutar proceso onload
     *
     * @param id
     * @param request
     * @param response
     */
    public void executeOnLoad(String id, HttpServletRequest request, HttpServletResponse response) {
        if (this.onload != null) {
            if (!this.onload.isEmpty()) {

                this.onload.forEach((iterator, ast) -> {
                    AstOperation operation = new AstOperation();

                    /* No es necesario, pero para evitar algun posible error */
                    operation.setRequest(request);
                    operation.setResponse(response);
                    operation.getScope().push("ON_LOAD");

                    SymbolTable table = new SymbolTable(id, "ON_LOAD");

                    for (Instruction i : ast.getInstructions()) {
                        Object o = i.run(table, operation);

                        if (o != null) {
                            if (o instanceof Exit) {
                                break;
                            }
                        }
                    }

                    if (!operation.getInserts().isEmpty()) {
                        this.inserts.addAll(operation.getInserts());
                        // request.setAttribute("inserts", operation.getInserts());
                    }

                    if (!operation.getAlerts().isEmpty()) {
                        this.alerts.addAll(operation.getAlerts());
                        // request.setAttribute("alerts", operation.getAlerts());
                    }

                    if (operation.isRedirect()) {
                        /* Redirigir */
                        this.redirect = true;
                    }
                });
            }
        }
    }

    private void checkTable(String process, SymbolTable current, HttpServletRequest request, HttpServletResponse response) {
        SymbolTable previous = (SymbolTable) request.getSession().getAttribute(process);
        if (previous != null) {
            for (Variable v : current) {
                for (Variable w : previous) {
                    if (v.getId().equals(w.getId())) {
                        v.setTried(v.getTried() + 1);
                    }
                }
            }
        }

        request.setAttribute("process", current);
        request.getSession().setAttribute("name", process);
        request.getSession().setAttribute(process, current);
    }

    /**
     * Actualizar archivo con informacion captcha
     *
     * @param id
     * @param path
     * @param success
     */
    public void updateCaptcha(String id, String path, boolean success) {
        ObjectMapper mapper = new ObjectMapper();
        Captcha c = null;
        try {
            c = mapper.readValue(new File(path + id), Captcha.class);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }

        if (c != null) {
            c.setTimes(c.getTimes() + 1);
            c.setDate(LocalDate.now().toString());
            if (success) {
                c.setSuccess(c.getSuccess() + 1);
            } else {
                c.setMistakes(c.getMistakes() + 1);
            }

            /* Actualizar informacion */
            String json = "";
            try {
                json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(c);
            } catch (JsonProcessingException ex) {
                ex.printStackTrace(System.out);
            }

            this.writeFile(path + id, json);
        } else {
            System.out.println("Captcha nulo");
        }
    }

    /**
     * Obtener direccion destino del captcha
     *
     * @param c
     * @return
     */
    public String getUrl(Captcha c) {
        Component head = c.getHead();
        for (Component ch : head.getChildren()) {
            if (ch.getTag() == Tag.LINK) {
                return ch.getParams().get(Param.HREF).getValue();
            }
        }

        return "";
    }

    public boolean isRedirect() {
        return redirect;
    }

    public List<String> getInserts() {
        return inserts;
    }

    public List<String> getAlerts() {
        return alerts;
    }
}
