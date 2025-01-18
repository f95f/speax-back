package com.persons.speax.repository;

import com.persons.speax.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    List<Language> findByActive(boolean active);

    boolean existsByName(String name);
}
