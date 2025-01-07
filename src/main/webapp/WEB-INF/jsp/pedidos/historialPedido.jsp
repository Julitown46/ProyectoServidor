<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.TiendaWhisky2.model.Pedido" %>
<!DOCTYPE html>
<html>
<head>
    <title>Historial de Pedidos</title>
    <style>
        <%@ include file="/WEB-INF/jsp/fragmentos/estilos.jspf" %>
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>
<h1 style="display: flex; justify-content: center">Historial de Pedidos</h1>

<div class="table-container">

<%
    // Recuperar la lista de pedidos del request
    List<Pedido> listaPedidos = (List<Pedido>) request.getAttribute("listaPedidos");

    if (listaPedidos != null && !listaPedidos.isEmpty()) {
%>
<table>
    <thead>
    <tr>
        <th>ID Pedido</th>
        <th>Nombre del Producto</th>
        <th>Fecha del Pedido</th>
        <th>Cantidad</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Pedido pedido : listaPedidos) {
    %>
    <tr>
        <td><%= pedido.getIdPedido() %></td>
        <td><%= pedido.getNombreProducto() %></td>
        <td><%= pedido.getFechaPedido() %></td>
        <td><%= pedido.getCantidad() %></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
<%
} else {
%>
<p>No tienes pedidos en el historial.</p>
<%
    }
%>
</div>
<a href="<%= request.getContextPath()%>" class="arriba">Volver al inicio</a>
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
</body>
</html>