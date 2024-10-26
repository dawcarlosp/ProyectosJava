package com.iesjuanbosco.ejemploweb.service;

import com.iesjuanbosco.ejemploweb.entity.Comentario;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.ComentarioRepository;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ComentarioRepository comentarioRepository;
    public String comentar(@PathVariable Long id, @Valid Comentario comentario, BindingResult bindingResult, Model model){
        Optional<Producto> producto = productoService.findById(id);
        if(producto.isPresent()) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("producto",producto.get());
                model.addAttribute("comentarios",this.comentarioRepository.findByProductoOrderByFechaDesc(producto.get()));
                return "/producto/producto-view";
            }
            comentario.setProducto(producto.get());
            comentario.setFecha(LocalDate.now());
            this.comentarioRepository.save(comentario);
            return "redirect:/productos/view/" + id;
        }
        return "redirect:/productos";
    }
    public List<Comentario> findAll(){
        return this.comentarioRepository.findAll();
    }
    public void save(Comentario comentario){
        this.comentarioRepository.save(comentario);
    }
    public void deleteById(@PathVariable Long id){
        this.comentarioRepository.deleteById(id);
    }
    public Optional findById(Long id){
        return this.comentarioRepository.findById(id);
    }
    public List<Comentario> findByProductoOrderByFechaDesc(Producto producto){
        return this.comentarioRepository.findByProductoOrderByFechaDesc(producto);
    }
}
