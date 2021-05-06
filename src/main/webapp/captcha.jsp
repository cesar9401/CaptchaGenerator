
<%@ page import="com.cesar31.captchaweb.model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="resources/assets/css/css.html" %>
        <link href="resources/assets/img/captcha.png" rel="icon" type="image/png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Captcha</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/nav.jsp"></jsp:include>

            <section id="captcha">
                <div class="container">

                </div>
            </section>

        <%@include file="resources/assets/js/js.html" %>
    </body>
</html>
