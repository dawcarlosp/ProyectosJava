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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
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
    public String saveCategoria(@Valid Categoria categoria, BindingResult bindingResult, Model model, @RequestParam("foto")MultipartFile foto){
        if(bindingResult.hasErrors()){
            return "/categoria/categoria-new";
        }else{
            this.categoriaRepository.save(categoria);
            try {
                if (!foto.isEmpty()) {
                    // Generar una ruta única para el archivo y guardarlo en el sistema de archivos
                    String nombreArchivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename(); // Nombre único para evitar colisiones
                    String rutaArchivo = "src/main/resources/static/fotos/categorias/" + nombreArchivo; // Ruta donde se almacenará el archivo
                    // Guardar el archivo en el sistema de archivos
                    Path path = Paths.get(rutaArchivo);
                    Files.write(path, foto.getBytes());

                    // Asignar la ruta de la imagen al campo "foto" de la entidad
                    categoria.setFoto(nombreArchivo);
                }

                // Guardar la entidad en la base de datos
                this.categoriaRepository.save(categoria);

                model.addAttribute("mensaje", "Entidad guardada con éxito");
            } catch (Exception e) {
                model.addAttribute("error", "Ocurrió un error al guardar la entidad");
            }
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
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Excluimos el campo "foto" del binding automático
        binder.setDisallowedFields("foto");
    }
    @PostMapping("/categorias/edit/{id}")
    public String editCategoria(@ModelAttribute Categoria categoria, @RequestParam("foto")MultipartFile foto, Model model){
        try {
            if (!foto.isEmpty()) {
                // Generar una ruta única para el archivo y guardarlo en el sistema de archivos
                String nombreArchivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename(); // Nombre único para evitar colisiones
                String rutaArchivo = "src/main/resources/static/fotos/categorias/" + nombreArchivo; // Ruta donde se almacenará el archivo
                // Guardar el archivo en el sistema de archivos
                Path path = Paths.get(rutaArchivo);
                Files.write(path, foto.getBytes());

                // Asignar la ruta de la imagen al campo "foto" de la entidad
                categoria.setFoto(nombreArchivo);
            }

            // Guardar la entidad en la base de datos
            categoriaRepository.save(categoria);

            model.addAttribute("mensaje", "Entidad guardada con éxito");
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al guardar la entidad");
        }

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
