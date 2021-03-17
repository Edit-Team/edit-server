package com.app.edit.provider;

import com.app.edit.domain.commentdeclaration.CommentDeclarationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CommentDeclarationProvider {

    private final CommentDeclarationRepository commentDeclarationRepository;

    @Autowired
    public CommentDeclarationProvider(CommentDeclarationRepository commentDeclarationRepository) {
        this.commentDeclarationRepository = commentDeclarationRepository;
    }
}
