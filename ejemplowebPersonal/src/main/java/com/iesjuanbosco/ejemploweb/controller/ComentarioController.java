package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.ComentarioRepository;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ComentarioController {
    private ComentarioRepository comentarioRepository;
    private ProductoRepository productoRepository;
    public ComentarioController(ComentarioRepository comentarioRepository, ProductoRepository productoRepository) {
        this.comentarioRepository = comentarioRepository;
        this.productoRepository = productoRepository;
    }
    //Comentar producto
    @PostMapping("/productos/comentarios/new/{id}")
    public String comentar(@PathVariable Long id, @Valid Comentario comentario, BindingResult bindingResult, Model model){
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
    //Listar comentarios
    @GetMapping("/comentarios")
    public String findAll(Model model){
        model.addAttribute("comentarios", this.comentarioRepository.findAll());
        return "/comentario/comentario-list";
    }
    //Alta comentario
    @GetMapping("/comentarios/new")
    public String newComentarioVista(Model model){
        List<Producto> productos = new ArrayList<>();
        productos = this.productoRepository.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("comentario", new Comentario());
        return "/comentario/comentario-new";
    }
    @PostMapping("/comentarios/new")
    public String newComentario(@Valid Comentario comentario, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/comentario/comentario-new";
        }
        this.comentarioRepository.save(comentario);
        return "redirect:/comentarios";
    }
    //Eliminar comentario
    @GetMapping ("/comentarios/del/{id}")
    public String eliminarComentario(@PathVariable Long id){
        this.comentarioRepository.deleteById(id);
        return "redirect:/comentarios";
    }
}
