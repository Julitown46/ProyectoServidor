<%@ page import="org.TiendaWhisky2.model.Usuario" %>
<%@ page import="java.util.Optional" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Inicio</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
  <style>
    main {
      display: flex;
      align-items: center;
      justify-content: center;
    }
    main div {
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    main p {
      margin-top: 45%;
      font-size: 20px;
    }
    <%@ include file="/WEB-INF/jsp/fragmentos/estilos.jspf" %>
  </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/fragmentos/header.jspf" %>
<%@ include file="/WEB-INF/jsp/fragmentos/nav.jspf" %>

<%
  Optional<Usuario> usuIdx = Optional.ofNullable((Usuario)session.getAttribute("usuario-logado"));
  if (usuIdx.isPresent()) {
%>
<!-- Usuario Logueado -->
<main>
  <div>
    <p>Bienvenido, <%= usuIdx.get().getUsuario() %></p>
  </div>
</main>

<div style="margin-top: 10px;">
  <a href="<%= request.getContextPath() + "/tienda/categorias" %>" class="crear">Ver las categorias</a>
</div>

<div style="margin-top: 10px;">
  <a href="<%= request.getContextPath() + "/tienda/productos" %>" class="crear">Ver los productos</a>
</div>
<%
} else {
%>
<main>
  <div>
    <p>Inicia sesión o registrate para mostrar el contenido</p>
  </div>
</main>
<%
  }
%>


<%@include file="/WEB-INF/jsp/fragmentos/bootstrap.jspf"%>
<%@ include file="/WEB-INF/jsp/fragmentos/footer.jspf" %>
</body>
</html>