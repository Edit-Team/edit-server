package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.domain.comment.Comment;
import com.app.edit.domain.comment.CommentRepository;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.IsAdopted;
import com.app.edit.enums.State;
import com.app.edit.response.comment.*;
import com.app.edit.response.coverletter.GetCoverLettersByCommentRes;
import com.app.edit.response.coverletter.GetCoverLettersRes;
import com.app.edit.response.user.GetUserInfosRes;
import com.app.edit.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.app.edit.config.BaseResponseStatus.*;
import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@Service
public class CommentProvider {

    private final CommentRepository commentRepository;
    private final CoverLetterProvider coverLetterProvider;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    public CommentProvider(CommentRepository commentRepository, CoverLetterProvider coverLetterProvider,
                           UserProvider userProvider, JwtService jwtService) {
        this.commentRepository = commentRepository;
        this.coverLetterProvider = coverLetterProvider;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }

    public GetCommentsRes retrieveCommentsByCoverLetterId(Pageable pageable, Long coverLetterId) throws BaseException {
        Long userInfoId = jwtService.getUserInfo().getUserId();
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

    /**
     * 내가 작성한 코멘트 조회
     * @param pageable
     * @param userInfoId
     * @return
     */
    public List<GetMyCommentsRes> retrieveMyComments(Pageable pageable, Long userInfoId) throws BaseException{

        // 내가 작성한 코멘트 조회
        Page<Comment> commentList = commentRepository.findByUser(pageable, userInfoId, State.ACTIVE);

        if(commentList.getSize() == 0)
            throw new BaseException(NOT_FOUND_COMMENT);

        GetUserInfosRes getUserInfosRes = userProvider.retrieveSympathizeUser(userInfoId);

        return commentList.stream()
                .map(comment -> GetMyCommentsRes.builder()
                        .userInfo(getUserInfosRes)
                        .commentInfo(GetMyCommentRes.builder()
                                .commentId(comment.getId())
                                .activity(comment.getActivity())
                                .commentContent(comment.getContent())
                                .concretenessLogic(comment.getConcretenessLogic())
                                .sentenceEvaluation(comment.getSentenceEvaluation())
                                .sincerity(comment.getSincerity())
                                .build())
                        .build())
                .collect(toList());
    }

    /**
     * 내가 작성한 코멘트 문장 조회
     * @return
     */
    public GetMyCommentWithCoverLetterRes getMyCommentWithCoverLetter(Long commentId, Long userInfoId) throws BaseException {

        Comment comment = commentRepository.findByIdAndState(commentId,State.ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_COMMENT));

        CoverLetter coverLetter = coverLetterProvider.getCoverLetterById(comment.getCoverLetter().getId());

        return GetMyCommentWithCoverLetterRes.builder()
                .commentRes(GetMyCommentsRes.builder()
                        .commentInfo(commentToGetMyCommentRes(comment))
                        .userInfo(userProvider.retrieveSympathizeUser(userInfoId))
                        .build())
                .coverLetterRes(GetCoverLettersByCommentRes.builder()
                        .userInfo(userProvider.retrieveSympathizeUser(coverLetter.getUserInfo().getId()))
                        .coverLetterId(coverLetter.getId())
                        .coverLetterContent(coverLetter.getContent())
                        .coverLetterCategoryName(coverLetter.getContent())
                        .build())
                .build();
    }


    public GetMyCommentRes commentToGetMyCommentRes(Comment comment){
        return GetMyCommentRes.builder()
                .commentId(comment.getId())
                .activity(comment.getActivity())
                .commentContent(comment.getContent())
                .concretenessLogic(comment.getConcretenessLogic())
                .sentenceEvaluation(comment.getSentenceEvaluation())
                .sincerity(comment.getSincerity())
                .build();
    }


    /**
     * 코멘트 등록할때 자소서 조회
     * @param userInfoId
     * @param coverLetterId
     * @return
     */
    public GetCoverLettersByCommentRes retrieveCommentWithCoverLetter(Long userInfoId, Long coverLetterId) throws BaseException {

        CoverLetter coverLetter = coverLetterProvider.getCoverLetterById(coverLetterId);


        return GetCoverLettersByCommentRes.builder()
                .userInfo(userProvider.retrieveSympathizeUser(userInfoId))
                .coverLetterId(coverLetterId)
                .coverLetterContent(coverLetter.getContent())
                .coverLetterCategoryName(null)
                .build();
    }
}
