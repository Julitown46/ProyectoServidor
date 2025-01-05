package org.TiendaWhisky2.dao;

import java.util.List;
import java.util.Optional;
import org.TiendaWhisky2.model.Categoria;
import org.TiendaWhisky2.model.CategoriaDTO;

public interface CategoriaDAO {
    public void create(Categoria categoria);
    public List<Categoria> getAll();
    public Optional<Categoria> find(int id);
    public void update(Categoria categoria);
    public void delete(int id);
    public Optional<Integer> getCantProducto(int id);
    public List<CategoriaDTO> getAllDTO();
    public List<CategoriaDTO> getAllOrderDTO(String orden, String modo);
}
