<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="org.TiendaWhisky2.model.Categoria" %>
<%@ page import="java.util.List" %>
<%@ page import="org.TiendaWhisky2.model.Producto" %>
<%@ page import="org.TiendaWhisky2.dao.CategoriaDAOImpl" %>
<%@ page import="java.util.ArrayList" %>

<%
    HttpSession miSession = request.getSession();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Productos</title>
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
<body style="margin-bottom: 40px">
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>
<form action="${pageContext.request.contextPath}/tienda/productos/" method="get">
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
<h2>Lista de Productos</h2>
<div class="table-container">
    <%
        List<Producto> listaProductos = (List<Producto>) request.getAttribute("listaProductos");
        Optional<Usuario> usuarioLogado = Optional.ofNullable((Usuario) session.getAttribute("usuario-logado"));
        boolean esAdmin = usuarioLogado.isPresent() && "Admin".equals(usuarioLogado.get().getRol());

        if (listaProductos != null && !listaProductos.isEmpty()) {
    %>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Precio</th>
            <th>Categoría</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Producto producto : listaProductos) {
        %>
        <tr>
            <td><%= producto.getIdProducto() %></td>
            <td><%= producto.getNombre() %></td>
            <td><%= producto.getDescripcion() %></td>
            <td><%= producto.getPrecio() %></td>
            <td><%= producto.getCategoria_id() %></td>
            <td>
                <%
                    if (esAdmin) {
                %>
                <a href="<%= request.getContextPath() + "/tienda/productos/editar/" + producto.getIdProducto() %>" class="editar">Editar</a>
                <a href="<%= request.getContextPath() + "/tienda/productos/detalle/" + producto.getIdProducto() %>" class="detalle">Detalle</a>
                <form style="display:inline" action="<%= request.getContextPath() + "/tienda/productos" %>"
                      method="post" onsubmit="return confirm('¿Estás seguro de que deseas eliminar este producto?');">

                    <input type="hidden" name="codigo" value="<%= producto.getIdProducto() %>"/>
                    <input type="hidden" name="__method__" value="delete"/>
                    <button type="submit" class="eliminar">Eliminar</button>
                </form>
                <%
                } else {
                %>
                <form style="display:inline" action="<%= request.getContextPath() + "/tienda/carrito" %>" method="post"
                      onsubmit="<%= usuarioLogado.isPresent() ? "" : "return verificarSesion();" %>" >
                    <input type="hidden" name="productoId" value="<%= producto.getIdProducto() %>"/>
                    <input type="hidden" name="accion" value="agregar"/>

                    <label>Cantidad:</label>
                    <input type="number" name="cantidad" min="1" value="1" style="width: 50px; margin-right: 10px;" required/>
                    <button type="submit" class="anadirCarrito">Añadir al carrito</button>
                </form>

                <%
                    }
                %>
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
    <p>No hay productos disponibles.</p>
    <%
        }
    %>
</div>

<%
    if (esAdmin) {
%>
<div style="margin-top: 10px;">
    <a href="<%= request.getContextPath() + "/tienda/productos/crear" %>" class="crear">Crear</a>
</div>
<%
    }
%>

<script>
    function verificarSesion() {
        alert("Debes iniciar sesión para añadirlo al carrito");
        return false;
    }
</script>


<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>

</body>
</html>