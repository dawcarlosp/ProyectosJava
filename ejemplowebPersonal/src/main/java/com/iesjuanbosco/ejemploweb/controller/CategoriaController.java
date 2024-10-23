package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.DTO.CategoriaCosteMedioDTO;
import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

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
        List<CategoriaCosteMedioDTO> categoriasEstadisticas = this.categoriaRepository.obtenerCosteMedioPorCategorias();
        model.addAttribute("categorias", categorias);
        model.addAttribute("categoriasEstadisticas",categoriasEstadisticas);
        return "/categoria/categoria-list";
    }
    //alta nueva categoria
    @GetMapping("/categorias/new")
    public String newCategoriaVista(Model model){
        model.addAttribute("categoria", new Categoria());
        return "/categoria/categoria-new";
    }
    @PostMapping("/categorias/new")
    public String saveCategoria(@ModelAttribute("categoria") Categoria categoria, BindingResult bindingResult, Model model, @RequestAttribute("file")MultipartFile file){
        if(bindingResult.hasErrors()){
            return "/categoria/categoria-new";
        }else{
            //Cambiamos el nombre el archivo
            UUID nombreUnico = UUID.randomUUID();
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String nuevoNombreFoto = nombreUnico + extension;
            //Guardar el archivo en disco
            Path ruta = Paths.get("uploads/imagesCategorias" + File.separator + nuevoNombreFoto);
            try {
                byte[] bytes = file.getBytes();
                Files.write(ruta, bytes);
            } catch (Exception e) {
                model.addAttribute("error", "Ocurrió un error al guardar la entidad");
            }
            //Guardamos la ruta en la BD
            categoria.setFoto(nuevoNombreFoto);
            categoriaRepository.save(categoria);
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
    public String editCategoria(@ModelAttribute Categoria categoria, @RequestAttribute("file")MultipartFile file, Model model, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/categoria/categoria-edit";
        }else{
            //Cambiamos el nombre el archivo
            UUID nombreUnico = UUID.randomUUID();
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String nuevoNombreFoto = nombreUnico + extension;
            //Guardar el archivo en disco
            Path ruta = Paths.get("uploads/imagesCategorias" + File.separator + nuevoNombreFoto);
            try {
                byte[] bytes = file.getBytes();
                Files.write(ruta, bytes);
            } catch (Exception e) {
                model.addAttribute("error", "Ocurrió un error al guardar la entidad");
            }
            //Guardamos la ruta en la BD
            categoria.setFoto(nuevoNombreFoto);
            categoriaRepository.save(categoria);
            return "redirect:/categorias";
        }
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
