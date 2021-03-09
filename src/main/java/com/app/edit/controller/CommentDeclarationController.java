package com.app.edit.controller;

import com.app.edit.domain.commentdeclaration.CommentDeclarationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class CommentDeclarationController {

    private final CommentDeclarationRepository commentDeclarationRepository;

    @Autowired
    public CommentDeclarationController(CommentDeclarationRepository commentDeclarationRepository) {
        this.commentDeclarationRepository = commentDeclarationRepository;
    }
}
