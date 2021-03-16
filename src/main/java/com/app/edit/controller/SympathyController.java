package com.app.edit.controller;

import com.app.edit.domain.sympathy.Sympathy;
import com.app.edit.service.SympathyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class SympathyController {

    private final SympathyService sympathyService;

    @Autowired
    public SympathyController(SympathyService sympathyService) {
        this.sympathyService = sympathyService;
    }
}
