package com.app.edit.provider;

import com.app.edit.domain.temporarycoverletter.TemporaryCoverLetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class TemporaryCoverLetterProvider {

    private final TemporaryCoverLetterRepository temporaryCoverLetterRepository;

    @Autowired
    public TemporaryCoverLetterProvider(TemporaryCoverLetterRepository temporaryCoverLetterRepository) {
        this.temporaryCoverLetterRepository = temporaryCoverLetterRepository;
    }
}
