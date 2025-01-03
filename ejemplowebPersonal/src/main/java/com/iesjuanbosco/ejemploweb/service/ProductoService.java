package com.iesjuanbosco.ejemploweb.service;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.entity.Foto;
import com.iesjuanbosco.ejemploweb.entity.FotoProducto;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import com.iesjuanbosco.ejemploweb.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;
    private static final List<String> TIPOS_PERMITIDOS = List.of("image/jpeg","image/png","image/gif","image/avif","image/webp","image/jpg");
    private static final long MAX_FILE_SIZE = 10000000;
    private static final String UPLOADS_DIRECTORY = "uploads/imagesProductos";
    public List<Producto> findAll() {
        return this.productoRepository.findAll();
    }
    public Optional findById(Long id) {
        return this.productoRepository.findById(id);
    }
    public void eliminarProducto(Long id){
        this.productoRepository.deleteById(id);
    }
    public void saveProducto(Producto producto) {
        this.productoRepository.save(producto);
    }
    //Metodo hecho por Samuel
    public void guardarProducto(Producto producto, MultipartFile[] files) {
        List<String> fotos = new ArrayList<>();
        for (MultipartFile file : files){
            if(file.isEmpty() || file == null){
                throw new IllegalArgumentException("Archivo no seleccionado");
            }
            if(!TIPOS_PERMITIDOS.contains(file.getContentType())){
                throw new IllegalArgumentException("Alguno de los archivos seleccionados no es una imagen");
            }
            if (file.getSize() > MAX_FILE_SIZE){
                throw new IllegalArgumentException("Archivo demasiado grande. Solo se admiten archivos < 10MB");
            }
            UUID nombreUnico = UUID.randomUUID();
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String nuevoNombreFoto = nombreUnico + extension;
            Path ruta = Paths.get(UPLOADS_DIRECTORY + File.separator + nuevoNombreFoto);
            String rutaTexto = ruta.toString();
            String rutaValida = rutaTexto.substring(rutaTexto.indexOf('/'));
            try {
                byte[] contenido = file.getBytes();
                Files.write(ruta, contenido);
                fotos.add(nuevoNombreFoto);
                FotoProducto fotillo = new FotoProducto();
                fotillo.setNombre(rutaValida);
                fotillo.setProducto(producto);
                productoRepository.save(producto);
                producto.getFotos().add(fotillo);
            } catch (
                    IOException e) {
                throw new RuntimeException("Error al guardar archivo", e);
            }
        }
        productoRepository.save(producto);
    }
    public double importe(){
        return this.productoRepository.importe();
    }
    public List<Producto> findByCategoria(Categoria categoria){
        return this.productoRepository.findByCategoria(categoria);
    }
    public Double importeCategoria(Long id){
        return this.productoRepository.importeCategoria(id);
    }
    public void deleteById(Long id){
        this.productoRepository.deleteById(id);
    }
    public void save(Producto producto){
        this.productoRepository.save(producto);
    }
}
