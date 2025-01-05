<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<style>
    <%@ include file="/WEB-INF/jsp/fragmentos/estilos.jspf" %>
</style>
<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;" >
    <form action="${pageContext.request.contextPath}/tienda/usuarios/login" method="post">
        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                Usuario:
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <input name="usuario" />
            </div>
            <div style="float: left;width: 50%">
                Contrasena:
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <input name="password" />
            </div>
        </div>
        <input type="hidden" name="__method__" value="login"/>
        <input type="submit" value="login" class="crear"/>

    </form>
</div>
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
</body>
</html>
