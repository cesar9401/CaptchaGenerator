
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="resources/assets/css/css.html" %>
        <link href="resources/assets/img/captcha.png" rel="icon" type="image/png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tabla de Simbolos</title>
    </head>
    <body>

        <jsp:include page="WEB-INF/nav.jsp"></jsp:include>

            <section id="symbol-table">
                <div class="container">
                    <div class="row">
                        <div class="col text-center my-4">
                            <h1 class="text-success display-2">Tabla de Simbolos</h1>
                            <h3 class="text-success">Captcha: ${table.getCaptcha()}</h3>
                            <h3 class="text-success">Proceso: ${table.getProcess()}</h3>
                    </div>
                </div>

                <div class="row">
                    <div class="col my-4 text-center">
                        <!-- Table here -->
                        <table id="symbols" class="table table-striped table-hover table-bordered">
                            <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Identificador</th>
                                    <th scope="col">Tipo</th>
                                    <th scope="col">Valor actual</th>
                                    <th scope="col">Modo</th>
                                    <th scope="col">Scope</th>
                                    <th scope="col">No. Ejecuci&oacute;n</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="v" items="${table}">
                                    <tr hidden>
                                        <th scope="row">1</th>
                                        <td>${v.id}</td>
                                        <td>${v.type.toString().toLowerCase()}</td>
                                        <td>${v.value}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${v.global}">
                                                    @global
                                                </c:when>
                                                <c:otherwise>
                                                    -
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${v.scope}</td>
                                        <td>${v.tried}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>

        <%@include file="resources/assets/js/js.html" %>
        <script>
            let tb = document.getElementById('symbols');
            let size = tb.getElementsByTagName('tr').length - 1;

            for (let i = 0; i < size; i++) {
                tb.tBodies[0].rows[i].cells[0].innerHTML = i + 1;
                setTimeout(function () {
                    tb.tBodies[0].rows[i].removeAttribute("hidden");
                }, 1500 * (i + 1));
            }
        </script>
    </body>
</html>
