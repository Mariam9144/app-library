package com.example.gestion_librarie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_librarie.model.Membre;
import com.example.gestion_librarie.repository.MembreRepository;

import lombok.Data;

@Service
@Data
public class MembreService {
	
	@Autowired
    private MembreRepository membreRepository;

    public Optional<Membre> getMembre(final int id) {
        return membreRepository.findById(id);
    }

    public List<Membre> getMembres() {
        return membreRepository.findAll();
    }

    public void deleteMembre(final int id) {
    	membreRepository.deleteById(id);
    }

    public Membre saveMembre(Membre membre) {
        Membre savedMembre = membreRepository.save(membre);
        return savedMembre;
    }

}
