package com.example.gestion_librarie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_librarie.model.Depot;

@Repository
public interface DepotRepository extends JpaRepository<Depot, Integer>  {

}
