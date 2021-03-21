package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.domain.temporarycomment.TemporaryCommentRepository;
import com.app.edit.enums.State;
import com.app.edit.response.comment.GetMyCommentRes;
import com.app.edit.response.comment.GetMyCommentsRes;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.app.edit.config.BaseResponseStatus.NOT_FOUND_TEMPORARY_COMMENT;
import static com.app.edit.config.BaseResponseStatus.NOT_FOUND_TEMPORARY_COVER_LETTER;

@Service
@Transactional(readOnly = true)
public class TemporaryCommentProvider {

    private final TemporaryCommentRepository temporaryCommentRepository;
    private final UserProvider userProvider;

    @Autowired
    public TemporaryCommentProvider(TemporaryCommentRepository temporaryCommentRepository, UserProvider userProvider) {
        this.temporaryCommentRepository = temporaryCommentRepository;
        this.userProvider = userProvider;
    }

    /**
     * 내 임시 코멘트 조회
     * @param userInfoId
     * @return
     */
    public GetMyCommentsRes getMyTemporaryComment(Long userInfoId) throws BaseException {

        GetMyCommentRes getMyCommentRes =
                temporaryCommentRepository.findMyTemporaryComment(userInfoId, State.ACTIVE)
                .orElseThrow(() -> new BaseException(NOT_FOUND_TEMPORARY_COMMENT));

        return GetMyCommentsRes.builder()
                .commentInfo(getMyCommentRes)
                .userInfo(userProvider.retrieveSympathizeUser(userInfoId))
                .build();
    }
}
