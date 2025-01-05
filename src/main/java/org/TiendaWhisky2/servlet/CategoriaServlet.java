package org.TiendaWhisky2.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.TiendaWhisky2.model.Categoria;
import org.TiendaWhisky2.dao.CategoriaDAO;
import org.TiendaWhisky2.dao.CategoriaDAOImpl;
import org.TiendaWhisky2.model.CategoriaDTO;

@WebServlet(name = "categoriasServlet", value = "/tienda/categorias/*")
public class CategoriaServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;

        String pathInfo = request.getPathInfo(); //

        if (pathInfo == null || "/".equals(pathInfo)) {
            CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
            String ordenarPor = request.getParameter("ordenar-por");
            String modoOrdenar = request.getParameter("modo-ordenar");

            // GET /categorias/ o /categorias
            List<CategoriaDTO> listaCategoriasDTO;

            if (ordenarPor != null && modoOrdenar != null) {
                listaCategoriasDTO = categoriaDAO.getAllOrderDTO(ordenarPor, modoOrdenar);
            } else {
                listaCategoriasDTO = categoriaDAO.getAllDTO();
            }

            request.setAttribute("listaCategorias", listaCategoriasDTO);

            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/categoria.jsp");

        } else {
            // GET
            // /categorias/{id}
            // /categorias/{id}/
            // /categorias/edit/{id}
            // /categorias/edit/{id}/
            // /categorias/crear
            // /categorias/crear/

            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {
                // GET /categorias/crear
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/crearCategoria.jsp");

            } else if (pathParts.length == 3 && "detalle".equals(pathParts[1])) {
                CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
                // GET /categorias/{id}
                try {
                    request.setAttribute("categoria", categoriaDAO.find(Integer.parseInt(pathParts[2])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/detalleCategoria.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/categoria.jsp");
                }
            } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
                CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

                // GET /categorias/editar/{id}
                try {
                    request.setAttribute("categoria", categoriaDAO.find(Integer.parseInt(pathParts[2])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/editarCategoria.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/categoria.jsp");
                }
            } else {
                System.out.println("Opción POST no soportada.");
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/categorias/categoria.jsp");
            }
        }

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;
        String __method__ = request.getParameter("__method__");

        if (__method__ == null) {
            // Crear uno nuevo
            CategoriaDAO catDAO = new CategoriaDAOImpl();

            String nombre = request.getParameter("nombre");
            Categoria nuevaCat = new Categoria();
            nuevaCat.setNombre(nombre);
            catDAO.create(nuevaCat);

        } else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {
            // Actualizar uno existente
            doPut(request, response);

        } else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {
            // Borrar uno existente
            doDelete(request, response);
        } else {
            System.out.println("Opción POST no soportada.");
        }

        response.sendRedirect(request.getContextPath() + "/tienda/categorias");
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoriaDAO catDAO = new CategoriaDAOImpl();
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        Categoria cat = new Categoria();

        try {
            int id = Integer.parseInt(codigo);
            cat.setIdCategoria(id);
            cat.setNombre(nombre);
            cat.setDescripcion(descripcion);
            catDAO.update(cat);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
    {
        RequestDispatcher dispatcher;
        CategoriaDAO catDAO = new CategoriaDAOImpl();
        String codigo = request.getParameter("codigo");

        try {
            int id = Integer.parseInt(codigo);

            catDAO.delete(id);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

}
