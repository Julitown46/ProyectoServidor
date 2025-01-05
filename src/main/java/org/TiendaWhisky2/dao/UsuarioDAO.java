package org.TiendaWhisky2.dao;

import java.util.List;
import java.util.Optional;
import org.TiendaWhisky2.model.Usuario;

public interface UsuarioDAO {
    public void create(Usuario usuario);
    public List<Usuario> getAll();
    public Optional<Usuario> find(int idUsuario);
    public void update(Usuario usuario);
    public void delete(int idUsuario);
    public Optional<Usuario> findByUsuario(String usuario);
}
