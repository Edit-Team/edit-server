package com.app.edit.service;

import com.app.edit.config.BaseException;
import com.app.edit.domain.comment.Comment;
import com.app.edit.domain.commentdeclaration.CommentDeclaration;
import com.app.edit.domain.commentdeclaration.CommentDeclarationRepository;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.IsProcessing;
import com.app.edit.provider.CommentProvider;
import com.app.edit.provider.UserInfoProvider;
import com.app.edit.request.comment.PostCommentDeclarationReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CommentDeclarationService {

    private final CommentDeclarationRepository commentDeclarationRepository;
    private final UserInfoProvider userInfoProvider;
    private final CommentProvider commentProvider;

    @Autowired
    public CommentDeclarationService(CommentDeclarationRepository commentDeclarationRepository, UserInfoProvider userInfoProvider, CommentProvider commentProvider) {
        this.commentDeclarationRepository = commentDeclarationRepository;
        this.userInfoProvider = userInfoProvider;
        this.commentProvider = commentProvider;
    }

    public Long createCommentDeclaration(PostCommentDeclarationReq request) throws BaseException {
        Long userId = 1L;
        Long commentId = request.getCommentId();
        UserInfo userInfo = userInfoProvider.getUserInfoById(userId);
        Comment comment = commentProvider.getCommentById(commentId);
        CommentDeclaration commentDeclaration = CommentDeclaration.builder()
                .comment(comment)
                .isProcessing(IsProcessing.NO)
                .build();
        userInfo.addCommentDeclaration(commentDeclaration);
        commentDeclarationRepository.save(commentDeclaration);
        return commentId;
    }
}
