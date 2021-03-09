package com.app.edit.provider;

import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.sympathy.SympathyRepository;
import com.app.edit.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SympathyProvider {

    private final SympathyRepository sympathyRepository;

    @Autowired
    public SympathyProvider(SympathyRepository sympathyRepository) {
        this.sympathyRepository = sympathyRepository;
    }

    public Long getSympathiesCount(CoverLetter coverLetter) {
        return sympathyRepository.countSympathiesByCoverLetterAndState(coverLetter, State.ACTIVE);
    }
}
