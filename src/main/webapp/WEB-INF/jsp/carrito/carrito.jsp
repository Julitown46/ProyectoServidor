<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="org.TiendaWhisky2.model.CarritoItem" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Carrito</title>
  <style><%@ include file="/WEB-INF/jsp/fragmentos/estilos.jspf" %></style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>
<h1 style="display: flex; justify-content: center">Carrito de Compras</h1>
<div class="table-container">

<%
  List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
  double total = 0;

  if (carrito != null && !carrito.isEmpty()) {
%>
<table>
  <thead>
  <tr>
    <th>Producto</th>
    <th>Precio Unitario</th>
    <th>Cantidad</th>
    <th>SubTotal</th>
    <th>Acción</th>
  </tr>
  </thead>
  <tbody>
  <%
    for (CarritoItem item : carrito) {
      double subtotal = item.getCantidad() * item.getProducto().getPrecio();
      total += subtotal;
  %>
  <tr>
    <td><%= item.getProducto().getNombre() %></td>
    <td><%= item.getProducto().getPrecio() %></td>
    <td><%= item.getCantidad() %></td>
    <td><%= subtotal %></td>
    <td>
      <form action="<%= request.getContextPath() %>/tienda/carrito" method="post">
        <input type="hidden" name="productoId" value="<%= item.getProducto().getIdProducto() %>" />
        <input type="hidden" name="action" value="eliminar" />
        <input type="submit" value="Eliminar" class="eliminar"/>
      </form>
    </td>
  </tr>
  <%
    }
  %>
  </tbody>
</table>
</div>
<h3 style="display: flex; justify-content: center">Total: <%= total %></h3>

<form action="<%= request.getContextPath() %>/tienda/pedidos" method="post">
  <input type="submit" value="Finalizar Pedido" class="arriba">
</form>

<%
} else {
%>
<p style="display: flex; justify-content: center">El carrito está vacío.</p>
<%
  }
%>

<a href="<%= request.getContextPath()%>" class="arriba">Volver al inicio</a>
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
</body>
</html>
