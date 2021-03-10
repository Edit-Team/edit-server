package com.app.edit.controller;

import com.app.edit.service.CommentDeclarationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class CommentDeclarationController {

    private final CommentDeclarationService commentDeclarationService;

    @Autowired
    public CommentDeclarationController(CommentDeclarationService commentDeclarationService) {
        this.commentDeclarationService = commentDeclarationService;
    }
}
