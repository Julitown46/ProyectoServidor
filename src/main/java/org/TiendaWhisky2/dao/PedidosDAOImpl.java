package org.TiendaWhisky2.dao;

import org.TiendaWhisky2.model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidosDAOImpl extends AbstractDAOImpl implements PedidosDAO {
    @Override
    public void create(Pedido pedido) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO pedidos (usuario_id, producto_id, nombreProducto, fechaPedido, cantidad) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setInt(idx++, pedido.getUsuario_id());
            ps.setInt(idx++, pedido.getProducto_id());
            ps.setString(idx++, pedido.getNombreProducto());
            ps.setString(idx++, pedido.getFechaPedido());
            ps.setInt(idx, pedido.getCantidad());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de pedido con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                pedido.setIdPedido(rsGenKeys.getInt(1));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeDb(conn, ps, null);
        }
    }

    @Override
    public List<Pedido> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Pedido> pedidos = new ArrayList<>();

        try {
            conn = connectDB();
            s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM pedidos");

            while (rs.next()) {
                Pedido pedido = new Pedido();
                int idx = 1;
                pedido.setIdPedido(rs.getInt(idx++));
                pedido.setUsuario_id(rs.getInt(idx++));
                pedido.setProducto_id(rs.getInt(idx++));
                pedido.setNombreProducto(rs.getString(idx++));
                pedido.setFechaPedido(rs.getString(idx++));
                pedido.setCantidad(rs.getInt(idx++));
                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeDb(conn, s, rs);
        }

        return pedidos;
    }

    @Override
    public Optional<Pedido> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT * FROM pedidos WHERE idPedido = ?");

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Pedido pedido = new Pedido();
                int idx = 1;
                pedido.setIdPedido(rs.getInt(idx++));
                pedido.setUsuario_id(rs.getInt(idx++));
                pedido.setProducto_id(rs.getInt(idx++));
                pedido.setNombreProducto(rs.getString(idx++));
                pedido.setFechaPedido(rs.getString(idx++));
                pedido.setCantidad(rs.getInt(idx++));
                return Optional.of(pedido);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeDb(conn, ps, rs);
        }

        return Optional.empty();
    }

    @Override
    public void update(Pedido pedido) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("UPDATE pedidos SET usuario_id = ?, producto_id = ?, nombreProducto = ?, fechaPedido = ?, cantidad = ? WHERE idPedido = ?");

            int idx = 1;
            ps.setInt(idx++, pedido.getUsuario_id());
            ps.setInt(idx++, pedido.getProducto_id ());
            ps.setString(idx++, pedido.getNombreProducto());
            ps.setString(idx++, pedido.getFechaPedido());
            ps.setInt(idx++, pedido.getCantidad());
            ps.setInt(idx++, pedido.getIdPedido());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("UPDATE de pedido con 0 filas actualizadas.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeDb(conn, ps, null);
        }
    }

    @Override
    public void delete(int id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("DELETE FROM pedidos WHERE idPedido = ?");

            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("DELETE de pedido con 0 filas eliminadas.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeDb(conn, ps, null);
        }
    }

    @Override
    public List<Pedido> getPedidoByIdUsuario(int idUsuario) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Pedido> listaPedidos = new ArrayList<>();

        try  {
            conn = connectDB();
            ps = conn.prepareStatement("SELECT * FROM pedidos WHERE usuario_id = ?");
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            while (rs.next()) {
                Pedido pedido = new Pedido();
                int idx = 1;
                pedido.setIdPedido(rs.getInt(idx++));
                pedido.setUsuario_id(rs.getInt(idx++));
                pedido.setProducto_id(rs.getInt(idx++));
                pedido.setNombreProducto(rs.getString(idx++));
                pedido.setFechaPedido(rs.getString(idx++));
                pedido.setCantidad(rs.getInt(idx++));
                listaPedidos.add(pedido);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            closeDb(conn, ps, rs);
        }

        return listaPedidos;
    }
}
