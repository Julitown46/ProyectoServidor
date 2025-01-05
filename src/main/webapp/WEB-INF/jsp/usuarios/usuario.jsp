<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>

<html>
<head>
    <title>Usuarios</title>
    <style>
        .clearfix::after {
            content: "";
            display: block;
            clear: both;
        }
        <%@ include file="/WEB-INF/jsp/fragmentos/estilos.jspf" %>
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>

<h2 style="display: flex; justify-content: center;">Usuarios</h2>
<div class="table-container">
            <%
                List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("listaUsuarios");

                Optional<Usuario> usuLogado = Optional.ofNullable((Usuario) session.getAttribute("usuario-logado"));
                String rolUsuario = usuLogado.map(Usuario::getRol).orElse("");
                boolean esAdmin = "Admin".equals(rolUsuario);

                if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
            %>

            <table>
                <thead>
                <tr>
                    <th>IdUsuario</th>
                    <th>Usuario</th>
                    <th>Rol</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (Usuario usuario : listaUsuarios) {
                %>
                <tr>
                    <td><%= usuario.getIdUsuario() %></td>

                    <td><%= usuario.getUsuario() %></td>

                    <td><%= usuario.getRol() %></td>
                    <td>
                        <% if (esAdmin) { %>

                        <a href="<%= request.getContextPath() + "/tienda/usuarios/editar/" + usuario.getIdUsuario() %>" class="editar">Editar</a>

                        <a href="<%= request.getContextPath() + "/tienda/usuarios/detalle/" + usuario.getIdUsuario() %>" class="detalle">Detalle</a>

                        <form style="display:inline" action="<%= request.getContextPath() + "/tienda/usuarios" %>"
                              method="post" onsubmit="return confirm('¿Estás seguro de que deseas eliminar este usuario?');">

                            <input type="hidden" name="codigo" value="<%= usuario.getIdUsuario() %>"/>
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
            <p>No hay usuarios disponibles.</p>
            <%
                }
            %>

        </div>

            <%
    if (esAdmin) {
%>
        <div style="margin-top: 10px;">
            <a href="<%= request.getContextPath() + "/tienda/usuarios/crear" %>" class="crear">Crear</a>
        </div>
            <%
    }
%>
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
</body>
</html>
