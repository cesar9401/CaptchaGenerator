
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
                
            Swal.queue(modals).then(function () {
                
            <c:if test="${url != null}">
                let timerInterval;
                Swal.fire({
                    title: 'Esta siendo redirigido a ${url}',
                    html: 'I will close in <b></b> milliseconds.',
                    timer: 2500,
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading();
                        timerInterval = setInterval(() => {
                            const content = Swal.getContent();
                            if (content) {
                                const b = content.querySelector('b');
                                if (b) {
                                    b.textContent = Swal.getTimerLeft();
                                }
                            }
                        }, 100);
                    },
                    willClose: () => {
                        clearInterval(timerInterval);
                    }
                }).then((result) => {
                    setTimeout(function () {
                        window.location.href = "${url}";
                    }, 500);
                });
            </c:if>
            }).catch(function() {
                <c:if test="${url != null}">
                window.location.href = "${url}";
                </c:if>
                console.log("Canceled");
            });
        </script>
    </body>
</html>
