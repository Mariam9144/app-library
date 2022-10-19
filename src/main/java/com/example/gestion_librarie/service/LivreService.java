package com.example.gestion_librarie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_librarie.model.Livre;
import com.example.gestion_librarie.repository.LivreRepository;

import lombok.Data;

@Service
@Data 
public class LivreService {

	@Autowired
    private LivreRepository livreRepository;

    public Optional<Livre> getLivre(final int id) {
        return livreRepository.findById(id);
    }

    public List<Livre> getLivres() {
        return livreRepository.findAll();
    }

    public void deleteLivre(final int id) {
    	livreRepository.deleteById(id);
    }

    public Livre saveLivre(Livre livre) {
       // Livre savedLivre =
               return livreRepository.saveAndFlush(livre);
        //livreRepository.flush();
        //return savedLivre;
    }

}
