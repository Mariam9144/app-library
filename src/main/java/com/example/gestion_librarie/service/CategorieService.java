package com.example.gestion_librarie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_librarie.model.Categorie;
import com.example.gestion_librarie.repository.CategorieRepository;

import lombok.Data;
@Data
@Service
public class CategorieService {

	@Autowired
    private CategorieRepository categorieRepository;

    public Optional<Categorie> getCategorie(final int id) {
        return categorieRepository.findById(id);
    }

    public List<Categorie> getCategories() {
        return categorieRepository.findAll();
    }

    public void deleteCategorie(final int id) {
    	categorieRepository.deleteById(id);
    }

    public Categorie saveCategorie(Categorie categorie) {
        Categorie savedCategorie = categorieRepository.save(categorie);
        return savedCategorie;
    }


}
