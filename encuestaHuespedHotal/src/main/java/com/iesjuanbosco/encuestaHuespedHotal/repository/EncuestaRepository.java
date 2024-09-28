package com.iesjuanbosco.encuestaHuespedHotal.repository;

import com.iesjuanbosco.encuestaHuespedHotal.entity.Encuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncuestaRepository extends JpaRepository<Encuesta,Long> {
}

