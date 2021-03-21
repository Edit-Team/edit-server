package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.domain.temporarycomment.TemporaryComment;
import com.app.edit.domain.temporarycomment.TemporaryCommentRepository;
import com.app.edit.enums.State;
import com.app.edit.response.comment.GetMyCommentRes;
import com.app.edit.response.comment.GetMyCommentsRes;
import com.app.edit.response.coverletter.GetCoverLettersByCommentRes;
import com.app.edit.response.temporaryComment.GetTemporaryCommentRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.app.edit.config.BaseResponseStatus.NOT_FOUND_TEMPORARY_COMMENT;

@Service
@Transactional(readOnly = true)
public class TemporaryCommentProvider {

    private final CoverLetterProvider coverLetterProvider;
    private final TemporaryCommentRepository temporaryCommentRepository;
    private final UserProvider userProvider;

    @Autowired
    public TemporaryCommentProvider(CoverLetterProvider coverLetterProvider, TemporaryCommentRepository temporaryCommentRepository,
                                    UserProvider userProvider) {
        this.coverLetterProvider = coverLetterProvider;
        this.temporaryCommentRepository = temporaryCommentRepository;
        this.userProvider = userProvider;
    }

    /**
     * 내 임시 코멘트 조회
     * @param userInfoId
     * @return
     */
    public GetMyCommentsRes getMyTemporaryComments(Long userInfoId) throws BaseException {

        GetMyCommentRes getMyCommentRes =
                temporaryCommentRepository.findMyTemporaryComment(userInfoId, State.ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TEMPORARY_COMMENT));

        return GetMyCommentsRes.builder()
                .commentInfo(getMyCommentRes)
                .userInfo(userProvider.retrieveSympathizeUser(userInfoId))
                .build();
    }

    /**
     * 코멘트 이어서 작성하기
     * @param temporaryCommentId
     * @param userInfoId
     * @return
     */
    public GetTemporaryCommentRes getMyTemporaryComment(Long temporaryCommentId, Long userInfoId) throws BaseException {

        GetMyCommentRes getMyCommentRes =
                temporaryCommentRepository.findMyTemporaryComment(userInfoId, State.ACTIVE)
                        .orElseThrow(() -> new BaseException(NOT_FOUND_TEMPORARY_COMMENT));

        TemporaryComment temporaryComment = getTemporaryCommentById(temporaryCommentId);

        return GetTemporaryCommentRes.builder()
                .getCoverLettersByCommentRes(temporaryCommentToGetCoverLettersByCommentRes(temporaryComment,userInfoId))
                .getMyCommentRes(getMyCommentRes)
                .build();
    }

    public TemporaryComment getTemporaryCommentById(Long temporaryCommentId) throws BaseException{
        return temporaryCommentRepository.findByIdAndState(temporaryCommentId,State.ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TEMPORARY_COMMENT));
    }

    public GetCoverLettersByCommentRes temporaryCommentToGetCoverLettersByCommentRes
            (TemporaryComment temporaryComment, Long userInfoId) throws BaseException {
        return GetCoverLettersByCommentRes.builder()
                .userInfo(userProvider.retrieveSympathizeUser(userInfoId))
                .coverLetterId(temporaryComment.getId())
                .coverLetterContent(temporaryComment.getContent())
                .coverLetterCategoryName(null)
                .build();
    }

}
