<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.TiendaWhisky2.model.Producto" %>
<%@ page import="org.TiendaWhisky2.model.Categoria" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Producto</title>
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
    Optional<Usuario> usuProd = Optional.ofNullable((Usuario) session.getAttribute("usuario-logado"));
    String rolUsuario = usuProd.map(Usuario::getRol).orElse("");
    boolean esAdmin = "Admin".equals(rolUsuario);

    if (esAdmin) {
%>
<div id="contenedora" style="float:none; margin: 0 auto;width: 900px;">
    <form action="${pageContext.request.contextPath}/tienda/productos/crear/" method="post">
        <div class="clearfix">
            <div style="float: left; width: 50%">
                <h1>Crear Producto</h1>
            </div>
            <div style="float: none;width: auto;overflow: hidden;min-height: 80px;position: relative;">

                <div style="position: absolute; left: 39%; top : 39%;">
                    <input type="submit" value="Crear" class="arriba"/>
                </div>

            </div>
        </div>

        <br>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left; width: 50%;">Nombre:</div>
            <div style="float: none; width: auto; overflow: hidden;">
                <input name="nombre" style="padding: 2px;" required/>
            </div>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left; width: 50%;">Precio:</div>
            <div style="float: none; width: auto; overflow: hidden;">
                <input name="precio" style="padding: 2px;" type="number" step="0.01" required/>
            </div>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left; width: 50%;">Descripción:</div>
            <div>
                <textarea name="descripcion" style="margin-top: 5px; height: 80px; width: auto; padding: 5px; resize: none;"></textarea>
            </div>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left; width: 50%;">Categoría:</div>
            <div style="float: none; width: auto; overflow: hidden;">
                <select name="categoria_id" style="padding: 2px;" required>
                    <option value="">Seleccione una categoría</option>
                    <%
                        List<Categoria> categorias = (List<Categoria>) request.getAttribute("categoria");
                        if (categorias != null && !categorias.isEmpty()) {
                     for (Categoria categoria : categorias) { %>
                    <option value="<%= categoria.getIdCategoria() %>"><%= categoria.getNombre() %></option>
                    <% }
                    } else {
                    %>
                    <option value="">No hay categorías disponibles</option>
                    <%
                        }
                    %>
                </select>
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