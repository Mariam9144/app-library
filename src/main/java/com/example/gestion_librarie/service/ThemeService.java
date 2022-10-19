package com.example.gestion_librarie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_librarie.model.Theme;
import com.example.gestion_librarie.repository.ThemeRepository;

import lombok.Data;

@Service
@Data
public class ThemeService {

	@Autowired
    private ThemeRepository themeRepository;

    public Optional<Theme> getTheme(final int id) {
        return themeRepository.findById(id);
    }

    public List<Theme> getThemes() {
        return themeRepository.findAll();
    }

    public void deleteTheme(final int id) {
    	themeRepository.deleteById(id);
    }

    public Theme saveTheme(Theme theme) {
        Theme savedTheme = themeRepository.save(theme);
        return savedTheme;
    }

}
