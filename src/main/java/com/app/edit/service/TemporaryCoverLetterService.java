package com.app.edit.service;

import com.app.edit.domain.temporarycoverletter.TemporaryCoverLetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TemporaryCoverLetterService {

    private final TemporaryCoverLetterRepository temporaryCoverLetterRepository;

    @Autowired
    public TemporaryCoverLetterService(TemporaryCoverLetterRepository temporaryCoverLetterRepository) {
        this.temporaryCoverLetterRepository = temporaryCoverLetterRepository;
    }
}
