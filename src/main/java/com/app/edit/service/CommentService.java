package com.app.edit.service;

import com.app.edit.config.BaseException;
import com.app.edit.domain.comment.Comment;
import com.app.edit.domain.comment.CommentRepository;
import com.app.edit.domain.temporarycomment.TemporaryComment;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.domain.user.UserInfoRepository;
import com.app.edit.enums.IsAdopted;
import com.app.edit.enums.State;
import com.app.edit.provider.CommentProvider;
import com.app.edit.provider.CoverLetterProvider;
import com.app.edit.provider.TemporaryCommentProvider;
import com.app.edit.request.comment.PostCommentReq;
import com.app.edit.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.app.edit.config.BaseResponseStatus.*;

@Transactional
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentProvider commentProvider;
    private final UserInfoRepository userInfoRepository;
    private final CoverLetterProvider coverLetterProvider;
    private final TemporaryCommentProvider temporaryCommentProvider;
    private final JwtService jwtService;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          CommentProvider commentProvider,
                          UserInfoRepository userInfoRepository,
                          CoverLetterProvider coverLetterProvider, TemporaryCommentProvider temporaryCommentProvider, JwtService jwtService) {
        this.commentRepository = commentRepository;
        this.commentProvider = commentProvider;
        this.userInfoRepository = userInfoRepository;
        this.coverLetterProvider = coverLetterProvider;
        this.temporaryCommentProvider = temporaryCommentProvider;
        this.jwtService = jwtService;
    }

    public Long updateCommentAdoptedById(Long commentId) throws BaseException {
        Long userInfoId = jwtService.getUserInfo().getUserId();
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

    /**
     * 코멘트 등록
     * @param userId
     * @param parameters
     * @throws BaseException
     */
    public void createComment(Long userId, PostCommentReq parameters) throws BaseException{

        UserInfo userInfo = userInfoRepository.findByStateAndId(State.ACTIVE,userId)
                .orElseThrow(() -> new BaseException(NOT_FOUND_USER));

        Comment comment = Comment.builder()
                .userInfo(userInfo)
                .coverLetter(coverLetterProvider.getCoverLetterById(parameters.getCoverLetterId()))
                .sentenceEvaluation(parameters.getSentenceEvaluation())
                .concretenessLogic(parameters.getConcretenessLogic())
                .sincerity(parameters.getSincerity())
                .activity(parameters.getActivity())
                .content(parameters.getContent())
                .isAdopted(IsAdopted.NO)
                .state(State.ACTIVE)
                .build();

        try {
            commentRepository.save(comment);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_COMMENT);
        }

        List<TemporaryComment> temporaryCommentList =
                temporaryCommentProvider.getTemporaryCommentByUserInfoIdAndStatus(userId,parameters.getCoverLetterId());

        if(temporaryCommentList.size() > 0){
            temporaryCommentList.forEach(temporaryComment
                    -> temporaryComment.setState(State.INACTIVE));
        }

    }
}
