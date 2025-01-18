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
    public ResponseEntity<List<Language>> listLanguages(
            @RequestParam(defaultValue = "false") boolean showDeactivated,
            @RequestParam(defaultValue = "") String searchTerm
    ) {
        List<Language> languages = service.listLanguages(showDeactivated, searchTerm);

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
    public ResponseEntity<List<Language>> addLanguage(
            @RequestBody @Valid List<LanguageAddindDTO> request,
            UriComponentsBuilder uriBuilder
    ) {

        List<Language> languages = service.addMultipleLanguages(request);
        URI uri = uriBuilder.path("api/v1/languages x").buildAndExpand().toUri();

        return ResponseEntity.created(uri).body(languages);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Language> updateLanguage(
            @PathVariable Long id,
            @RequestBody @Valid LanguageAddindDTO request
    ) {
        Language language = service.updateLanguage(id, request);

        return ResponseEntity.ok(language);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Language> activateOrDeactivateLanguage(
            @PathVariable Long id,
            @RequestParam boolean setActive
    ) {
        service.activateOrDeactivateLanguage(id, setActive);
        return ResponseEntity.noContent().build();
    }

}