package com.app.edit.domain.sympathy;

import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SympathyRepository extends JpaRepository<Sympathy, SympathyId> {

    Long countSympathiesByCoverLetterAndState(CoverLetter coverLetter, State state);
}
