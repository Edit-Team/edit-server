package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.domain.comment.Comment;
import com.app.edit.domain.comment.CommentRepository;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.enums.State;
import com.app.edit.response.comment.CommentInfo;
import com.app.edit.response.comment.GetCommentsRes;
import com.app.edit.response.coverletter.GetCoverLettersRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.app.edit.config.BaseResponseStatus.NOT_FOUND_COMMENT;
import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@Service
public class CommentProvider {

    private final CommentRepository commentRepository;
    private final CoverLetterProvider coverLetterProvider;

    @Autowired
    public CommentProvider(CommentRepository commentRepository, CoverLetterProvider coverLetterProvider) {
        this.commentRepository = commentRepository;
        this.coverLetterProvider = coverLetterProvider;
    }

    public GetCommentsRes retrieveCommentsByCoverLetterId(Pageable pageable, Long coverLetterId) throws BaseException {
        Long userInfoId = 1L;
        CoverLetter coverLetter = coverLetterProvider.getCoverLetterById(coverLetterId);
        List<CommentInfo> commentInfos = commentRepository.findCommentsByCoverLetter(pageable, coverLetter, State.ACTIVE).stream()
                .map(comment -> {
                    CommentInfo commentInfo = comment.toCommentInfo();
                    if (comment.getUserInfo().getId().equals(userInfoId)) {
                        commentInfo.setMine(true);
                    }
                    return commentInfo;
                })
                .collect(toList());
        GetCoverLettersRes coverLetterInfo = coverLetter.toGetCoverLetterRes();
        return new GetCommentsRes(coverLetterInfo, commentInfos);
    }

    public Comment getCommentById(Long commentId) throws BaseException {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new BaseException(NOT_FOUND_COMMENT);
        }
        return comment.get();
    }
}
