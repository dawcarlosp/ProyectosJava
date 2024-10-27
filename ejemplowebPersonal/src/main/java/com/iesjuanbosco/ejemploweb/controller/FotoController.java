package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Foto;
import com.iesjuanbosco.ejemploweb.service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FotoController {
    @Autowired
    private FotoService fotoService;
    @GetMapping("/productos/edit/{idP}/foto/del/{id}")
    public String deleteFoto(@PathVariable Long id){
      fotoService.deleteById(id);
      return "redirect:/productos/edit/{idP}";
    }
}
