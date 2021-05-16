
<%@ page import="com.cesar31.captchaweb.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="resources/assets/css/css.html" %>
        <link href="resources/assets/img/captcha.png" rel="icon" type="image/png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
    </head>
    <body style="background-color: ${background};">
        <jsp:include page="WEB-INF/nav.jsp"></jsp:include>

            <section id="captcha">
                <div class="container">
                    <form action="CaptchaAnswer" method="post">
                        <input type="hidden" value="${id}" name="@id">
                    ${html}
                </form>
            </div>
        </section>

        <section id="link-table" hidden>
            <div class="container mt-4">
                <div class="row">
                    <div class="col text-center mt-4">
                        <a href="CaptchaAnswer?action=symbol-table" class="btn btn-outline-dark my-4">Reporte de Ejecución</a>
                    </div>
                </div>
            </div>
        </section>

        <%@include file="resources/assets/js/js.html" %>
        <!--
        <script src="//cdn.jsdelivr.net/npm/sweetalert2@10"></script>
        -->
        <script src="resources/assets/js/sweetalert2.all.min.js"></script>

        <c:if test="${inserts != null}">
            <script>
                <c:forEach var="i" items="${inserts}">
                    ${i}
                </c:forEach>
            </script>
        </c:if>

        <script>


            var modals = [];
            <c:if test="${alerts != null}">
                <c:forEach var="a" items="${alerts}">
            modals.push({title: 'Informacion', text: '${a}'});
            console.log('Mensaje: ${a}');
                </c:forEach>
            </c:if>

            <c:if test="${on_load != null}">
            modals.push({icon: 'question', title: 'Tabla de simbolos', text: '¿Desea ver la tabla de simbolos de los procesos ON_LOAD?', footer: '<a href="CaptchaAnswer?action=symbol-table&captcha=${on_load}&process=ON_LOAD" target="_blank">Reporte de Ejecución</a>'});
            </c:if>

            <c:if test="${process != null}">
            modals.push({icon: 'question', title: 'Tabla de simbolos', text: '¿Desea ver la tabla de simbolos?', footer: '<a href="CaptchaAnswer?action=symbol-table&captcha=${captcha}&process=${process}" target="_blank">Reporte de Ejecución</a>'});
            </c:if>

            Swal.queue(modals).then(function () {

            <c:if test="${url != null}">
                //let timerInterval;
                Swal.fire({
                    title: 'Redirigiendo...',
                    html: 'I will close in a few seconds.',
                    timer: 1500,
                    timerProgressBar: true
                }).then((result) => {
                    setTimeout(function () {
                        window.location.href = "${url}";
                    }, 500);
                });
            </c:if>
            }).catch(function () {
            <c:if test="${url != null}">
                window.location.href = "${url}";
            </c:if>
                console.log("Canceled");
            });
        </script>
    </body>
</html>
