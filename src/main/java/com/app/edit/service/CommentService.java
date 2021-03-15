package com.app.edit.service;

import com.app.edit.config.BaseException;
import com.app.edit.domain.comment.Comment;
import com.app.edit.domain.comment.CommentRepository;
import com.app.edit.enums.IsAdopted;
import com.app.edit.provider.CommentProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.app.edit.config.BaseResponseStatus.CAN_NOT_ADOPT_COMMENT_MORE_THAN_ONE;
import static com.app.edit.config.BaseResponseStatus.DO_NOT_HAVE_PERMISSION;

@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentProvider commentProvider;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentProvider commentProvider) {
        this.commentRepository = commentRepository;
        this.commentProvider = commentProvider;
    }

    public Long updateCommentAdoptedById(Long commentId) throws BaseException {
        Long userInfoId = 1L;
        Comment selectedComment = commentProvider.getCommentById(commentId);
        validateUser(userInfoId, selectedComment);
        validateAdoptedComment(selectedComment);
        selectedComment.setIsAdopted(IsAdopted.YES);
        commentRepository.save(selectedComment);
        return selectedComment.getId();
    }

    private void validateUser(Long userInfoId, Comment selectedComment) throws BaseException {
        if (!selectedComment.getCoverLetter().getUserInfo().getId().equals(userInfoId)) {
            throw new BaseException(DO_NOT_HAVE_PERMISSION);
        }
    }

    private void validateAdoptedComment(Comment selectedComment) throws BaseException {
        boolean hasAdoptedComment = selectedComment.getCoverLetter().getComments().stream()
                .anyMatch(comment -> comment.getIsAdopted().equals(IsAdopted.YES));
        if (hasAdoptedComment) {
            throw new BaseException(CAN_NOT_ADOPT_COMMENT_MORE_THAN_ONE);
        }
    }
}
