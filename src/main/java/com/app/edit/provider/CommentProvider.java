package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.domain.comment.Comment;
import com.app.edit.domain.comment.CommentRepository;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.enums.IsAdopted;
import com.app.edit.enums.State;
import com.app.edit.response.comment.CommentInfo;
import com.app.edit.response.comment.GetCommentsRes;
import com.app.edit.response.comment.GetNotAdoptedCommentContentsRes;
import com.app.edit.response.coverletter.GetCoverLettersRes;
import com.app.edit.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.app.edit.config.BaseResponseStatus.*;
import static com.app.edit.config.Constant.PROFILE_SEPARATOR;
import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@Service
public class CommentProvider {

    private final CommentRepository commentRepository;
    private final CoverLetterProvider coverLetterProvider;
    private final JwtService jwtService;

    @Autowired
    public CommentProvider(CommentRepository commentRepository, CoverLetterProvider coverLetterProvider, JwtService jwtService) {
        this.commentRepository = commentRepository;
        this.coverLetterProvider = coverLetterProvider;
        this.jwtService = jwtService;
    }

    public GetCommentsRes retrieveCommentsByCoverLetterId(Pageable pageable, Long coverLetterId) throws BaseException {
        Long userInfoId = jwtService.getUserInfo().getUserId();
        CoverLetter coverLetter = coverLetterProvider.getCoverLetterById(coverLetterId);
        List<CommentInfo> commentInfos = commentRepository.findCommentsByCoverLetter(pageable, coverLetter, State.ACTIVE).stream()
                .map(comment -> {
                    CommentInfo commentInfo = comment.toCommentInfo();
                    setIsMineToCommentInfo(userInfoId, comment, commentInfo);
                    setUserProfileToCommentInfo(comment, commentInfo);
                    return commentInfo;
                })
                .collect(toList());
        GetCoverLettersRes coverLetterInfo = coverLetter.toGetCoverLetterRes();
        if (coverLetter.getUserInfo().getId().equals(userInfoId)) {
            coverLetterInfo.setIsMine(true);
        }
        coverLetterInfo.setIsSympathy(null);
        coverLetterInfo.setSympathiesCount(null);
        return new GetCommentsRes(coverLetterInfo, commentInfos);
    }

    private void setIsMineToCommentInfo(Long userInfoId, Comment comment, CommentInfo commentInfo) {
        if (comment.getUserInfo().getId().equals(userInfoId)) {
            commentInfo.setIsMine(true);
        }
    }

    private void setUserProfileToCommentInfo(Comment comment, CommentInfo commentInfo) {
        String profileColorName = comment.getUserInfo().getUserProfile().getProfileColor().getName();
        String profileEmotionName = comment.getUserInfo().getUserProfile().getProfileEmotion().getName();
        commentInfo.setUserProfile(profileColorName + PROFILE_SEPARATOR + profileEmotionName);
    }

    public Comment getCommentById(Long commentId) throws BaseException {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new BaseException(NOT_FOUND_COMMENT);
        }
        if (comment.get().getState().equals(State.INACTIVE)) {
            throw new BaseException(ALREADY_DELETED_COMMENT);
        }
        return comment.get();
    }

    public GetNotAdoptedCommentContentsRes getNotAdoptedCommentContentsById(Long coverLetterId, Pageable pageable) throws BaseException {
        CoverLetter coverLetter = coverLetterProvider.getCoverLetterById(coverLetterId);
        Page<Comment> notAdoptedComments = commentRepository
                .findNotAdoptedCommentsByCoverLetter(pageable, coverLetter, IsAdopted.NO, State.ACTIVE);
        List<String> notAdoptedCommentContents = notAdoptedComments.stream()
                .map(Comment::getContent)
                .collect(toList());
        return new GetNotAdoptedCommentContentsRes(notAdoptedCommentContents);
    }
}
