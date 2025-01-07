package org.TiendaWhisky2.servlet;

import jakarta.servlet.RequestDispatcher;
import org.TiendaWhisky2.dao.PedidosDAO;
import org.TiendaWhisky2.dao.PedidosDAOImpl;
import org.TiendaWhisky2.model.CarritoItem;
import org.TiendaWhisky2.model.Pedido;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.TiendaWhisky2.model.Usuario;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@WebServlet("/tienda/pedidos")
public class PedidoServlet extends HttpServlet {
        private final PedidosDAOImpl pedidosDAO = new PedidosDAOImpl();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            HttpSession session = req.getSession();
            Optional<Usuario> usuario = Optional.ofNullable((Usuario) session.getAttribute("usuario-logado"));

            // Verifica si hay un usuario autenticado en la sesión
            if (!usuario.isPresent()) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }

            // Obtén los pedidos del usuario autenticado
            Integer idUsuario = usuario.get().getIdUsuario();
            List<Pedido> listaPedidos = pedidosDAO.getPedidoByIdUsuario(idUsuario);

            // Pasar la lista de pedidos al JSP
            req.setAttribute("listaPedidos", listaPedidos);
            req.getRequestDispatcher("/WEB-INF/jsp/pedidos/historialPedido.jsp").forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            HttpSession session = req.getSession();
            RequestDispatcher dispatcher;
            List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
            Optional<Usuario> usuCat = Optional.ofNullable((Usuario) session.getAttribute("usuario-logado"));
            if (!usuCat.isPresent()) {
                System.out.println("INFO: El usuario no está autenticado o la sesión ha expirado.");
            }
            Integer idUsuario = usuCat.map(Usuario::getIdUsuario).orElse(null);


            if (carrito != null && !carrito.isEmpty()) {
                for (CarritoItem item : carrito) {
                    Pedido pedido = new Pedido();
                    pedido.setUsuario_id(idUsuario);
                    pedido.setProducto_id(item.getProducto().getIdProducto());
                    pedido.setNombreProducto(item.getProducto().getNombre());
                    pedido.setFechaPedido(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    pedido.setCantidad(item.getCantidad());
                    pedidosDAO.create(pedido);
                }
                session.removeAttribute("carrito"); // Vaciar carrito
            }

            req.getRequestDispatcher("/WEB-INF/jsp/pedidos/pedidoConfirmado.jsp").forward(req, resp);
        }
    }