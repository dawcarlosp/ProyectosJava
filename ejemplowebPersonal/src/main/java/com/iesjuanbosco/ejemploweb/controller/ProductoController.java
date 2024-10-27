package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.entity.Foto;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import com.iesjuanbosco.ejemploweb.repository.ComentarioRepository;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import com.iesjuanbosco.ejemploweb.service.CategoriaService;
import com.iesjuanbosco.ejemploweb.service.ComentarioService;
import com.iesjuanbosco.ejemploweb.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Para que Spring sepa que esta clase es un controlador tenemos que añadir la anotación controller antes de la clase
@Controller//Anotación que le indica a Spring que esta clase es un controlador
public class ProductoController {
    //Para acceder al repositorio creamos una propiedad y la asignamos en el contructor
    @Autowired
    private ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private ComentarioService comentarioService;
    /*
  GET /productos
  Con la anotación GetMapping le indicamos a Spring que el siguiente metodo se va ejecutar cuando
     el usuario acceda a la URL anterior
  */

    //Listar productos
    @GetMapping("/productos/")
    public String findAll(Model model){
        List<Producto> productos = this.productoService.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", this.categoriaService.findAll());
        model.addAttribute("importe", this.productoService.importe());
        return "producto/product-list";
    }
    //Listar productos por categoria
    @GetMapping("/productos/{id}")
    public String findAll(Model model, @PathVariable Long id){
        Optional <Categoria> categoria = this.categoriaService.findById(id);
        if (categoria.isPresent()) {
            List<Producto> productos = this.productoService.findByCategoria(categoria.get());
            model.addAttribute("productos", productos);
            model.addAttribute("selectedCategoriaId", id);
            model.addAttribute("importeCategoria", this.productoService.importeCategoria(id));
            model.addAttribute("categorias", this.categoriaService.findAll());
            return "/producto/product-list";
        }else{
            return "redirect:/productos";
        }

    }
    //Eliminar producto
    @GetMapping("/productos/del/{id}")
    public String deleteProductoVista(@PathVariable Long id){
        this.productoService.deleteById(id);
        return "redirect:/productos/";
    }
    //Alta producto
    @GetMapping("/productos/new")
    public String newProductoVista(Model model){
        List<Categoria> categorias = this.categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        model.addAttribute("producto", new Producto());
        return "/producto/producto-new";
    }
    @PostMapping("/productos/new")
    public String newProducto(@Valid Producto producto, BindingResult bindingResult, MultipartFile files[]){
        if(bindingResult.hasErrors()){
            return "/producto/producto-new";
        }
           this.productoService.guardarProducto(producto,files);
           return "redirect:/productos/";
    }
    //Modificar producto
    @GetMapping("/productos/edit/{id}")
        public String editProductoVista(@PathVariable Long id, Model modelo){
        Optional<Producto> pro = this.productoService.findById(id);
        List<Categoria> categorias = this.categoriaService.findAll();
        modelo.addAttribute("categorias", categorias);
        if(pro.isPresent()){
            modelo.addAttribute("producto",pro.get());
            //Para las fotos
            List<Foto> fotosRutaCompleta = pro.get().getFotos();
            List<Foto> fotos = new ArrayList<>();
            for(Foto foto : fotosRutaCompleta){
                String trozos[] = foto.getRuta().split("/");
                String valido = trozos[1]+ '/' + trozos[2];
                Foto fotoN = new Foto();
                fotoN.setRuta(valido);
                fotoN.setProducto(foto.getProducto());
                fotos.add(fotoN);
            }
            modelo.addAttribute("fotos",fotos);
            return "/producto/producto-edit";
        }else {
            return "redirect:/productos/";
        }
    }
    @PostMapping("/productos/edit/{id}")
        public String editProducto(Producto producto, MultipartFile files[]){
            this.productoService.guardarProducto(producto,files);
            return "redirect:/productos/";
    }
    //Visualizar productos individualmente
    @GetMapping("/productos/view/{id}")
        public String view(@PathVariable Long id, Model model){
        Optional<Producto> producto = this.productoService.findById(id);
        List<Foto> fotosRutaCompleta = producto.get().getFotos();
        List<Foto> fotos = new ArrayList<>();
        for(Foto foto : fotosRutaCompleta){
            String trozos[] = foto.getRuta().split("/");
            String valido = trozos[1]+ '/' + trozos[2];
            Foto fotoN = new Foto();
            fotoN.setRuta(valido);
            fotoN.setProducto(foto.getProducto());
            fotos.add(fotoN);
        }
        if(producto.isPresent()){
            model.addAttribute("producto", producto.get());
            model.addAttribute("comentario" , new Comentario());
            model.addAttribute("comentarios", this.comentarioService.findByProductoOrderByFechaDesc(producto.get()));
            model.addAttribute("fotos", fotos);
            return "/producto/producto-view";
        }else{
            return "redirect:/productos/";
        }
    }
}
