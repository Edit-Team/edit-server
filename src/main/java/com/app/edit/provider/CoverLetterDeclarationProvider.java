package com.app.edit.provider;

import com.app.edit.domain.coverletterdeclaration.CoverLetterDeclarationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CoverLetterDeclarationProvider {

    private final CoverLetterDeclarationRepository coverLetterDeclarationRepository;

    @Autowired
    public CoverLetterDeclarationProvider(CoverLetterDeclarationRepository coverLetterDeclarationRepository) {
        this.coverLetterDeclarationRepository = coverLetterDeclarationRepository;
    }
}
