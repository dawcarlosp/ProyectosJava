package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.ComentarioRepository;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import com.iesjuanbosco.ejemploweb.service.ComentarioService;
import com.iesjuanbosco.ejemploweb.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ComentarioService comentarioService;
    //Comentar producto
    @PostMapping("/productos/comentarios/new/{id}")
    public String comentar(@PathVariable Long id, @Valid Comentario comentario, BindingResult bindingResult, Model model){
        return this.comentarioService.comentar(id, comentario, bindingResult, model);
    }
    //Listar comentarios
    @GetMapping("/comentarios")
    public String findAll(Model model){
        model.addAttribute("comentarios", this.comentarioService.findAll());
        return "/comentario/comentario-list";
    }
    //Alta comentario
    @GetMapping("/comentarios/new")
    public String newComentarioVista(Model model){
        List<Producto> productos = new ArrayList<>();
        productos = this.productoService.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("comentario", new Comentario());
        return "/comentario/comentario-new";
    }
    @PostMapping("/comentarios/new")
    public String newComentario(@Valid Comentario comentario, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/comentario/comentario-new";
        }
        this.comentarioService.save(comentario);
        return "redirect:/comentarios";
    }
    //Eliminar comentario
    @GetMapping ("/comentarios/del/{id}")
    public String eliminarComentario(@PathVariable Long id){
        this.comentarioService.deleteById(id);
        return "redirect:/comentarios";
    }
    //editar comentario
    @GetMapping("/comentarios/edit/{id}")
    public String editComentario(Model model, @PathVariable Long id){
        Optional<Comentario> comentario = this.comentarioService.findById(id);
        if(comentario.isPresent()){
            model.addAttribute("comentario",comentario.get());
            model.addAttribute("productos", this.productoService.findAll());
            return "comentario/comentario-edit";
        }else{
            return "redirect:/comentarios";
        }
    }
    @PostMapping("/comentarios/edit/{id}")
    public String editComentarioP(Model model, Comentario comentario, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "comentario/comentario-edit";
        }
        this.comentarioService.save(comentario);
        return "redirect:/comentarios";
    }
    @GetMapping("/comentarios/view/{id}")
    public String verComentario(@PathVariable Long id, Model model){
        Optional<Comentario> comentario = this.comentarioService.findById(id);
        if(comentario.isPresent()){
            model.addAttribute("comentario", comentario.get());
            return "/comentario/comentario-view";
        }else{
            return "redirect:/comentarios";
        }
    }
}
