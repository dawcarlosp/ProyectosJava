package com.iesjuanbosco.ejemploweb.repository;

import com.iesjuanbosco.ejemploweb.entity.Categoria;
import com.iesjuanbosco.ejemploweb.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//Indica que esta clase es un repositorio
@Repository
//CRUD tipico ya esta hecho
//Si queremos operaciones personalizadas hay que añadir manualmente
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    List<Producto> findByCategoria(Categoria categoria);
    @Query("SELECT SUM(p.precio*p.cantidad) FROM Producto p")
    Double importe();
    @Query("SELECT SUM(p.precio*p.cantidad) FROM Producto p WHERE p.categoria.id= :id")
    Double importeCategoria(@Param("id") Long id);
    List<Producto> findFirstByPrecioOrderByPrecioDesc(Double precio);

    @Query("SELECT COUNT(p) FROM Producto p WHERE p.categoria.id = :categoriaId")
    long countByIdCategoria(@Param("categoriaId") Long categoriaId);

    @Query(value = "SELECT COUNT(*) FROM producto WHERE categoria_id = :categoriaId", nativeQuery = true)
    Long countByIdCategoriaNativa(@Param("categoriaId") Long categoriaId);

    @Query("SELECT AVG(p.precio) FROM Producto p WHERE p.categoria.id = :categoriaId")
    Double AVGPrecioByCategoria(@Param("categoriaId") Long categoriaId);
}
