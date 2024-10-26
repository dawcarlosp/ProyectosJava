package com.iesjuanbosco.ejemploweb.service;

import com.iesjuanbosco.ejemploweb.DTO.CategoriaCosteMedioDTO;
import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;
    private static final List<String> TIPOS_PERMITIDOS = List.of("image/jpeg","image/png","image/png","image/gif","image/avif","image/webp","image/jpg");
    private static final long MAX_FILE_SIZE = 10000000;
    private static final String UPLOADS_DIRECTORY = "uploads/imagesCategorias";

    public List<Categoria> findAll(){
        return this.categoriaRepository.findAll();
    }
    public List<CategoriaCosteMedioDTO> obtenerCategoriasConStats(){
        return this.categoriaRepository.obtenerCategoriasConStats();
    }
    public List<Categoria> obtenerCategorias(){
        return this.categoriaRepository.findAll();
    }
    public void eliminarCategoria(long id){
        this.categoriaRepository.deleteById(id);
    }
    public Optional findById(long idCategoria){
        Optional<Categoria> categoria = this.categoriaRepository.findById(idCategoria);
        return categoria;
    }
    public void save(Categoria categoria){
        this.categoriaRepository.save(categoria);
    }
    public void guardarCategoria(Categoria categoria, MultipartFile file){
        if(file == null || file.isEmpty()){
            throw new IllegalArgumentException("Archivo no seleccionado");
        }
        if(!TIPOS_PERMITIDOS.contains((file.getContentType()))){
            throw new IllegalArgumentException("El archivo seleccionado no es una imagen");
        }
        if(file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("Archivo demasiado grande. Solo se admiten archivos < 10MB");
        }

        UUID nombreUnico = UUID.randomUUID();
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String nuevoNombreFoto = nombreUnico + extension;
        Path ruta = Paths.get(UPLOADS_DIRECTORY + File.separator + nuevoNombreFoto);

        try {
            byte[] contenido = file.getBytes();
            Files.write(ruta, contenido);
            categoria.setFoto(nuevoNombreFoto);
            categoriaRepository.save(categoria);
        } catch (
                IOException e) {
            throw new RuntimeException("Error al guardar archivo", e);
        }

    }
}
