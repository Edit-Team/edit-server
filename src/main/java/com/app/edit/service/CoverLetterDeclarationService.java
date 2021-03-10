package com.app.edit.service;

import com.app.edit.config.BaseException;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.coverletterdeclaration.CoverLetterDeclaration;
import com.app.edit.domain.coverletterdeclaration.CoverLetterDeclarationRepository;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.IsProcessing;
import com.app.edit.provider.CoverLetterProvider;
import com.app.edit.provider.UserInfoProvider;
import com.app.edit.request.coverletter.PostCoverLetterDeclarationReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CoverLetterDeclarationService {

    private final CoverLetterDeclarationRepository coverLetterDeclarationRepository;
    private final CoverLetterProvider coverLetterProvider;
    private final UserInfoProvider userInfoProvider;

    @Autowired
    public CoverLetterDeclarationService(CoverLetterDeclarationRepository coverLetterDeclarationRepository, CoverLetterProvider coverLetterProvider, UserInfoProvider userInfoProvider) {
        this.coverLetterDeclarationRepository = coverLetterDeclarationRepository;
        this.coverLetterProvider = coverLetterProvider;
        this.userInfoProvider = userInfoProvider;
    }

    public Long createCoverLetterDeclaration(PostCoverLetterDeclarationReq request) throws BaseException {
        Long userInfoId = 1L;
        Long coverLetterId = request.getCoverLetterId();
        UserInfo userInfo = userInfoProvider.getUserInfoById(userInfoId);
        CoverLetter coverLetter = coverLetterProvider.getCoverLetterById(coverLetterId);
        CoverLetterDeclaration coverLetterDeclaration = CoverLetterDeclaration.builder()
                .coverLetter(coverLetter)
                .isProcessing(IsProcessing.NO)
                .build();
        userInfo.addCoverLetterDeclaration(coverLetterDeclaration);
        coverLetterDeclarationRepository.save(coverLetterDeclaration);
        return coverLetterId;
    }
}
