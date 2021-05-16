package com.cesar31.captchaweb.servlet;

import com.cesar31.captchaweb.control.DBHandler;
import com.cesar31.captchaweb.control.ParserControl;
import com.cesar31.captchaweb.model.Captcha;
import java.io.IOException;
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
        String path = request.getServletContext().getRealPath(PATH);
        path = path.replace("target/CaptchaGenerator-1.0", "src/main/webapp");
        String id = request.getParameter("id");

        DBHandler db = new DBHandler();
        Captcha c = db.getCaptcha(path, id);

        if (c != null) {
            ParserControl control = new ParserControl();
            String html = control.getHtml(c);
            String title = control.getTitle();
            String background = control.getBackground();

            /* enviar id */
            request.setAttribute("id", id);

            request.setAttribute("title", title);
            request.setAttribute("background", background);
            request.setAttribute("html", html);

            /* Ejecutar ON_LOAD aqui */
            db.executeOnLoad(id, request, response);
            if (!db.getInserts().isEmpty()) {
                request.setAttribute("inserts", db.getInserts());
            }

            if (!db.getAlerts().isEmpty()) {
                request.setAttribute("alerts", db.getAlerts());
            }

            if (db.isRedirect()) {
                /* Instrucciones para redirigir */
                String url = db.getUrl(c);
                request.setAttribute("url", url);
            }

            request.getRequestDispatcher("captcha.jsp").forward(request, response);
        } else {
            /* Captcha no existe */
            request.getRequestDispatcher("captcha-not-found.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        switch (action) {
            case "get-source":
                getSource(request, response);
                break;
        }
    }

    private void getSource(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Archivo de entrada */
        String source = new String(request.getParameter("source").getBytes("ISO-8859-1"), "UTF-8");
        String path = request.getServletContext().getRealPath(PATH);
        path = path.replace("target/CaptchaGenerator-1.0", "src/main/webapp");

        /* Direccion de almacenamiento */
        ParserControl control = new ParserControl(source, path);
        control.parseSourceCode();

        if (control.getErrors().isEmpty()) {
            /* Sin errores, mostrar link */

            String name = control.getName();
            System.out.println(name);
            request.setAttribute("name", name);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {

            request.setAttribute("source", source);
            request.setAttribute("errors", control.getErrors());
            request.getRequestDispatcher("editor.jsp").forward(request, response);
        }
    }
}
