package com.cesar31.captchaweb.servlet;

import com.cesar31.captchaweb.control.AstOperation;
import com.cesar31.captchaweb.model.Exit;
import com.cesar31.captchaweb.model.Instruction;
import com.cesar31.captchaweb.model.SymbolTable;
import java.io.IOException;
import java.util.LinkedList;
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        String action = request.getParameter("action");
        System.out.println("action: " + action);

        SymbolTable table = new SymbolTable();
        AstOperation operation = new AstOperation();
        operation.setRequest(request);
        operation.setResponse(response);

        LinkedList<Instruction> AST = (LinkedList<Instruction>) request.getSession().getAttribute("AST");
        for (Instruction i : AST) {
            Object o = i.run(table, operation);

            if (o != null) {
                if (o instanceof Exit) {
                    break;
                }
            }
        }

        table.forEach(v -> {
            System.out.println(v.toString());
        });

        if(!operation.getInserts().isEmpty()) {
            request.getSession().setAttribute("inserts", operation.getInserts());
        }
        
        request.getSession().setAttribute("alerts", operation.getAlerts());
        request.getRequestDispatcher("captcha.jsp").forward(request, response);
    }
}
