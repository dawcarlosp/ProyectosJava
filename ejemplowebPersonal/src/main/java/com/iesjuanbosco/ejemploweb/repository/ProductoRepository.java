package com.iesjuanbosco.ejemploweb.repository;

import com.iesjuanbosco.ejemploweb.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Indica que esta clase es un repositorio
@Repository
//CRUD tipico ya esta hecho
//Si queremos operaciones personalizadas hay que añadir manualmente
public interface ProductoRepository extends JpaRepository<Producto,Long> {
  //  List<Producto> findAllByCategoria(String categoria);
}
