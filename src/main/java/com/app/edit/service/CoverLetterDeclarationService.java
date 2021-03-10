package com.app.edit.service;

import com.app.edit.domain.coverletterdeclaration.CoverLetterDeclarationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CoverLetterDeclarationService {

    private final CoverLetterDeclarationRepository coverLetterDeclarationRepository;

    @Autowired
    public CoverLetterDeclarationService(CoverLetterDeclarationRepository coverLetterDeclarationRepository) {
        this.coverLetterDeclarationRepository = coverLetterDeclarationRepository;
    }
}
