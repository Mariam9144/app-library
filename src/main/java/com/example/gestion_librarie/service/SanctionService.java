package com.example.gestion_librarie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_librarie.model.Sanction;
import com.example.gestion_librarie.repository.SanctionRepository;

import lombok.Data;

@Service
@Data
public class SanctionService {

	@Autowired
    private SanctionRepository sanctionRepository;

    public Optional<Sanction> getSanction(final int id) {
        return sanctionRepository.findById(id);
    }

    public List<Sanction> getSanctions() {
        return sanctionRepository.findAll();
    }

    public void deleteSanction(final int id) {
    	sanctionRepository.deleteById(id);
    }

    public Sanction saveSanction(Sanction sanction) {
        Sanction savedSanction = sanctionRepository.save(sanction);
        return savedSanction;
    }

}
