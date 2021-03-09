package com.app.edit.provider;

import com.app.edit.domain.comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CommentProvider {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentProvider(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
