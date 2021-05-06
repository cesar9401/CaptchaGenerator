
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="resources/assets/css/css.html" %>
        <link href="resources/assets/css/editor.css" rel="stylesheet">
        <link href="resources/assets/img/captcha.png" rel="icon" type="image/png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Captcha</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/nav.jsp"></jsp:include>

            <section id="upload-file">
                <div class="container">
                    <div class="row">
                        <div class="col">
                            <form>
                                <div class="row my-3">
                                    <div class="col">
                                        <input class="form-control" name="data" type="file" id="data">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>

            <section id="code-editor" class="mb-4">
                <div class="container my-4">
                    <div class="row">
                        <div class="col">
                            <h3 class="text-center text-secondary">Editor de Texto</h3>
                            <div>
                                <form action="CaptchaMain" method="post">
                                    <div class="row">
                                        <div class="col">
                                            <textarea class="rownr" rows="25" cols="2" readonly></textarea>
                                            <span>
                                                <textarea class="txt" rows="25" cols="3" nowrap="nowrap" wrap="off"
                                                          autocomplete="off" autocorrect="off" autocapitalize="off" spellcheck="false"
                                                          onclick="selectionchanged(this)"
                                                          onkeyup="keyup(this, event)" 
                                                          onmousemove="input_changed(this)"
                                                          oninput="input_changed(this)"
                                                          onscroll="scroll_changed(this)"
                                                          name="source"
                                                          id="source" required>
                                                </textarea>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="row my-0">
                                        <div class="col d-flex justify-content-end my-0">
                                            <span class="badge bg-danger">
                                                <p class="my-0 fs-6 text" id="info">Posicion actual: (0,0)</p>
                                            </span>
                                        </div>
                                    </div>
                                    <div class="row text-center">
                                        <div class="col-4 offset-4 d-grid">
                                            <button type="submit" name="action" value="get-source" class="btn btn-dark btn-lg btn-block mb-4">Compilar</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

        <%@include file="resources/assets/js/js.html" %>
        <script src="resources/assets/js/editor.js"></script>
        <script src="resources/assets/js/upload.js"></script>
    </body>
</html>
