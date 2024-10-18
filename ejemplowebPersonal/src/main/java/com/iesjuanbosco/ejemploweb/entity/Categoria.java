package com.iesjuanbosco.ejemploweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //Patron Builder
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private String foto;
    //CascadeType.ALL
    //El crud que se haga en categoria, se aplica automaticamente a las intancias de producto
    @OneToMany(targetEntity = Producto.class, cascade = CascadeType.ALL, mappedBy = "categoria")
    //mappedBy = "categoria" le dice a JPA que esta relación está mapeada por el campo categoria en la clase Producto.
    //Esto significa que Categoria no es la dueña de la relación. No va a generar una columna o tabla adicional en la base de datos para esta relación. Solo "sigue" la relación gestionada por Producto.
    private List productos = new ArrayList();


    public Categoria(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List getProductos() {
        return productos;
    }

    public void setProductos(List productos) {
        this.productos = productos;
    }
}
