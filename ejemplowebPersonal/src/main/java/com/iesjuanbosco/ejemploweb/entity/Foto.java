package com.iesjuanbosco.ejemploweb.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Controller;

@Controller
@Entity
public class Foto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ruta;
    @ManyToOne(targetEntity = Producto.class)
    @JoinColumn(name = "id_producto")
    private Producto producto;
}
