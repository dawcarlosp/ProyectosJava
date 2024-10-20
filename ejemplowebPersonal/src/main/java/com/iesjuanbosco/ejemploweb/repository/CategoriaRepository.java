package com.iesjuanbosco.ejemploweb.repository;

import com.iesjuanbosco.ejemploweb.DTO.CategoriaCosteMedioDTO;
import com.iesjuanbosco.ejemploweb.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    Categoria findByNombre(String nombre);
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.categoria.id = :categoriaId")
    long countByIdCategoria(@Param("categoriaId") Long categoriaId);

    @Query(value = "SELECT COUNT(*) FROM producto WHERE categoria_id = :categoriaId", nativeQuery = true)
    Long countByIdCategoriaNativa(@Param("categoriaId") Long categoriaId);

    @Query("SELECT AVG(p.precio) FROM Producto p WHERE p.categoria.id = :categoriaId")
    Double AVGPrecioByCategoria(@Param("categoriaId") Long categoriaId);

    @Query("SELECT new com.iesjuanbosco.ejemploweb.DTO.CategoriaCosteMedioDTO(c.id, c.nombre, AVG(p.precio),COUNT(p))" +
            "FROM Categoria c LEFT JOIN c.productos p GROUP BY c.id")
    List<CategoriaCosteMedioDTO> obtenerCosteMedioPorCategorias();
}
