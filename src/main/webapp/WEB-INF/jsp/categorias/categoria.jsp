<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="org.TiendaWhisky2.model.Categoria" %>
<%@page import="org.TiendaWhisky2.model.CategoriaDTO" %>
<%@page import="org.TiendaWhisky2.model.Usuario" %>
<%@page import="java.util.List" %>
<%@page import="java.util.Optional" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Categorias</title>
    <style>
        <%@ include file="/WEB-INF/jsp/fragmentos/estilos.jspf" %>
    </style>
</head>
<body style="margin-bottom: 40px">
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>
<form action="${pageContext.request.contextPath}/tienda/categorias" method="get">
    <label for="ordenar-por">Ordenar por:</label>
    <select name="ordenar-por" id="ordenar-por">
        <option value="codigo">codigo</option>
        <option value="nombre">nombre</option>
    </select>

    <label for="modo-ordenar">Modo:</label>
    <select name="modo-ordenar" id="modo-ordenar">
        <option value="asc">asc</option>
        <option value="desc">desc</option>
    </select>

    <button type="submit">Ordenar</button>
</form>
<h2 style="display: flex; justify-content: center;">Categorias</h2>
<br>
<div class="table-container">
    <%
        List<CategoriaDTO> listaCategorias = (List<CategoriaDTO>) request.getAttribute("listaCategorias");
        Optional<Usuario> usuCat = Optional.ofNullable((Usuario) session.getAttribute("usuario-logado"));
        String rolUsuario = usuCat.map(Usuario::getRol).orElse("");
        boolean esAdmin = "Admin".equals(rolUsuario);

        if (listaCategorias != null && !listaCategorias.isEmpty()) {
    %>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>NumProd</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (CategoriaDTO categoria : listaCategorias) {
        %>
        <tr>
            <td><%= categoria.getIdCategoria() %></td>
            <td><%= categoria.getNombre() %></td>
            <td><%= categoria.getDescripcion() %></td>
            <td><%= categoria.getCantProductos() %></td>
            <td>
                <% if (esAdmin) { %>
                <a href="<%= request.getContextPath() + "/tienda/categorias/editar/" + categoria.getIdCategoria() %>" class="editar">Editar</a>
                <a href="<%= request.getContextPath() + "/tienda/categorias/detalle/" + categoria.getIdCategoria() %>" class="detalle">Detalle</a>
                <form style="display:inline" action="<%= request.getContextPath() + "/tienda/categorias" %>"
                      method="post" onsubmit="return confirm('¿Estás seguro de que deseas eliminar esta categoria?');">

                    <input type="hidden" name="codigo" value="<%= categoria.getIdCategoria() %>"/>
                    <input type="hidden" name="__method__" value="delete"/>
                    <button type="submit" class="eliminar">Eliminar</button>
                </form>

                <% } %>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <%
    } else {
    %>
    <p>No hay categorias disponibles.</p>
    <%
        }
    %>

</div>

<%
    if (esAdmin) {
%>
<div style="margin-top: 10px;">
    <a href="<%= request.getContextPath() + "/tienda/categorias/crear" %>" class="crear">Crear</a>
</div>
<%
    }
%>

<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>

</body>
</html>