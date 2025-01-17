package com.persons.speax.service;

import com.persons.speax.dto.LanguageAddindDTO;
import com.persons.speax.entity.Language;
import com.persons.speax.repository.LanguageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LanguageService {

    private final LanguageRepository repository;

    public LanguageService(LanguageRepository repository) {
        this.repository = repository;
    }

    public List<Language> listLanguages(boolean showDeactivated) {
        return showDeactivated? repository.findAll() : repository.findByActive(true);
    }

    @Transactional
    public Language addLanguage(LanguageAddindDTO language) {
        Language parsedLanguage = new Language(language.name());
        return repository.save(parsedLanguage);
    }

    public Language getLanguage(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public void deleteLanguage(Long id) {
        repository.deleteById(id);
    }


    @Transactional
    public Language updateLanguage(Long id, LanguageAddindDTO language) {
        Language parsedLanguage = new Language(language.name());
        parsedLanguage.setId(id);
        return repository.save(parsedLanguage);
    }


    @Transactional
    public void activateOrDeactivateLanguage(Long id, boolean active) {
        Language language = repository.findById(id).orElseThrow();
        language.setActive(active);
        repository.save(language);
    }
}
