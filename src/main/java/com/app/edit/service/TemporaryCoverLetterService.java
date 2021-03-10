package com.app.edit.service;

import com.app.edit.config.BaseException;
import com.app.edit.domain.coverlettercategory.CoverLetterCategory;
import com.app.edit.domain.temporarycoverletter.TemporaryCoverLetter;
import com.app.edit.domain.temporarycoverletter.TemporaryCoverLetterRepository;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.CoverLetterType;
import com.app.edit.enums.State;
import com.app.edit.provider.CoverLetterCategoryProvider;
import com.app.edit.provider.UserInfoProvider;
import com.app.edit.request.temporarycoverletter.PostTemporaryCoverLetterReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TemporaryCoverLetterService {

    private final TemporaryCoverLetterRepository temporaryCoverLetterRepository;
    private final CoverLetterCategoryProvider coverLetterCategoryProvider;
    private final UserInfoProvider userInfoProvider;

    @Autowired
    public TemporaryCoverLetterService(TemporaryCoverLetterRepository temporaryCoverLetterRepository, CoverLetterCategoryProvider coverLetterCategoryProvider, UserInfoProvider userInfoProvider) {
        this.temporaryCoverLetterRepository = temporaryCoverLetterRepository;
        this.coverLetterCategoryProvider = coverLetterCategoryProvider;
        this.userInfoProvider = userInfoProvider;
    }

    public Long createWritingTemporaryCoverLetter(PostTemporaryCoverLetterReq request) throws BaseException {
        Long userInfoId = 1L;
        Long coverLetterCategoryId = request.getCoverLetterCategoryId();
        String content = request.getCoverLetterContent();
        UserInfo userInfo = userInfoProvider.getUserInfoById(userInfoId);
        CoverLetterCategory coverLetterCategory = coverLetterCategoryProvider
                .getCoverLetterCategoryById(coverLetterCategoryId);
        TemporaryCoverLetter requestedWritingTemporaryCoverLetter = TemporaryCoverLetter.builder()
                .coverLetterCategory(coverLetterCategory)
                .content(content)
                .state(State.ACTIVE)
                .type(CoverLetterType.WRITING)
                .build();
        userInfo.addTemporaryCoverLetter(requestedWritingTemporaryCoverLetter);
        TemporaryCoverLetter savedTemporaryCoverLetter = temporaryCoverLetterRepository
                .save(requestedWritingTemporaryCoverLetter);
        return savedTemporaryCoverLetter.getId();
    }
}
