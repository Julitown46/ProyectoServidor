<%@page import="org.TiendaWhisky2.model.Usuario" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario</title>
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
    <form action="${pageContext.request.contextPath}/tienda/usuarios/editar/" method="post">
        <input type="hidden" name="__method__" value="put" />

        <div class="clearfix" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h1>Editar Usuario</h1>
            <div>
                <input type="submit" value="Guardar" class="arriba" />
            </div>
        </div>

        <%
            Optional<Usuario> optionalUsuario = (Optional<Usuario>) request.getAttribute("usuario");
            if (optionalUsuario.isPresent()) {
                Usuario usuario = optionalUsuario.get();
        %>

        <div class="clearfix" style="display: flex; justify-content: space-between; margin-top: 6px;">
            <div style="flex: 1;">
                <label>Codigo</label>
            </div>
            <div style="flex: 2;">
                <input name="codigo" value="<%= usuario.getIdUsuario() %>" readonly="readonly" />
            </div>
        </div>

        <div class="clearfix" style="display: flex; justify-content: space-between; margin-top: 6px;">
            <div style="flex: 1;">
                <label>Usuario</label>
            </div>
            <div style="flex: 2;">
                <input name="usuario" value="<%= usuario.getUsuario() %>" />
            </div>
        </div>

        <div class="clearfix" style="display: flex; justify-content: space-between; margin-top: 6px;">
            <div style="flex: 1;">
                <label>Password</label>
            </div>
            <div style="flex: 2;">
                <input name="password" value="" />
            </div>
        </div>

        <div style="margin-top: 6px;" class="clearfix">
            <div style="float: left;width: 50%">
                <label>Rol</label>
            </div>
            <div style="float: none;width: auto;overflow: hidden;">
                <select name="rol">
                    <option value="Admin" <%= "Admin".equals(usuario.getRol()) ? "selected" : "" %>>Admin</option>
                    <option value="Cliente" <%= "Cliente".equals(usuario.getRol()) ? "selected" : "" %>>Cliente</option>
                </select>
            </div>
        </div>


    </form>
</div>

<%
    } else {
        response.sendRedirect("/tienda/usuarios");
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