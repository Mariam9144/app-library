package com.example.gestion_librarie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_librarie.model.MaisonEdition;
import com.example.gestion_librarie.repository.MaisonEditionRepository;

import lombok.Data;

@Service
@Data
public class MaisonEditionService {

	@Autowired
    private MaisonEditionRepository maisonEditionRepository;

    public Optional<MaisonEdition> getMaisonEdition(final int id) {
        return maisonEditionRepository.findById(id);
    }

    public List<MaisonEdition> getMaisonEditions() {
        return maisonEditionRepository.findAll();
    }

    public void deleteMaisonEdition(final int id) {
    	maisonEditionRepository.deleteById(id);
    }

    public MaisonEdition saveMaisonEdition(MaisonEdition maisonEdition) {
        MaisonEdition savedMaisonEdition = maisonEditionRepository.save(maisonEdition);
        return savedMaisonEdition;
    }
}
