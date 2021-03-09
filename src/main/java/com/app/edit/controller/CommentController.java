package com.app.edit.controller;

import com.app.edit.provider.CommentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentProvider commentProvider;

    @Autowired
    public CommentController(CommentProvider commentProvider) {
        this.commentProvider = commentProvider;
    }
}
