package com.persons.speax.validation;

import com.persons.speax.entity.Language;
import com.persons.speax.exception.ApiValidationException;
import com.persons.speax.repository.LanguageRepository;
import org.springframework.stereotype.Component;

@Component
public class LanguageExistsValidation implements LanguageCreationValidation{

    private final LanguageRepository languageRepository;

    public LanguageExistsValidation(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public void validate(Language language) {
        String languageName = language.getName();
        boolean doesLanguageExist = languageRepository.existsByName(languageName);

        if (doesLanguageExist) {
            throw new ApiValidationException("Language with name " + languageName + " already exists.");
        }
    }
}
