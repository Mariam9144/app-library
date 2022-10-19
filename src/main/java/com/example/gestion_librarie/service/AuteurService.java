package com.example.gestion_librarie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_librarie.model.Auteur;
import com.example.gestion_librarie.repository.AuteurRepository;

import lombok.Data;

@Service
@Data

public class AuteurService {

	@Autowired
    private AuteurRepository auteurRepository;

    public Optional<Auteur> getAuteur(final int id) {
        return auteurRepository.findById(id);
    }

    public List<Auteur> getAuteurs() {
        return auteurRepository.findAll();
    }

    public void deleteAuteur(final int id) {
    	auteurRepository.deleteById(id);
    }

    public Auteur saveAuteur(Auteur auteur) {
        Auteur savedAuteur = auteurRepository.save(auteur);
        return savedAuteur;
    }

}
