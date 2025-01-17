package com.persons.speax.controller;

import com.persons.speax.dto.LanguageAddindDTO;
import com.persons.speax.entity.Language;
import com.persons.speax.service.LanguageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/languages")
public class LanguageController {

    private final LanguageService service;

    public LanguageController(LanguageService service) {
        this.service = service;
    }



    @GetMapping
    public ResponseEntity<List<Language>> listLanguages() {
        List<Language> languages = service.listLanguages();

        return ResponseEntity.ok(languages);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguage(@PathVariable Long id) {
        Language language = service.getLanguage(id);

        return ResponseEntity.ok(language);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        service.deleteLanguage(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("/add")
    public ResponseEntity<Language> addLanguage(
            @RequestBody @Valid LanguageAddindDTO request,
            UriComponentsBuilder uriBuilder
    ) {

        Language language = service.addLanguage(request);
        URI uri = uriBuilder.path("api/v1/languages/{id}").buildAndExpand(language.getId()).toUri();

        return ResponseEntity.created(uri).body(language);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Language> updateLanguage(
            @PathVariable Long id,
            @RequestBody @Valid LanguageAddindDTO request
    ) {
        Language language = service.updateLanguage(id, request);

        return ResponseEntity.ok(language);
    }
}

