<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="org.TiendaWhisky2.model.Categoria"%>
<%@page import="java.util.Optional"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
<title>Detalle Categoria</title>
    <style>
        <%@ include file="/WEB-INF/jsp/fragmentos/estilos.jspf" %>
    </style>
</head>
<body>
<div id="contenedora" style="margin: 0 auto; width: 900px;">
    <form action="${pageContext.request.contextPath}/tienda/categorias/editar/" method="post">
        <input type="hidden" name="__method__" value="put" />

        <div class="clearfix" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h1>Editar Categoria</h1>
            <div>
                <a href="<%= request.getContextPath() + "/tienda/categorias" %>" class="arriba">Volver</a>
            </div>
        </div>

        <%
            Optional<Categoria> optionalCategoria = (Optional<Categoria>) request.getAttribute("categoria");
            if (optionalCategoria.isPresent()) {
                Categoria categoria = optionalCategoria.get();
        %>

        <div class="clearfix" style="display: flex; justify-content: space-between; margin-top: 6px;">
            <div style="flex: 1;">
                <label>Codigo</label>
            </div>
            <div style="flex: 2;">
                <input name="codigo" value="<%= categoria.getIdCategoria() %>" readonly="readonly" />
            </div>
        </div>

        <div class="clearfix" style="display: flex; justify-content: space-between; margin-top: 6px;">
            <div style="flex: 1;">
                <label>Nombre</label>
            </div>
            <div style="flex: 2;">
                <input name="nombre" value="<%= categoria.getNombre() %>" readonly="readonly"/>
            </div>
        </div>

        <div class="clearfix" style="display: flex; justify-content: space-between; margin-top: 6px;">
            <div style="flex: 1;">
                <label>Descripcion</label>
            </div>
            <div style="flex: 2;">
                <textarea name="descripcion" rows="4" cols="50" readonly="readonly">
                    <%= categoria.getDescripcion() != null ? categoria.getDescripcion() : "" %>
                </textarea>
            </div>
        </div>

    </form>
</div>
<%
    }
%>
    <%@include file="/WEB-INF/jsp/fragmentos/bootstrap.jspf"%>
    <%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>

</body>
</html>