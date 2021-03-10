package com.app.edit.provider;

import com.app.edit.domain.coverlettercategory.CoverLetterCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CoverLetterCategoryProvider {

    private final CoverLetterCategoryRepository coverLetterCategoryRepository;

    @Autowired
    public CoverLetterCategoryProvider(CoverLetterCategoryRepository coverLetterCategoryRepository) {
        this.coverLetterCategoryRepository = coverLetterCategoryRepository;
    }
}
