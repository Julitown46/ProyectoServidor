<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@page import="org.TiendaWhisky2.model.Categoria" %>
<%@page import="java.util.Optional" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Categoria</title>
    <style>
        <%@ include file="/WEB-INF/jsp/fragmentos/estilos.jspf" %>
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>

<%
    Optional<Usuario> usuCat = Optional.ofNullable((Usuario) session.getAttribute("usuario-logado"));
    String rolUsuario = usuCat.map(Usuario::getRol).orElse("");
    boolean esAdmin = "Admin".equals(rolUsuario);

    if (esAdmin) {
%>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;">
    <form action="${pageContext.request.contextPath}/informatica/categorias/crear/" method="post">
        <div class="clearfix">
            <div style="float: left; width: 50%">
                <h1>Crear categoria</h1>
            </div>
            <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">

                <div style="position: absolute; left: 39%; top : 39%;">
                    <input type="submit" value="Crear" class="crear"/>
                </div>

            </div>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left; width: 50%;">
                Nombre:
            </div>
            <div style="float: none; width: auto; overflow: hidden;">
                <input name="nombre" style="padding: 2px;"/>
            </div>
            <div style="float: left; width: 50%; margin-top: 10px;">
                Descripción:
            </div>
            <div>
                <textarea name="descripcion" style="margin-top: 5px; height: 80px; width: auto; padding: 5px; resize: none;"></textarea>
            </div>
        </div>

    </form>
</div>
<%
} else {
%>
<script type="text/javascript">
    alert("No tienes permisos de administrador. Serás redirigido al inicio.");
    window.location.href = "${pageContext.request.contextPath}/";
</script>
<%
    }
%>

<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
</body>
</html>