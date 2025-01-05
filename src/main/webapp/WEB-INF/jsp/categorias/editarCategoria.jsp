<%@page import="org.TiendaWhisky2.model.Categoria" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Categoria</title>
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
<div id="contenedora" style="margin: 0 auto; width: 900px;">
    <form action="${pageContext.request.contextPath}/tienda/categorias/editar/" method="post">
        <input type="hidden" name="__method__" value="put" />

        <div class="clearfix" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h1>Editar Categoria</h1>
            <div>
                <input type="submit" value="Guardar" class="arriba" />
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
                <input name="nombre" value="<%= categoria.getNombre() %>" />
            </div>
        </div>

        <div class="clearfix" style="display: flex; justify-content: space-between; margin-top: 6px;">
            <div style="flex: 1;">
                <label>Descripcion</label>
            </div>
            <div style="flex: 2;">
                <textarea name="descripcion" rows="4" cols="50">
                    <%= categoria.getDescripcion() != null ? categoria.getDescripcion() : "" %>
                </textarea>
            </div>
        </div>

    </form>
</div>

            <%
            } else {
                response.sendRedirect("/tienda/categorias");
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

<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
</body>
</html>