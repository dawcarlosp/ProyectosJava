package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import com.iesjuanbosco.ejemploweb.repository.ComentarioRepository;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//Para que Spring sepa que esta clase es un controlador tenemos que añadir la anotación controller antes de la clase
@Controller//Anotación que le indica a Spring que esta clase es un controlador
public class ProductoController {
    //Para acceder al repositorio creamos una propiedad y la asignamos en el contructor
    private ProductoRepository productoRepository;
    private CategoriaRepository categoriaRepository;
    private ComentarioRepository comentarioRepository;
    public ProductoController(ProductoRepository repository, CategoriaRepository categoriaREpository, ComentarioRepository comentarioRepository){
        this.productoRepository = repository;
        this.categoriaRepository = categoriaREpository;
        this.comentarioRepository = comentarioRepository;
    }
    /*
  GET /productos
  Con la anotación GetMapping le indicamos a Spring que el siguiente metodo se va ejecutar cuando
     el usuario acceda a la URL anterior
  */

    //Listar productos
    @GetMapping("/productos/")
    public String findAll(Model model){
        List<Producto> productos = this.productoRepository.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", this.categoriaRepository.findAll());
        return "producto/product-list";
    }
    //Listar productos por categoria
    @GetMapping("/productos/{id}")
    public String findAll(Model model, @PathVariable Long id){
        Optional <Categoria> categoria = this.categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            List<Producto> productos = this.productoRepository.findByCategoria(categoria.get());
            model.addAttribute("productos", productos);
            model.addAttribute("selectedCategoriaId", id);
            model.addAttribute("categorias", this.categoriaRepository.findAll());
            return "/producto/product-list";
        }else{
            return "redirect:/productos";
        }

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
        List<Categoria> categorias = this.categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        model.addAttribute("producto", new Producto());
        return "/producto/producto-new";
    }
    @PostMapping("/productos/new")
    public String newProducto(@Valid Producto producto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/producto/producto-new";
        }
           this.productoRepository.save(producto);
           return "redirect:/productos";
    }
    //Modificar producto
    @GetMapping("/productos/edit/{id}")
        public String editProductoVista(@PathVariable Long id, Model modelo){
        Optional<Producto> pro = this.productoRepository.findById(id);
        List<Categoria> categorias = this.categoriaRepository.findAll();
        modelo.addAttribute("categorias", categorias);
        if(pro.isPresent()){
            modelo.addAttribute("producto",pro.get());
            return "/producto/producto-edit";
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
            model.addAttribute("comentario" , new Comentario());
            return "/producto/producto-view";
        }else{
            return "redirect:/productos";
        }
    }
    @PostMapping("/productos/view/{id}")
    public String comentar(@PathVariable Long id,@Valid Comentario comentario, BindingResult bindingResult, Model model){
        Optional<Producto> producto = this.productoRepository.findById(id);
        if(producto.isPresent()) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("producto",producto.get());
                return "/producto/producto-view";
            }
            comentario.setProducto(producto.get());
            comentario.setFecha(LocalDate.now());
            this.comentarioRepository.save(comentario);
            return "redirect:/productos/view/" + id;
        }
        this.comentarioRepository.save(comentario);
        return "redirect:/productos";
    }
}
