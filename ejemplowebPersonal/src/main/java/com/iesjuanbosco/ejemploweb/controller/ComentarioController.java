package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.repository.ComentarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComentarioController {
    private ComentarioRepository comentarioRepository;
    public ComentarioController(ComentarioRepository comentarioRepository){
        this.comentarioRepository = comentarioRepository;
    }
    @GetMapping("/comentarios")
    public String findAll(){
        return "/comentario/comentario-list";
    }
}
