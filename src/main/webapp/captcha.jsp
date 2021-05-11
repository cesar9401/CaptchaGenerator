
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

        <%@include file="resources/assets/js/js.html" %>
        <script src="//cdn.jsdelivr.net/npm/sweetalert2@10"></script>
        <c:if test="${alerts != null}">
            <script>
                var modals = [];
                <c:forEach var="a" items="${alerts}">
                modals.push({title: 'Informacion', text: '${a}'});
                console.log('Mensaje: ${a}');
                </c:forEach>
                Swal.queue(modals);
            </script>
        </c:if>

        <c:if test="${inserts != null}">
            <script>
                <c:forEach var="i" items="${inserts}">
                    ${i}
                </c:forEach>
            </script>
        </c:if>
    </body>
</html>
