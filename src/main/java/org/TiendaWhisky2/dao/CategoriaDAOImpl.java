package org.TiendaWhisky2.dao;

import org.TiendaWhisky2.model.Categoria;
import org.TiendaWhisky2.model.CategoriaDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaDAOImpl extends AbstractDAOImpl implements CategoriaDAO {

    @Override
    public void create(Categoria categoria) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();
            ps = conn.prepareStatement("INSERT INTO categorias (nombre) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setString(idx++, categoria.getNombre());
            ps.setString(idx, categoria.getDescripcion());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de categoria con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                categoria.setIdCategoria(rsGenKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public List<Categoria> getAll() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Categoria> listCat = new ArrayList<>();

        try {
            conn = connectDB();
            s = conn.createStatement();

            rs = s.executeQuery("SELECT * FROM categorias");
            while (rs.next()) {
                Categoria cat = new Categoria();
                int idx = 1;
                cat.setIdCategoria(rs.getInt(idx++));
                cat.setNombre(rs.getString(idx));
                listCat.add(cat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listCat;
    }

    @Override
    public Optional<Categoria> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT * FROM categorias WHERE idCategoria = ?");

            int idx =  1;
            ps.setInt(idx, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Categoria cat = new Categoria();
                idx = 1;
                cat.setIdCategoria(rs.getInt(idx++));
                cat.setNombre(rs.getString(idx++));
                cat.setDescripcion(rs.getString(idx));

                return Optional.of(cat);
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
    public void update(Categoria categoria) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("UPDATE categorias SET nombre = ?, descripcion = ? WHERE idCategoria = ?");
            int idx = 1;
            ps.setString(idx++, categoria.getNombre());
            ps.setString(idx++, categoria.getDescripcion()); // Corregido: asignamos el valor del segundo parámetro
            ps.setInt(idx, categoria.getIdCategoria()); // Asignamos el tercer parámetro

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Update de categoria con 0 registros actualizados.");

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
            ps = conn.prepareStatement("DELETE FROM categorias WHERE idCategoria = ?");

            int idx = 1;
            ps.setInt(idx, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de categoria con 0 registros eliminados.");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public Optional<Integer> getCantProducto(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT COUNT(*) FROM productos WHERE categoria_id = ?");

            int idx = 1;
            ps.setInt(idx, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                idx = 1;
                int count = rs.getInt(idx);
                return Optional.of(count);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

        return Optional.empty();
    }

    @Override
    public List<CategoriaDTO> getAllDTO() {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<CategoriaDTO> listCat = new ArrayList<>();

        try {
            conn = connectDB();
            s = conn.createStatement();

            rs = s.executeQuery("""
        SELECT idCategoria, nombre, descripcion, 
               (select count(*) from productos p where p.categoria_id=c.idCategoria) numero
        FROM categorias c
        """);
            while (rs.next()) {
                CategoriaDTO cat = new CategoriaDTO();
                int idx = 1;
                cat.setIdCategoria(rs.getInt(idx++));
                cat.setNombre(rs.getString(idx++));
                cat.setDescripcion(rs.getString(idx++));
                cat.setCantProductos(rs.getInt(idx));
                listCat.add(cat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listCat;
    }

    @Override
    public List<CategoriaDTO> getAllOrderDTO(String orden, String modo) {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<CategoriaDTO> listCat = new ArrayList<>();

        try {
            conn = connectDB();

            String consulta = "SELECT idCategoria codigo, nombre, descripcion, (select count(*) from productos p where p.categoria_id=c.idCategoria) numero FROM categorias c ORDER BY " + orden + " " + modo.toUpperCase();

            s = conn.createStatement();

            rs = s.executeQuery(consulta);
            while (rs.next()) {
                CategoriaDTO cat = new CategoriaDTO();
                int idx = 1;
                cat.setIdCategoria(rs.getInt(idx++));
                cat.setNombre(rs.getString(idx++));
                cat.setDescripcion(rs.getString(idx++));
                cat.setCantProductos(rs.getInt(idx));
                listCat.add(cat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, s, rs);
        }
        return listCat;
    }
}
