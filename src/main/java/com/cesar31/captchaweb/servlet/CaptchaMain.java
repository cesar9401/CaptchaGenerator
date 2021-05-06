package com.cesar31.captchaweb.servlet;

import com.cesar31.captchaweb.control.ParserControl;
import com.cesar31.captchaweb.model.Captcha;
import com.cesar31.captchaweb.model.Instruction;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "CaptchaMain", urlPatterns = {"/CaptchaMain"})
public class CaptchaMain extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CaptchaMain</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CaptchaMain at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        processRequest(request, response);
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
        switch (action) {
            case "get-source":
                getSource(request, response);
                break;
        }
    }

    private void getSource(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String source = request.getParameter("source");
        String source = new String(request.getParameter("source").getBytes("ISO-8859-1"), "UTF-8");
        ParserControl control = new ParserControl(source);
        control.parseSourceCode();
        if (control.getErrors().isEmpty()) {
            /* Recordad verificar errores AST antes de mostrar captcha */
            LinkedList<Instruction> AST = control.getAST();

            /* Redirigir a captcha */
            Captcha captcha = control.getCaptcha();
            request.setAttribute("captcha", captcha);
            request.getRequestDispatcher("captcha.jsp").forward(request, response);
        } else {
            /* Redirigir errores */
            control.getErrors().forEach(e -> {
                System.out.println(e);
            });
        }
    }
}
