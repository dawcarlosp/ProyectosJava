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

import java.text.DecimalFormat;
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
        int totalEncuestas;
        int promedioEdad;
        int sumaEdades = 0;
        //porcentajes
        String uno, dos, tres, cuatro, cinco;
        List<Opcion> filtros = Arrays.asList(
                new Opcion("todos", "Todas las encuestas"),
                new Opcion("muySatisfecho", "Muy Satisfecho"),
                new Opcion("satisfecho", "Satisfecho"),
                new Opcion("neutral", "Neutral"),
                new Opcion("insatisfecho", "Insatisfecho" ),
                new Opcion("muyInsatisfecho", "Muy Insatisfecho" )
        );
        model.addAttribute("filtros", filtros);
        List<Encuesta> encuestas = new ArrayList<>();
        if (filtro == null || filtro.isEmpty() || filtro.equals("todos")) {
            encuestas = this.encuestaRepository.findAll();
        } else {
            // Si se especifica una categoría, filtrar los productos
            encuestas = this.encuestaRepository.findBynivelSatisfaccion(filtro);
        }
        totalEncuestas = this.encuestaRepository.findAll().size();
        for (int i = 0; i < totalEncuestas; i++) {
            sumaEdades += this.encuestaRepository.findAll().get(i).getEdad();
        }
        if(totalEncuestas!=0) {
            promedioEdad = sumaEdades / totalEncuestas;
        }else{
            promedioEdad = 0;
        }
        model.addAttribute("encuestas", encuestas);
        model.addAttribute("totalEncuestas", totalEncuestas);
        model.addAttribute("promedioEdad", promedioEdad);
        //Porcentajes
        List<String> porcentajes = new ArrayList<>();
        if(!this.encuestaRepository.findAll().isEmpty()) {
            uno = porcentajeS("muyInsatisfecho");
            dos = porcentajeS("insatisfecho");
            tres = porcentajeS("neutral");
            cuatro = porcentajeS("satisfecho");
            cinco = porcentajeS("muySatisfecho");
            porcentajes.add(uno);
            porcentajes.add(dos);
            porcentajes.add(tres);
            porcentajes.add(cuatro);
            porcentajes.add(cinco);
            model.addAttribute("porcentajes", porcentajes);
        }
            return "encuesta-list";

    }
    //Metodo que devuelve el porcentaje
    public String porcentajeS(String filtro) {
        double re = 0;
        String formateado;
        DecimalFormat df = new DecimalFormat("#.##");
        double valorParcial = this.encuestaRepository.findBynivelSatisfaccion(filtro).size();
        double valorTotal = this.encuestaRepository.findAll().size();
        if(!this.encuestaRepository.findAll().isEmpty()){
            re = (valorParcial/valorTotal) * 100;
        }
        return formateado =  df.format(re);
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
    @GetMapping("/encuestas/exito")
    public String exito(){
        return "encuesta-exito";
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
            model.addAttribute("encuesta", encuesta);
            return "encuesta-new";
        }
        this.encuestaRepository.save(encuesta);
        return "redirect:/encuestas/exito";
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
