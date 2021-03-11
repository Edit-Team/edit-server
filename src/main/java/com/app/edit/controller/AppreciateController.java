package com.app.edit.controller;

import com.app.edit.service.AppreciateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class AppreciateController {

    private final AppreciateService appreciateService;

    @Autowired
    public AppreciateController(AppreciateService appreciateService) {
        this.appreciateService = appreciateService;
    }
}
