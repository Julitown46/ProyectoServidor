package org.TiendaWhisky2.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.TiendaWhisky2.dao.CategoriaDAO;
import org.TiendaWhisky2.dao.CategoriaDAOImpl;
import org.TiendaWhisky2.dao.ProductoDAO;
import org.TiendaWhisky2.dao.ProductoDAOImpl;
import org.TiendaWhisky2.model.Categoria;
import org.TiendaWhisky2.model.CategoriaDTO;
import org.TiendaWhisky2.model.Producto;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "productosServlet", value = "/tienda/productos/*")
public class ProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher;

        String pathInfo = request.getPathInfo(); //

        if (pathInfo == null || "/".equals(pathInfo)) {
            ProductoDAO productoDAO = new ProductoDAOImpl();
            String ordenarPor = request.getParameter("ordenar-por");
            String modoOrdenar = request.getParameter("modo-ordenar");

            List<Producto> listaProd;

            if (ordenarPor != null && modoOrdenar != null) {
                listaProd = productoDAO.getAllOrder(ordenarPor, modoOrdenar);
            } else {
                listaProd = productoDAO.getAll();
            }

            request.setAttribute("listaProductos", listaProd);
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/producto.jsp");

        } else {
            // GET
            // 		/productos/{id}
            // 		/productos/{id}/
            // 		/productos/edit/{id}
            // 		/productos/edit/{id}/
            // 		/productos/crear
            // 		/productos/crear/

            pathInfo = pathInfo.replaceAll("/$", "");
            String[] pathParts = pathInfo.split("/");

            if (pathParts.length == 2 && "crear".equals(pathParts[1])) {

                // GET
                // /productos/crear
                CategoriaDAO catDAO = new CategoriaDAOImpl();
                request.setAttribute("categoria", catDAO.getAll());
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/crearProducto.jsp");


            } else if (pathParts.length == 3 && "detalle".equals(pathParts[1])) {
                ProductoDAO prodDAO = new ProductoDAOImpl();
                // GET
                // /productos/{id}
                try {
                    request.setAttribute("producto",prodDAO.find(Integer.parseInt(pathParts[2])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/detalleProducto.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/producto.jsp");
                }

            } else if (pathParts.length == 3 && "editar".equals(pathParts[1])) {
                ProductoDAO prodDAO = new ProductoDAOImpl();
                CategoriaDAO catDAO = new CategoriaDAOImpl();
                // GET
                // /productos/editar/{id}
                try {
                    request.setAttribute("categoria", catDAO.getAll());
                    request.setAttribute("producto",prodDAO.find(Integer.parseInt(pathParts[2])));
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/editarProducto.jsp");

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/producto.jsp");
                }


            } else {

                System.out.println("Opción POST no soportada.");
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/productos/producto.jsp");

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
            ProductoDAO prodDAO = new ProductoDAOImpl();

            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            Double precio = Double.parseDouble(request.getParameter("precio"));
            Integer categoria_id = Integer.parseInt(request.getParameter("categoria_id"));
            Producto nuevoProd = new Producto();


            nuevoProd.setNombre(nombre);
            nuevoProd.setPrecio(precio);
            nuevoProd.setDescripcion(descripcion);
            nuevoProd.setCategoria_id(categoria_id);
            prodDAO.create(nuevoProd);

        } else if (__method__ != null && "put".equalsIgnoreCase(__method__)) {
            // Actualizar uno existente
            doPut(request, response);

        } else if (__method__ != null && "delete".equalsIgnoreCase(__method__)) {
            // Borrar
            doDelete(request, response);

        } else {
            System.out.println("Opción POST no soportada.");
        }

        response.sendRedirect(request.getContextPath() + "/tienda/productos");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        Double precio = Double.parseDouble(request.getParameter("precio"));
        String descripcion = request.getParameter("descripcion");
        Integer idProd = Integer.parseInt(request.getParameter("codigo"));
        Integer categoria_id = Integer.parseInt(request.getParameter("categoria_id"));
        Producto nuevoProd = new Producto();
        ProductoDAO prodDAO = new ProductoDAOImpl();

        try {
            nuevoProd.setIdProducto(idProd);
            nuevoProd.setNombre(nombre);
            nuevoProd.setPrecio(precio);
            nuevoProd.setDescripcion(descripcion);
            nuevoProd.setCategoria_id(categoria_id);
            prodDAO.update(nuevoProd);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
    {
        RequestDispatcher dispatcher;
        ProductoDAO productoDAO = new ProductoDAOImpl();
        String codigo = request.getParameter("codigo");

        try {

            int id = Integer.parseInt(codigo);

            productoDAO.delete(id);

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }
}