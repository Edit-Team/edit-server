package com.app.edit.controller;

import com.app.edit.service.CoverLetterDeclarationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class CoverLetterDeclarationController {

    private CoverLetterDeclarationService coverLetterDeclarationService;

    @Autowired
    public CoverLetterDeclarationController(CoverLetterDeclarationService coverLetterDeclarationService) {
        this.coverLetterDeclarationService = coverLetterDeclarationService;
    }
}
