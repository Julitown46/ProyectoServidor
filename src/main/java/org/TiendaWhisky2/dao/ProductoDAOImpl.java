package org.TiendaWhisky2.dao;

import org.TiendaWhisky2.model.CategoriaDTO;
import org.TiendaWhisky2.model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDAOImpl extends AbstractDAOImpl implements ProductoDAO {

    @Override
    public void create(Producto producto) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO productos (nombre,precio,descripcion,categoria_id) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setString(idx++, producto.getNombre());
            ps.setDouble(idx++, producto.getPrecio());
            ps.setString(idx++, producto.getDescripcion());
            ps.setInt(idx,producto.getCategoria_id());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de producto con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                producto.setIdProducto(rsGenKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public List<Producto> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Producto> listProd = new ArrayList<>();

        try {
            conn = connectDB();
            s = conn.createStatement();

            rs = s.executeQuery("SELECT * FROM productos");
            while (rs.next()) {
                Producto prod = new Producto();
                int idx = 1;
                prod.setIdProducto(rs.getInt(idx++));
                prod.setNombre(rs.getString(idx++));
                prod.setPrecio(rs.getDouble(idx++));
                prod.setDescripcion(rs.getString(idx++));
                prod.setCategoria_id(rs.getInt(idx++));
                listProd.add(prod);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listProd;
    }

    @Override
    public Optional<Producto> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT * FROM productos WHERE idProducto = ?");

            int idx =  1;
            ps.setInt(idx, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Producto prod = new Producto();
                idx = 1;
                prod.setIdProducto(rs.getInt(idx++));
                prod.setNombre(rs.getString(idx++));
                prod.setPrecio(rs.getDouble(idx++));
                prod.setDescripcion(rs.getString(idx++));
                prod.setCategoria_id(rs.getInt(idx));

                return Optional.of(prod);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

        return Optional.empty();
    }

    @Override
    public void update(Producto producto) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("UPDATE productos SET nombre = ?,precio = ?, descripcion = ?, categoria_id = ? WHERE idProducto = ?");
            int idx = 1;
            ps.setString(idx++, producto.getNombre());
            ps.setDouble(idx++, producto.getPrecio());
            ps.setString(idx++, producto.getDescripcion());
            ps.setInt(idx++, producto.getCategoria_id());
            ps.setInt(idx, producto.getIdProducto());

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Update de producto con 0 registros actualizados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public void delete(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("DELETE FROM productos WHERE idProducto = ?");
            int idx = 1;
            ps.setInt(idx, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de producto con 0 registros eliminados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

    }

    @Override
    public List<Producto> getAllOrder(String orden, String modo) {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Producto> listProd = new ArrayList<>();

        try {
            conn = connectDB();

            String consulta = "SELECT idProducto codigo, nombre, precio, descripcion, categoria_id FROM productos p ORDER BY " + orden + " " + modo.toUpperCase();

            s = conn.createStatement();

            rs = s.executeQuery(consulta);
            while (rs.next()) {
                Producto prod = new Producto();
                int idx = 1;
                prod.setIdProducto(rs.getInt(idx++));
                prod.setNombre(rs.getString(idx++));
                prod.setPrecio(rs.getDouble(idx++));
                prod.setDescripcion(rs.getString(idx++));
                prod.setCategoria_id(rs.getInt(idx));
                listProd.add(prod);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listProd;
    }
}