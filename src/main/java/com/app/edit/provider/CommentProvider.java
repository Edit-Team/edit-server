package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.domain.comment.CommentRepository;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.response.comment.GetCommentsRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
