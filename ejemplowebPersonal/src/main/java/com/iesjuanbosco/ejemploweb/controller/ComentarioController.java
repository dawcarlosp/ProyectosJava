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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ComentarioController {
    private ComentarioRepository comentarioRepository;
    private ProductoRepository productoRepository;
    public ComentarioController(ComentarioRepository comentarioRepository, ProductoRepository productoRepository) {
        this.comentarioRepository = comentarioRepository;
        this.productoRepository = productoRepository;
    }
    @GetMapping("/comentarios")
    public String findAll(Model model){
        model.addAttribute("comentarios", comentarioRepository.findAll());
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
}
