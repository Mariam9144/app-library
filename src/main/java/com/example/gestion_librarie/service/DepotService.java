package com.example.gestion_librarie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_librarie.model.Depot;
import com.example.gestion_librarie.repository.DepotRepository;

import lombok.Data;

@Service
@Data
public class DepotService {
	@Autowired
    private DepotRepository depotRepository;

    public Optional<Depot> getDepot(final int id) {
        return depotRepository.findById(id);
    }

    public List<Depot> getDepots() {
        return depotRepository.findAll();
    }

    public void deleteDepot(final int id) {
    	depotRepository.deleteById(id);
    }

    public Depot saveDepot(Depot depot) {
        Depot savedDepot = depotRepository.save(depot);
        return savedDepot;
    }
	

}
