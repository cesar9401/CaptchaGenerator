package com.cesar31.captchaweb.servlet;

import com.cesar31.captchaweb.control.AstOperation;
import com.cesar31.captchaweb.control.DBHandler;
import com.cesar31.captchaweb.control.ParserControl;
import com.cesar31.captchaweb.model.AST;
import com.cesar31.captchaweb.model.Captcha;
import com.cesar31.captchaweb.model.Exit;
import com.cesar31.captchaweb.model.Instruction;
import com.cesar31.captchaweb.model.SymbolTable;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cesar31
 */
@WebServlet(name = "CaptchaAnswer", urlPatterns = {"/CaptchaAnswer"})
public class CaptchaAnswer extends HttpServlet {

    private final String PATH = "/resources/db/";

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        switch (action) {
            case "list":
                /* listado de captchas */
                getList(request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String script = request.getParameter("action");
        script = script.replace("()", "");
        System.out.println("script: " + script);
        String id = request.getParameter("@id");
        System.out.println("id = " + id);

        String path = request.getServletContext().getRealPath(PATH);
        path = path.replace("target/CaptchaGenerator-1.0", "src/main/webapp");

        DBHandler db = new DBHandler();
        Captcha c = db.getCaptcha(path, id);

        if (c != null) {
            ParserControl control = new ParserControl();
            String html = control.getHtml(c);
            String title = control.getTitle();
            String background = control.getBackground();
            
            request.setAttribute("id", id);
            request.setAttribute("title", title);
            request.setAttribute("background", background);
            request.setAttribute("html", html);
            
            /* ejecutar codigo aqui */
            db.executeScript(script, request, response);
            
            request.getRequestDispatcher("captcha.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("captcha-not-found.jsp").forward(request, response);
        }

//        SymbolTable table = new SymbolTable();
//        AstOperation operation = new AstOperation();
//        operation.setRequest(request);
//        operation.setResponse(response);
//
//        AST ast = (AST) request.getSession().getAttribute("AST");
//        for (Instruction i : ast.getInstructions()) {
//            Object o = i.run(table, operation);
//
//            if (o != null) {
//                if (o instanceof Exit) {
//                    break;
//                }
//            }
//        }
//
//        table.forEach(v -> {
//            System.out.println(v.toString());
//        });
//
//        if(!operation.getInserts().isEmpty()) {
//            request.getSession().setAttribute("inserts", operation.getInserts());
//        }
        //request.getSession().setAttribute("alerts", operation.getAlerts());
        //request.getRequestDispatcher("captcha.jsp").forward(request, response);
    }

    private void getList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // System.out.println(request.getServletContext().getRealPath("."));

        String path = request.getServletContext().getRealPath(PATH);
        path = path.replace("target/CaptchaGenerator-1.0", "src/main/webapp");

        ParserControl control = new ParserControl();
        List<Captcha> captchas = control.getCaptchas(path);

        request.setAttribute("list", captchas);
        request.getRequestDispatcher("report-captcha.jsp").forward(request, response);
    }
}
