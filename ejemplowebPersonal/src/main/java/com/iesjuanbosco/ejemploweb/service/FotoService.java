package com.iesjuanbosco.ejemploweb.service;

import com.iesjuanbosco.ejemploweb.entity.Foto;
import com.iesjuanbosco.ejemploweb.repository.FotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FotoService {
    @Autowired
    private FotoRepository fotoRepository;
    public void deleteById(Long id){
        this.fotoRepository.deleteById(id);;
    }
}
