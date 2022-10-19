package com.example.gestion_librarie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_librarie.model.Retrait;
import com.example.gestion_librarie.repository.RetraitRepository;

import lombok.Data;

@Service
@Data
public class RetraitService {

	@Autowired
    private RetraitRepository retraitRepository;

    public Optional<Retrait> getRetrait(final int id) {
        return retraitRepository.findById(id);
    }

    public List<Retrait> getRetraits() {
        return retraitRepository.findAll();
    }

    public void deleteRetrait(final int id) {
    	retraitRepository.deleteById(id);
    }

    public Retrait saveRetrait(Retrait retrait) {
        Retrait savedRetrait = retraitRepository.save(retrait);
        return savedRetrait;
    }
}
