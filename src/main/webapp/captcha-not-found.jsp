
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="resources/assets/css/css.html" %>
       	<link type="text/css" rel="stylesheet" href="resources/assets/css/style404.css" />
        <link href="resources/assets/img/captcha.png" rel="icon" type="image/png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>NOT FOUND</title>
    </head>
    <body>
        <jsp:include page="WEB-INF/nav.jsp"></jsp:include>
            
        <div id="notfound">
                <div class="notfound">
                    <div class="notfound-404">
                        <div></div>
                        <h1>404</h1>
                    </div>
                    <h2>Page not found</h2>
                    <a href="index.jsp">home page</a>
                </div>
            </div>


        <%@include file="resources/assets/js/js.html" %>
    </body>
</html>
