package com.example.gestion_librarie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_librarie.model.Emprunt;
import com.example.gestion_librarie.repository.EmpruntRepository;

import lombok.Data;

@Service
@Data
public class EmpruntService {

	@Autowired
    private EmpruntRepository empruntRepository;

    public Optional<Emprunt> getEmprunt(final int id) {
        return empruntRepository.findById(id);
    }

    public List<Emprunt> getEmprunts() {
        return empruntRepository.findAll();
    }

    public void deleteEmprunt(final int id) {
    	empruntRepository.deleteById(id);
    }

    public Emprunt saveEmprunt(Emprunt emprunt) {
        Emprunt savedEmprunt = empruntRepository.save(emprunt);
        empruntRepository.flush();
        return savedEmprunt;
    }
}
