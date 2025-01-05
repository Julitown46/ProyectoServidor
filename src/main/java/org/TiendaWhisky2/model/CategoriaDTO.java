package org.TiendaWhisky2.model;

public class CategoriaDTO extends Categoria{
    private Integer cantProductos;

    public CategoriaDTO(Categoria categoria, Integer cantProductos) {
        this.setIdCategoria(categoria.getIdCategoria());
        this.setNombre(categoria.getNombre());
        this.cantProductos = cantProductos;
    }

    public CategoriaDTO() {}

    public Integer getCantProductos() {
        return cantProductos;
    }

    public void setCantProductos(Integer cantProductos) {
        this.cantProductos = cantProductos;
    }
}
