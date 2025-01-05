<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="org.TiendaWhisky2.model.Producto" %>
<%@page import="org.TiendaWhisky2.dao.CategoriaDAOImpl" %>
<%@page import="java.util.List" %>
<%@ page import="org.TiendaWhisky2.model.Categoria" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Producto</title>
    <style>
        body {
            background-color: #f9f9f9;
            margin: 0;
        }

        h2 {
            text-align: center;
        }
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
    <form action="${pageContext.request.contextPath}/tienda/productos/editar/" method="post">
        <input type="hidden" name="__method__" value="put" />
        <div class="clearfix">
            <div style="float: left; width: 50%">
                <h1>Editar Producto</h1>
            </div>
            <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">
                <div style="position: absolute; left: 39%; top : 39%;">
                    <input type="submit" value="Guardar" class="arriba" />
                </div>
            </div>
        </div>

            <%
            Optional<Producto> optionalProducto = (Optional<Producto>) request.getAttribute("producto");

            if (optionalProducto.isPresent()) {
                Producto producto = optionalProducto.get();
        %>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                <label>Código</label>
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <input name="codigo" value="<%= producto.getIdProducto() %>" readonly="readonly"/>
            </div>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                <label>Nombre</label>
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <input name="nombre" value="<%= producto.getNombre() %>"/>
            </div>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                <label>Precio</label>
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <input name="precio" value="<%= producto.getPrecio() %>" type="number" step="0.01"/>
            </div>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                <label>Descripción</label>
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <textarea name="descripcion" rows="4" cols="50">
                    <%= producto.getDescripcion() != null ? producto.getDescripcion() : "" %>
                </textarea>
            </div>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                <label>Categoría</label>
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <select name="categoria_id">
                    <%
                        CategoriaDAOImpl categoriaDao = new CategoriaDAOImpl();
                        List<Categoria> categorias = categoriaDao.getAll();
                        for (Categoria categoria : categorias) {
                    %>
                    <option value="<%= categoria.getIdCategoria() %>"
                            <%= categoria.getIdCategoria() == producto.getCategoria_id() ? "selected" : "" %>>
                        <%= categoria.getNombre() %>
                    </option>
                    <% } %>
                </select>
            </div>
        </div>

            <%
            } else {
                response.sendRedirect("/tienda/productos");
            }
    } else {
%>
        <script type="text/javascript">
            alert("No tienes permisos de administrador. Serás redirigido al inicio.");
            window.location.href = "${pageContext.request.contextPath}/";
        </script>
            <%
    }
%>

</div>

<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
</body>
</html>