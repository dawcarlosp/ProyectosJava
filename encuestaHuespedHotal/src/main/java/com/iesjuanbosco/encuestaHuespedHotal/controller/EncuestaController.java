package com.iesjuanbosco.encuestaHuespedHotal.controller;
import com.iesjuanbosco.encuestaHuespedHotal.entity.Encuesta;
import com.iesjuanbosco.encuestaHuespedHotal.entity.Opcion;
import com.iesjuanbosco.encuestaHuespedHotal.repository.EncuestaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class EncuestaController {
    private EncuestaRepository encuestaRepository;
    public EncuestaController(EncuestaRepository repo){
        this.encuestaRepository = repo;
    }
    //Página principal
    @GetMapping("/encuestas")
    public String findAll(@RequestParam(value = "filtro", required = false) String filtro, Model model){
        List<Opcion> filtros = Arrays.asList(
                new Opcion("todos", "Todas las encuestas"),
                new Opcion("muysatisfecho", "Muy Satisfecho"),
                new Opcion("satisfecho", "Satisfecho"),
                new Opcion("neutral", "Neutral"),
                new Opcion("insatisfecho", "Insatisfecho" ),
                new Opcion("muyinsatisfecho", "Muy Insatisfecho" )
        );
        model.addAttribute("filtros", filtros);
        List<Encuesta> encuestas = new ArrayList<>();
        if (filtro == null || filtro.isEmpty() || filtro.equals("todos")) {
            encuestas = this.encuestaRepository.findAll();
        } else {
            // Si se especifica una categoría, filtrar los productos
            encuestas = this.encuestaRepository.findBynivelSatisfaccion(filtro);
        }

        model.addAttribute("encuestas", encuestas);
        return "encuesta-list";
    }
    //Eliminar encuesta
    @GetMapping("/encuestas/del/{id}")
    public String deleteEncuestaVista(@PathVariable Long id){
        this.encuestaRepository.deleteById(id);
        return "redirect:/encuestas";
    }
    //Alta encuesta
    @GetMapping("/encuestas/new")
    public String newEncuestaVista(Model model){
        model.addAttribute("encuesta", new Encuesta());
        List<Opcion> opciones = Arrays.asList(
                new Opcion("Trabajo", "Trabajo"),
                new Opcion("Estudios", "Estudios"),
                new Opcion("Turismo", "Turismo"),
                new Opcion("Otros", "Otros" )
        );
        model.addAttribute("opciones", opciones);
        return "encuesta-new";
    }
    @PostMapping("/encuestas/new")
    public String newEncuesta(@Valid Encuesta encuesta, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            List<Opcion> opciones = Arrays.asList(
                    new Opcion("Trabajo", "Trabajo"),
                    new Opcion("Estudios", "Estudios"),
                    new Opcion("Turismo", "Turismo"),
                    new Opcion("Otros", "Otros" )
            );
            model.addAttribute("opciones", opciones);
            return "encuesta-new";
        }
        this.encuestaRepository.save(encuesta);
        return "redirect:/encuestas";
    }
    //Modificar encuesta
    @GetMapping("/encuestas/edit/{id}")
    public String editEncuestaVista(@PathVariable Long id, Model modelo){
        Optional<Encuesta> encuesta = this.encuestaRepository.findById(id);
        if(encuesta.isPresent()){
            modelo.addAttribute("encuesta",encuesta.get());
            List<Opcion> opciones = Arrays.asList(
                    new Opcion("Trabajo", "Trabajo"),
                    new Opcion("Estudios", "Estudios"),
                    new Opcion("Turismo", "Turismo"),
                    new Opcion("Otros", "Otros" )
            );
            modelo.addAttribute("opciones",opciones);
            return "encuesta-edit";
        }else {
            return "redirect:/encuestas";
        }
    }
    @PostMapping("/encuestas/edit/{id}")
    public String editEncuesta(Encuesta encuesta){
        this.encuestaRepository.save(encuesta);
        return "redirect:/encuestas";
    }
    //Visualizar encuesta individualmente
    @GetMapping("/encuestas/view/{id}")
    public String view(@PathVariable Long id, Model model){
        Optional<Encuesta> encuesta = this.encuestaRepository.findById(id);
        if(encuesta.isPresent()){
            model.addAttribute("encuesta", encuesta.get());
            return "encuesta-view";
        }else{
            return "redirect:/encuestas";
        }
    }
}
