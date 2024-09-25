package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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
    //Pagina principal
    @GetMapping("/productos")
    public String findAll(Model model){
        List<Producto> productos = this.productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "product-list";
    }
    //Eliminar producto
    @GetMapping("/productos/del/{id}")
    public String deleteProductoVista(@PathVariable Long id){
        this.productoRepository.deleteById(id);
        return "redirect:/productos";
    }
    //Alta producto
    @GetMapping("/productos/new")
    public String newProductoVista(Model model){
        model.addAttribute("producto", new Producto());
        return "producto-new";
    }
    @PostMapping("/productos/new")
    public String newProducto(@Valid Producto producto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "producto-new";
        }
           this.productoRepository.save(producto);
           return "redirect:/productos";
    }
    //Modificar producto
    @GetMapping("/productos/edit/{id}")
        public String editProductoVista(@PathVariable Long id, Model modelo){
        Optional<Producto> pro = this.productoRepository.findById(id);
        if(pro.isPresent()){
            modelo.addAttribute("producto",pro.get());
            return "producto-edit";
        }else {
            return "redirect:/productos";
        }
    }
    @PostMapping("/productos/edit/{id}")
        public String editProducto(Producto producto){
            this.productoRepository.save(producto);
            return "redirect:/productos";
    }
    //Visualizar productos individualmente
    @GetMapping("/productos/view/{id}")
        public String view(@PathVariable Long id, Model model){
        Optional<Producto> producto = this.productoRepository.findById(id);
        if(producto.isPresent()){
            model.addAttribute("producto", producto.get());
            return "producto-view";
        }else{
            return "redirect:/productos";
        }
    }
}
