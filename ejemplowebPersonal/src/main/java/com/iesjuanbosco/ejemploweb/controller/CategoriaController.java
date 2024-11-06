package com.iesjuanbosco.ejemploweb.controller;

import com.iesjuanbosco.ejemploweb.DTO.CategoriaCosteMedioDTO;
import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import com.iesjuanbosco.ejemploweb.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private CategoriaService categoriaService;

    //Mostras productos de una categoria
    /*
    @GetMapping("/categorias/detalle/{id}")
    public String detalleCategoriaV(@PathVariable Long id, Model model){
        Optional<Categoria> categoria = this.categ.findById(id);
        if(categoria.isPresent()){
            var productos = categoria.get().getProductos();
            model.addAttribute("productos",productos);
            model.addAttribute("categoria",categoria);
            return "/categoria/categoria-detalle";
        }
        return "redirect:/categorias";
    }
    */
    //listar categorias
    @GetMapping("/categorias")
    public String findAllC(Model model) {
        List<CategoriaCosteMedioDTO> categoriasConStats = this.categoriaService.obtenerCategoriasConStats();
        List<Categoria> categorias = this.categoriaService.obtenerCategorias();
        model.addAttribute("categoriasE", categoriasConStats);
        model.addAttribute("categorias", categorias);
        return "/categoria/categoria-list";
    }

    //alta nueva categoria
    @GetMapping("/categorias/new")
    public String addCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "/categoria/categoria-new";
    }

    @PostMapping("/categorias/new")
    public String addCategoriaInsert(@ModelAttribute("categoria") Categoria categoria,
                                     @RequestParam("file") MultipartFile file,
                                     RedirectAttributes redirectAttributes) {
        try {
            categoriaService.guardarCategoria(categoria, file);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/categorias/new";
        }
        return "redirect:/categorias";
    }

    //eliminar categoria
    @GetMapping("/categorias/del/{id}")
    public String borrarCategoria(@PathVariable("id") Long id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/categorias";
    }

    //editar categoria
    @GetMapping("/categorias/edit/{id}")
    public String editCategoria(@PathVariable Long id, Model model) {
        Optional<Categoria> categoria = this.categoriaService.findById(id);
        if (categoria.isPresent()) {
            model.addAttribute("categoria", categoria.get());
            return "/categoria/categoria-edit";
        } else {
            return "redirect:/categorias";
        }
    }

    @PostMapping("/categorias/edit/{id}")
    public String editCategoria(@Valid Categoria categoria, @RequestAttribute("file") MultipartFile file, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/categoria/categoria-edit";
        } else {
            if(!file.isEmpty()) {
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
            }
            this.categoriaService.save(categoria);
            return "redirect:/categorias";
        }
    }

    //Visualizar categoria
    @GetMapping("/categorias/view/{id}")
    public String viewCategorias(@PathVariable Long id, Model model) {
        Optional<Categoria> categoria = this.categoriaService.findById(id);
        if (categoria.isPresent()) {
            model.addAttribute("categoria", categoria.get());
            return "/categoria/categoria-view";
        } else {
            return "redirect:/categorias";
        }
    }
}