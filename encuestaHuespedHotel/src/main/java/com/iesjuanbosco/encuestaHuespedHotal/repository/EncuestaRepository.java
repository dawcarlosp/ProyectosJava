package com.iesjuanbosco.encuestaHuespedHotal.repository;

import com.iesjuanbosco.encuestaHuespedHotal.entity.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncuestaRepository extends JpaRepository<Encuesta,Long> {
    List<Encuesta> findBynivelSatisfaccion(String satisfaccion);
    @Query("SELECT MAX(e.id) FROM Encuesta e")
    Long findMaxId();
}

