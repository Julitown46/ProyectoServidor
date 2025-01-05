package org.TiendaWhisky2.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.TiendaWhisky2.dao.UsuarioDAO;
import org.TiendaWhisky2.dao.UsuarioDAOImpl;
import org.TiendaWhisky2.model.Usuario;
import org.TiendaWhisky2.util.Util;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;


@WebServlet(name = "usuariosServlet", value = "/tienda/usuarios/*")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;

        String pathInfo = request.getPathInfo(); //

        if (pathInfo == null || "/".equals(pathInfo)) {
            UsuarioDAO userDAO = new UsuarioDAOImpl();

            //GET
            //	/usuarios/
            //	/usuarios

            request.setAttribute("listaUsuarios", userDAO.getAll());
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/usuario.jsp");

        } else if ("/login".equals(pathInfo)) {
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/login.jsp");
        } else {
            // GET
            // 		/usuarios/{id}
            // 		/usuarios/{id}/
            // 		/usuarios/edit/{id}
            // 		/usuarios/edit/{id}/
            // 		/usuarios/crear
            // 		/usuarios/crear/

            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {

                UsuarioDAO userDAO = new UsuarioDAOImpl();
                List<Usuario> usuarios = userDAO.getAll();
                request.setAttribute("usuarios", usuarios);

                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/crearUsuario.jsp");


            } else if (pathParts.length == 2) {
                UsuarioDAO userDAO = new UsuarioDAOImpl();

                try {
                    request.setAttribute("usuario",userDAO.find(Integer.parseInt(pathParts[1])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/detalleUsuario.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/usuario.jsp");
                }

            } else if (pathParts.length == 3 && "editar".equals(pathParts[1]) ) {
                UsuarioDAO userDAO = new UsuarioDAOImpl();

                try {
                    request.setAttribute("usuario", userDAO.find(Integer.parseInt(pathParts[2])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/editarUsuario.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/usuario.jsp");
                }

            } else {

                System.out.println("Opción POST no soportada.");
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/usuarios/usuario.jsp");

            }

        }

        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;
        String __method__ = request.getParameter("__method__");

        System.out.println(__method__);

        if (__method__ == null) {
            String usuario = request.getParameter("usuario");
            String password = request.getParameter("password");
            String rol = request.getParameter("rol");
            Usuario nuevoUser = new Usuario();
            nuevoUser.setUsuario(usuario);

            try {
                nuevoUser.setPassword(Util.hashPassword(password));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            nuevoUser.setRol(rol);

            UsuarioDAO UsuarioDAO = new UsuarioDAOImpl();
            UsuarioDAO.create(nuevoUser);

        } else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {
            // Actualizar uno existente
            doPut(request, response);

        } else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {
            // Borrar uno existente
            doDelete(request, response);

        } else if (__method__ != null && "login".equalsIgnoreCase(__method__)) {
            doLogin(request, response);
            return;
        } else if (__method__ != null && "logout".equalsIgnoreCase(__method__)) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect(request.getContextPath());
            return;
        } else {
            System.out.println("Opción POST no soportada.");
        }

        response.sendRedirect(request.getContextPath() + "/tienda/usuarios");
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        String hashedPassword;
        UsuarioDAO UsuDAO = new UsuarioDAOImpl();

        Optional<Usuario> nuevo = UsuDAO.findByUsuario(usuario);

        if (nuevo.isPresent()) {

            try {
                hashedPassword = Util.hashPassword(password);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            Usuario usu = nuevo.get();

            if (usu.getPassword().equals(hashedPassword)) {
                HttpSession session = request.getSession(true);
                session.setAttribute("usuario-logado", usu);
                response.sendRedirect(request.getContextPath());
            } else {
                System.out.println("Password no encontrado");
                response.sendRedirect(request.getContextPath());
            }
        } else {
            System.out.println("Usuario no encontrado");
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        String rol = request.getParameter("rol");
        Integer id = Integer.parseInt(request.getParameter("codigo"));
        Usuario nuevoUser = new Usuario();
        UsuarioDAO userDAO = new UsuarioDAOImpl();

        try {
            nuevoUser.setIdUsuario(id);
            nuevoUser.setUsuario(usuario);
            try {
                nuevoUser.setPassword(Util.hashPassword(password));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            nuevoUser.setRol(rol);
            userDAO.update(nuevoUser);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
    {
        RequestDispatcher dispatcher;
        UsuarioDAO UsuarioDAO = new UsuarioDAOImpl();
        String codigo = request.getParameter("codigo");

        try {

            int id = Integer.parseInt(codigo);

            UsuarioDAO.delete(id);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }
}
