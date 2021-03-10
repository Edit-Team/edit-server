package com.app.edit.service;

import com.app.edit.config.BaseException;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.coverletter.CoverLetterRepository;
import com.app.edit.domain.coverlettercategory.CoverLetterCategory;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.CoverLetterType;
import com.app.edit.enums.State;
import com.app.edit.provider.CoverLetterCategoryProvider;
import com.app.edit.provider.UserInfoProvider;
import com.app.edit.request.coverletter.PostCoverLetterReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.app.edit.config.Constant.DEFAULT_ORIGINAL_COVER_LETTER_ID;

@Transactional
@Service
public class CoverLetterService {

    private final CoverLetterRepository coverLetterRepository;
    private final UserInfoProvider userInfoProvider;
    private final CoverLetterCategoryProvider coverLetterCategoryProvider;

    @Autowired
    public CoverLetterService(CoverLetterRepository coverLetterRepository, UserInfoProvider userInfoProvider, CoverLetterCategoryProvider coverLetterCategoryProvider) {
        this.coverLetterRepository = coverLetterRepository;
        this.userInfoProvider = userInfoProvider;
        this.coverLetterCategoryProvider = coverLetterCategoryProvider;
    }

    public Long createCoverLetter(PostCoverLetterReq request) throws BaseException {
        Long userId = 1L;
        Long coverLetterCategoryId = request.getCoverLetterCategoryId();
        CoverLetterCategory coverLetterCategory = coverLetterCategoryProvider
                .getCoverLetterCategoryById(coverLetterCategoryId);
        UserInfo userInfo = userInfoProvider.getUserInfoById(userId);
        CoverLetter requestedCoverLetter = CoverLetter.builder()
                .coverLetterCategory(coverLetterCategory)
                .originalCoverLetterId(DEFAULT_ORIGINAL_COVER_LETTER_ID)
                .content(request.getCoverLetterContent())
                .state(State.ACTIVE)
                .type(CoverLetterType.WRITING)
                .build();
        userInfo.addCoverLetter(requestedCoverLetter);
        CoverLetter savedCoverLetter = coverLetterRepository.save(requestedCoverLetter);
        return savedCoverLetter.getId();
    }
}
