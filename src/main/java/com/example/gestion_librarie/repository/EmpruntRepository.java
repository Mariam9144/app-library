package com.example.gestion_librarie.repository;

import com.example.gestion_librarie.model.Livre;
import com.example.gestion_librarie.model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_librarie.model.Emprunt;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Integer> {
	boolean existsByMembre(Membre membre);
	boolean existsByLivre(Livre livre);


}
