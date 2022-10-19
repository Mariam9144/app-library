package com.example.gestion_librarie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_librarie.model.Utilisateur;
import com.example.gestion_librarie.repository.UtilisateurRepository;

import lombok.Data;

@Service
@Data

public class UtilisateurService {

	@Autowired
    private UtilisateurRepository utilisateurRepository;

    public Optional<Utilisateur> getUtilisateur(final int id) {
        return utilisateurRepository.findById(id);
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public void deleteUtilisateur(final int id) {
    	utilisateurRepository.deleteById(id);
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
        return savedUtilisateur;
    }

}
