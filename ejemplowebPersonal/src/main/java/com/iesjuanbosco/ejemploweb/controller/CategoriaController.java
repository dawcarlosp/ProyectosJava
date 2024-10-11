package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoriaController {
    private CategoriaRepository categoriaRepository;
    public CategoriaController(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaRepository getCategoriaRepository() {
        return categoriaRepository;
    }

    //Mostras productos de una categoria
    @GetMapping("/categorias/detalle/{id}")
    public String detalleCategoriaV(@PathVariable Long id, Model model){
        Optional<Categoria> categoria = this.categoriaRepository.findById(id);
        if(categoria.isPresent()){
            var productos = categoria.get().getProductos();
            model.addAttribute("productos",productos);
            model.addAttribute("categoria",categoria);
            return "/categoria/categoria-detalle";
        }
        return "redirect:/categorias";
    }
    //listar categorias
    @GetMapping("/categorias")
    public String findAllC(Model model){
        List<Categoria> categorias = this.categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        return "/categoria/categoria-list";
    }
    //alta nueva categoria
    @GetMapping("/categorias/new")
    public String newCategoriaVista(Model model){
        model.addAttribute("categoria", new Categoria());
        return "/categoria/categoria-new";
    }
    @PostMapping("/categorias/new")
    public String saveCategoria(@Valid Categoria categoria, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "/categoria/categoria-new";
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
            return "/categoria/categoria-edit";
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
            return "/categoria/categoria-view";
        }else{
            return "redirect:/categorias";
        }
    }
}
