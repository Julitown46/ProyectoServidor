package org.TiendaWhisky2.dao;

import org.TiendaWhisky2.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidosDAO {
    public void create(Pedido pedido);
    public List<Pedido> getAll();
    public Optional<Pedido> find(int id);
    public void update(Pedido pedido);
    public void delete(int id);
    public List<Pedido> getPedidoByIdUsuario (int idUsuario);
}
