package com.app.edit.service;

import com.app.edit.domain.commentdeclaration.CommentDeclarationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentDeclarationService {

    private final CommentDeclarationRepository commentDeclarationRepository;

    @Autowired
    public CommentDeclarationService(CommentDeclarationRepository commentDeclarationRepository) {
        this.commentDeclarationRepository = commentDeclarationRepository;
    }
}
