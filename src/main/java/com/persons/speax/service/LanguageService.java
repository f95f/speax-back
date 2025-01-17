package com.persons.speax.service;

import com.persons.speax.dto.LanguageAddindDTO;
import com.persons.speax.entity.Language;
import com.persons.speax.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LanguageService {

    private final LanguageRepository repository;

    public LanguageService(LanguageRepository repository) {
        this.repository = repository;
    }

    public List<Language> listLanguages() {
        return repository.findAll();
    }

    public Language addLanguage(LanguageAddindDTO language) {
        Language parsedLanguage = new Language(language.name());
        return repository.save(parsedLanguage);
    }
}
