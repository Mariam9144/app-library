package com.example.gestion_librarie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_librarie.model.Retour;
import com.example.gestion_librarie.repository.RetourRepository;

import lombok.Data;

@Data
@Service
public class RetourService {

	@Autowired
    private RetourRepository retourRepository;

    public Optional<Retour> getRetour(final int id) {
        return retourRepository.findById(id);
    }

    public List<Retour> getRetours() {
        return retourRepository.findAll();
    }

    public void deleteRetour(final int id) {
    	retourRepository.deleteById(id);
    }

    public Retour saveRetour(Retour retour) {
        Retour savedRetour = retourRepository.save(retour);
        return savedRetour;
    }

}
