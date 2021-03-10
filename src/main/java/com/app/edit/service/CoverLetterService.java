package com.app.edit.service;

import com.app.edit.domain.coverletter.CoverLetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CoverLetterService {

    private final CoverLetterRepository coverLetterRepository;

    @Autowired
    public CoverLetterService(CoverLetterRepository coverLetterRepository) {
        this.coverLetterRepository = coverLetterRepository;
    }
}
