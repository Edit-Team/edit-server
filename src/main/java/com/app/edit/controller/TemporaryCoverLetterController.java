package com.app.edit.controller;

import com.app.edit.service.TemporaryCoverLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class TemporaryCoverLetterController {

    private final TemporaryCoverLetterService temporaryCoverLetterService;

    @Autowired
    public TemporaryCoverLetterController(TemporaryCoverLetterService temporaryCoverLetterService) {
        this.temporaryCoverLetterService = temporaryCoverLetterService;
    }
}
