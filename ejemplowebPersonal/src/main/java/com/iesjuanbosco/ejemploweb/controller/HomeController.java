package com.iesjuanbosco.ejemploweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {
    //Pagina principal
    @GetMapping("/inicio")
    public String inicio(Model model){
        return "inicio";
    }
}
