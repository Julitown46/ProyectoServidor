package org.TiendaWhisky2.dao;

import java.util.List;
import java.util.Optional;

import org.TiendaWhisky2.model.CategoriaDTO;
import org.TiendaWhisky2.model.Producto;

public interface ProductoDAO {
    public void create(Producto producto);
    public List<Producto> getAll();
    public Optional<Producto> find(int id);
    public void update(Producto producto);
    public void delete(int id);
    public List<Producto> getAllOrder(String orden, String modo);
}
