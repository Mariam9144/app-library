package com.example.gestion_librarie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_librarie.model.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme,Integer> {

}
