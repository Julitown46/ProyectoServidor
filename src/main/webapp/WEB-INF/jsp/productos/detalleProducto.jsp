<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="org.TiendaWhisky2.model.Producto"%>
<%@page import="java.util.Optional"%>
<%@ page import="org.TiendaWhisky2.model.Pedido" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
<title>Detalle Producto</title>
    <style>
        <%@ include file="/WEB-INF/jsp/fragmentos/estilos.jspf" %>
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>
<div id="contenedora" style="margin: 0 auto; width: 900px;">
<form action="${pageContext.request.contextPath}/tienda/productos/editar/" method="post">
        <input type="hidden" name="__method__" value="put" />

        <div class="clearfix" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
<h1>Detalle Producto</h1>
            <div>
<a href="<%= request.getContextPath() + "/tienda/productos" %>" class="arriba">Volver</a>
            </div>
        </div>

        <%
            Optional<Producto> optionalProducto = (Optional<Producto>) request.getAttribute("producto");

            if (optionalProducto.isPresent()) {

                Producto producto = optionalProducto.get();
        %>

        <div class="clearfix" style="display: flex; justify-content: space-between; margin-top: 6px;">
            <div style="flex: 1;">
                <label>Codigo</label>
            </div>
            <div style="flex: 2;">
<input name="codigo" value="<%= producto.getIdProducto() %>" readonly="readonly" />
            </div>
        </div>

        <div class="clearfix" style="display: flex; justify-content: space-between; margin-top: 6px;">
            <div style="flex: 1;">
                <label>Nombre</label>
            </div>
            <div style="flex: 2;">
                <input name="nombre" value="<%= producto.getNombre() %>" readonly="readonly"/>
            </div>
        </div>

        <div class="clearfix" style="display: flex; justify-content: space-between; margin-top: 6px;">
            <div style="flex: 1;">
                <label>Descripcion</label>
            </div>
            <div style="flex: 2;">
                <textarea name="descripcion" rows="4" cols="50" readonly="readonly">
                    <%= producto.getDescripcion() != null ? producto.getDescripcion() : "" %>
                </textarea>
            </div>
        </div>
    <div class="clearfix" style="display: flex; justify-content: space-between; margin-top: 6px;">
        <div style="flex: 1;">
            <label>Precio</label>
        </div>
        <div style="flex: 2;">
            <input name="precio" value="<%= producto.getPrecio() %>" readonly="readonly"/>
        </div>
    </div>
    <div class="clearfix" style="display: flex; justify-content: space-between; margin-top: 6px;">
        <div style="flex: 1;">
            <label>Categoria Id</label>
        </div>
        <div style="flex: 2;">
            <input name="categoriaId" value="<%= producto.getCategoria_id() %>" readonly="readonly"/>
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