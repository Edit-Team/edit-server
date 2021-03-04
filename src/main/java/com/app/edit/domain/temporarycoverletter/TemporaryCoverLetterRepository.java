package com.app.edit.domain.temporarycoverletter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporaryCoverLetterRepository extends JpaRepository<TemporaryCoverLetter, Long> {
}
