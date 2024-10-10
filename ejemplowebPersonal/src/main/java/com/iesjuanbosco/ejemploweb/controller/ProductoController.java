package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
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
    private CategoriaRepository categoriaRepository;

    public ProductoController(ProductoRepository repository, CategoriaRepository categoriaREpository){
        this.productoRepository = repository;
        this.categoriaRepository = categoriaREpository;
    }
    /*
  GET /productos
  Con la anotación GetMapping le indicamos a Spring que el siguiente metodo se va ejecutar cuando
     el usuario acceda a la URL anterior
  */
    //Pagina principal
    @GetMapping("/inicio")
    public String inicio(Model model){
        return "inicio";
    }
    //listar categorias
    @GetMapping("/categorias")
    public String findAllC(Model model){
        List<Categoria> categorias = this.categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        return "categoria-list";
    }
    //alta nueva categoria
    @GetMapping("/categorias/new")
    public String newCategoriaVista(Model model){
        model.addAttribute("categoria", new Categoria());
        return "categoria-new";
    }
    @PostMapping("/categorias/new")
    public String saveCategoria(@Valid Categoria categoria, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "categoria-new";
        }else{
            this.categoriaRepository.save(categoria);
            return "redirect:/categorias";
        }
    }
    //eliminar categoria
    @GetMapping("/categorias/del/{id}")
    public String deleteCategoria(@PathVariable Long id){
        this.categoriaRepository.deleteById(id);
        return "redirect:/categorias";
    }
    //editar categoria
    @GetMapping("/categorias/edit/{id}")
    public String editCategoria(@PathVariable Long id, Model model){
        Optional<Categoria> categoria = this.categoriaRepository.findById(id);
        if(categoria.isPresent()){
            model.addAttribute("categoria", categoria.get());
            return "categoria-edit";
        }else{
            return "redirect:/categorias";
        }
    }
    @PostMapping("/categorias/edit/{id}")
    public String editCategoria(Categoria categoria){
        this.categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }
    //Visualizar categoria
    @GetMapping("/categorias/view/{id}")
    public String viewCategorias(@PathVariable Long id, Model model){
        Optional<Categoria> categoria = this.categoriaRepository.findById(id);
        if(categoria.isPresent()){
            model.addAttribute("categoria", categoria.get());
            return "categoria-view";
        }else{
            return "redirect:/categorias";
        }
    }
    //Listar productos
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
        List<Categoria> categorias = this.categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
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
        List<Categoria> categorias = this.categoriaRepository.findAll();
        modelo.addAttribute("categorias", categorias);
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
