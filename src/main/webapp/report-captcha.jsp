
<%@ page import="com.cesar31.captchaweb.model.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="resources/assets/css/css.html" %>
        <link href="resources/assets/img/captcha.png" rel="icon" type="image/png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Estadisticas</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/nav.jsp"></jsp:include>
            <section id="captchas-list">
                <div class="container">
                    <div class="row">
                        <div class="col text-center mt-4">
                            <h1 class="text-success display-1 my-4">Listado de Captcha's</h1>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <table class="table table-striped table-hover table-bordered">
                                <thead>
                                    <tr>
                                        <th scope="col">Id</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">Enlace</th>
                                        <th scope="col">Handle</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="c" items="${list}">
                                    <tr>
                                        <th scope="row">${c.params.get(Param.ID).value}</th>
                                        <td>${c.params.get(Param.NAME).value}</td>
                                        <td><a href="CaptchaMain?id=${c.params.get(Param.ID).value}.gcic" target="_blank">Click Aqui</a></td>
                                        <td>@mdo</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>

        <%@include file="resources/assets/js/js.html" %>
    </body>
</html>
