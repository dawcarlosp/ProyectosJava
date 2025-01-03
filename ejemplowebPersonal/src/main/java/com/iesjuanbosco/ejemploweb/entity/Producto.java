package com.iesjuanbosco.ejemploweb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity //Especifica que esta clase es una entidad
//Indica que la tabla en la base de datos relacionada con esta entidad
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //Patron Builder
@Table(name = "productos")
public class Producto {
    @Id //Esta anotación especifica que este campo va a ser la clave principal de la tabla en la base de datos
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Esta anotación especifica que la clabe primaria sea "auto-increment"
    private Long id;
    @NotEmpty(message="El título es obligatorio")
    @Size(min = 1, message = "El titulo no puede estar vacio")
    private String titulo;
    @NotNull(message = "Cantidad obligatoria")
    private Integer cantidad;
    @NotNull(message = "El precio no puede ser nulo")
    @Min(value = 0, message = "El precio debe ser positivo")
    private Double precio;
    @ManyToOne(targetEntity = Categoria.class)
   @JoinColumn(name = "id_categoria")
    @NotNull(message = "Es obligatorio seleccionar una categoria")
    private Categoria categoria;
    @OneToMany(targetEntity = Comentario.class, cascade = CascadeType.ALL, mappedBy = "producto")
    private List<Comentario> comentarios = new ArrayList<Comentario>();
    @OneToMany(targetEntity =  FotoProducto.class, cascade = CascadeType.ALL, mappedBy = "producto")
    private List<FotoProducto> fotos = new ArrayList<>();
    public Producto(Long id, String titulo, Integer cantidad, Double precio) {
        this.id = id;
        this.titulo = titulo;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    public Double getImporte(){
        return this.precio*this.cantidad;
    }
    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                '}';
    }
}
