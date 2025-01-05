<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro</title>
    <style>
        body {
            background-color: #f9f9f9;
            margin: 0;
        }

        .clearfix::after {
            content: "";
            display: block;
            clear: both;
        }
        <%@ include file="/WEB-INF/jsp/fragmentos/estilos.jspf" %>
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>

<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;">
    <form action="${pageContext.request.contextPath}/tienda/usuarios/crear/" method="post">
        <div class="clearfix">
            <div style="float: left; width: 50%">
                <h1>Registro</h1>
            </div>
            <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">

                <div style="position: absolute; left: 39%; top : 39%;">
                    <input type="submit" value="Crear" class="arriba"/>
                </div>

            </div>
        </div>

        <div class="clearfix">
            <hr/>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                Nombre:
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <input name="usuario" required/>
            </div>
        </div>
        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                Password:
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <input type="password" name="password" required/>
            </div>
        </div>
        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                <label>Rol</label>
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <select name="rol">
                    <option value="Admin">Admin</option>
                    <option value="Cliente">Cliente</option>
                </select>
            </div>
        </div>
    </form>
</div>
<%
    String error = (String)request.getAttribute("error");
    if (error != null) {
%>
<p style="color: red; text-align: center"><%= error %></p>
<% } %>

<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
</body>
</html>