package com.persons.speax.service;

import com.persons.speax.dto.LanguageAddindDTO;
import com.persons.speax.entity.Language;
import com.persons.speax.exception.ApiValidationException;
import com.persons.speax.repository.LanguageRepository;
import com.persons.speax.validation.LanguageCreationValidation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LanguageService {

    private final LanguageRepository repository;
    private final List<LanguageCreationValidation> validations;

    public LanguageService(
            LanguageRepository repository,
            List<LanguageCreationValidation> validations
    ) {
        this.repository = repository;
        this.validations = validations;
    }

    public List<Language> listLanguages(boolean showDeactivated) {
        return showDeactivated? repository.findAll() : repository.findByActive(true);
    }

    @Transactional
    public Language addLanguage(LanguageAddindDTO language) {
        Language parsedLanguage = new Language(language.name());

        validations.forEach(validation -> validation.validate(parsedLanguage));
        return repository.save(parsedLanguage);
    }

    public Language getLanguage(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Language not found"));
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
        Language language = this.getLanguage(id);
        language.setActive(active);
        repository.save(language);
    }

    public List<Language> addMultipleLanguages(@Valid List<LanguageAddindDTO> request) {

        return request.stream()
                .map(this::addLanguage)
                .toList();

    }
}
