<%--
  Created by IntelliJ IDEA.
  User: moren
  Date: 06/01/2025
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pedido Confirmado</title>
    <style>
        <%@ include file="/WEB-INF/jsp/fragmentos/estilos.jspf" %>
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>
<h1>Tu pedido ha sido registrado con éxito</h1>
<a href="<%= request.getContextPath()%>" class="arriba">Volver al inicio</a>
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
</body>
</html>