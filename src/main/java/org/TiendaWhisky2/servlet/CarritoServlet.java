package org.TiendaWhisky2.servlet;

import org.TiendaWhisky2.dao.ProductoDAO;
import org.TiendaWhisky2.dao.ProductoDAOImpl;
import org.TiendaWhisky2.model.CarritoItem;
import org.TiendaWhisky2.model.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/tienda/carrito")
public class CarritoServlet extends HttpServlet {
    private ProductoDAO productoDAO = new ProductoDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }

        int totalCantidad = carrito.stream().mapToInt(CarritoItem::getCantidad).sum();
        session.setAttribute("cantCart", totalCantidad);

        req.setAttribute("carrito", carrito);
        req.getRequestDispatcher("/WEB-INF/jsp/carrito/carrito.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }

        String action = req.getParameter("action");

        if ("eliminar".equals(action)) {

            try {
                int productoId = Integer.parseInt(req.getParameter("productoId"));
                carrito.removeIf(item -> item.getProducto().getIdProducto() == productoId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            try {
                int productoId = Integer.parseInt(req.getParameter("productoId"));
                int cantidad = Integer.parseInt(req.getParameter("cantidad"));

                Producto producto = productoDAO.find(productoId).orElse(null);

                if (producto != null) {
                    boolean encontrado = false;
                    for (CarritoItem item : carrito) {
                        if (item.getProducto().getIdProducto() == productoId) {
                            item.setCantidad(item.getCantidad() + cantidad);
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        carrito.add(new CarritoItem(producto, cantidad));
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        int totalCantidad = carrito.stream().mapToInt(CarritoItem::getCantidad).sum();
        session.setAttribute("cantCart", totalCantidad);

        resp.sendRedirect(req.getContextPath() + "/tienda/carrito");
    }
}