package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.domain.comment.Comment;
import com.app.edit.domain.comment.CommentRepository;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.response.comment.GetCommentsRes;
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

    public List<GetCommentsRes> retrieveCommentsByCoverLetterId(Pageable pageable, Long coverLetterId) throws BaseException {
        CoverLetter coverLetter = coverLetterProvider.getCoverLetterById(coverLetterId);
        return commentRepository.findCommentsByCoverLetter(pageable, coverLetter).stream()
                .map(comment -> comment.toGetCommentsRes())
                .collect(toList());
    }

    public Comment getCommentById(Long commentId) throws BaseException {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new BaseException(NOT_FOUND_COMMENT);
        }
        return comment.get();
    }
}