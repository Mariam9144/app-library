package com.example.gestion_librarie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_librarie.model.Retour;

@Repository
public interface RetourRepository extends JpaRepository<Retour, Integer> {

}
