package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;

//Para que Spring sepa que esta clase es un controlador tenemos que añadir la anotación controller antes de la clase
@Controller//Anotación que le indica a Spring que esta clase es un controlador
public class ProductoController {
    //Para acceder al repositorio creamos una propiedad y la asignamos en el contructor
    private ProductoRepository productoRepository;
    public ProductoController(ProductoRepository repository){
        this.productoRepository = repository;
    }
    /*
  GET /productos
  Con la anotación GetMapping le indicamos a Spring que el siguiente metodo se va ejecutar cuando
     el usuario acceda a la URL anterior
  */
    @GetMapping("/productos")
    public String findAll(Model model){
        //Antes se regresaba el html aqui, ya no, este comentario es personal
        //Aqui nos quedamos, el objetivo es mostrar los productos en el html, pero podriamos comprobar que funcionaba simplemente haciendo el return de product-list y eliminando las dos lineas de abajo de momento
       List<Producto> lista = this.productoRepository.findAll();
       model.addAttribute("productos",lista);
        return "product-list";
    }
    //El Post lo borro luego, no se va a usar de momento
    @PostMapping("/productos")
    public String addProducto(){
        return "product-add";
    }
    @GetMapping("/productos/add")
    public String add(){
        List<Producto> productos = new ArrayList<Producto>();
            Producto p1 = new Producto(null,"Producto 1",20,45.5);
            Producto p2 = new Producto(null,"Producto 2",10,35.5);
            Producto p3 = new Producto(null,"Producto 3",22,55.5);
            Producto p4 = new Producto(null,"Producto 4",70,65.5);
            productos.add(p1);
            productos.add(p2);
            productos.add(p3);
            productos.add(p4);
            //Guardamos todos los productos en la base de datos utilizando el objeto productoRepository
            this.productoRepository.saveAll(productos);
            //Redirige al controlador /productos
        return "redirect:/productos";
    }
}
