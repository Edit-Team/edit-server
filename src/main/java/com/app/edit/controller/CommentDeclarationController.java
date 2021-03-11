package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.request.comment.PostCommentDeclarationReq;
import com.app.edit.service.CommentDeclarationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class CommentDeclarationController {

    private final CommentDeclarationService commentDeclarationService;

    @Autowired
    public CommentDeclarationController(CommentDeclarationService commentDeclarationService) {
        this.commentDeclarationService = commentDeclarationService;
    }

    /*
     * 코멘트 신고 API
     **/
    @ApiOperation(value = "코멘트 신고 API")
    @PostMapping("/declare-comments")
    public BaseResponse<Long> postCommentDeclaration(@RequestBody @Valid PostCommentDeclarationReq request) throws BaseException {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, commentDeclarationService.createCommentDeclaration(request));
    }
}
