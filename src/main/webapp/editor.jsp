
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
                                    <div class="col-12 col-sm-10">
                                        <input class="form-control" name="data" type="file" id="data">
                                    </div>
                                    <div class="col-12 col-sm-2 d-grid">
                                        <button id="save" type="button" class="btn btn-light">Save</button>
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
                                                <c:if test="${source != null}">${source}</c:if>
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
        </section>

        <section id="errors" hidden>
            <div class="container mb-4">
                <div class="row">
                    <div class="col text-center my-2">
                        <h1 class="text-danger mt-4">Errores</h1>
                    </div>
                </div>

                <div class="row">
                    <div class="col mb-4">
                        <table class="table table-striped table-hover table-bordered">
                            <thead>
                                <tr>
                                    <th scope="col">Linea</th>
                                    <th scope="col">Columna</th>
                                    <th scope="col">Tipo</th>
                                    <th scope="col">Lexema</th>
                                    <th scope="col">Descripcion</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="e" items="${errors}">
                                    <tr>
                                        <th scope="row">${e.line}</th>
                                        <th scope="row">${e.column}</th>
                                        <td>${e.type}</td>
                                        <td>${e.lexema}</td>
                                        <td>${e.description}</td>
                                    </tr>                                    
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>

        <%@include file="resources/assets/js/js.html" %>
        <script src="resources/assets/js/editor.js"></script>
        <script src="resources/assets/js/upload.js"></script>

        <script>
            let button = document.getElementById('save');
            let text = document.getElementById('source');
            
            button.addEventListener('click', () => {
                let source = text.value;
                if(source !== undefined) {
                    if(source.trim() !== "") {
                        save(source);
                    }
                }
            });
            
            function save(source) {
                let link = document.createElement('a');
                link.href = 'data:text/plain;charset=UTF-8,' + escape(source);
                link.download = 'output.gcic';
                link.click();
            }
        </script>
        
        <c:if test="${errors != null}">
            <script>
                document.getElementById('errors').removeAttribute('hidden');
                document.location.hash = '#errors';
            </script>
        </c:if>
    </body>
</html>
