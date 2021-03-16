package com.app.edit.service;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.coverlettercategory.CoverLetterCategory;
import com.app.edit.domain.temporarycoverletter.TemporaryCoverLetter;
import com.app.edit.domain.temporarycoverletter.TemporaryCoverLetterRepository;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.CoverLetterType;
import com.app.edit.enums.State;
import com.app.edit.provider.CoverLetterCategoryProvider;
import com.app.edit.provider.CoverLetterProvider;
import com.app.edit.provider.TemporaryCoverLetterProvider;
import com.app.edit.provider.UserInfoProvider;
import com.app.edit.request.temporarycoverletter.PatchCompletingTemporaryCoverLetterReq;
import com.app.edit.request.temporarycoverletter.PatchWritingTemporaryCoverLetterReq;
import com.app.edit.request.temporarycoverletter.PostCompletingTemporaryCoverLetterReq;
import com.app.edit.request.temporarycoverletter.PostWritingTemporaryCoverLetterReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.app.edit.config.Constant.DEFAULT_ORIGINAL_COVER_LETTER_ID;

@Transactional
@Service
public class TemporaryCoverLetterService {

    private final TemporaryCoverLetterRepository temporaryCoverLetterRepository;
    private final TemporaryCoverLetterProvider temporaryCoverLetterProvider;
    private final CoverLetterCategoryProvider coverLetterCategoryProvider;
    private final CoverLetterProvider coverLetterProvider;
    private final UserInfoProvider userInfoProvider;

    @Autowired
    public TemporaryCoverLetterService(TemporaryCoverLetterRepository temporaryCoverLetterRepository, TemporaryCoverLetterProvider temporaryCoverLetterProvider, CoverLetterCategoryProvider coverLetterCategoryProvider, CoverLetterProvider coverLetterProvider, UserInfoProvider userInfoProvider) {
        this.temporaryCoverLetterRepository = temporaryCoverLetterRepository;
        this.temporaryCoverLetterProvider = temporaryCoverLetterProvider;
        this.coverLetterCategoryProvider = coverLetterCategoryProvider;
        this.coverLetterProvider = coverLetterProvider;
        this.userInfoProvider = userInfoProvider;
    }

    public Long createWritingTemporaryCoverLetter(PostWritingTemporaryCoverLetterReq request) throws BaseException {
        Long userInfoId = 1L;
        Long coverLetterCategoryId = request.getCoverLetterCategoryId();
        String content = request.getCoverLetterContent();
        UserInfo userInfo = userInfoProvider.getUserInfoById(userInfoId);
        CoverLetterCategory coverLetterCategory = coverLetterCategoryProvider
                .getCoverLetterCategoryById(coverLetterCategoryId);
        TemporaryCoverLetter requestedWritingTemporaryCoverLetter = TemporaryCoverLetter.builder()
                .coverLetterCategory(coverLetterCategory)
                .originalCoverLetterId(DEFAULT_ORIGINAL_COVER_LETTER_ID)
                .content(content)
                .state(State.ACTIVE)
                .type(CoverLetterType.WRITING)
                .build();
        userInfo.addTemporaryCoverLetter(requestedWritingTemporaryCoverLetter);
        TemporaryCoverLetter savedTemporaryCoverLetter = temporaryCoverLetterRepository
                .save(requestedWritingTemporaryCoverLetter);
        return savedTemporaryCoverLetter.getId();
    }

    public Long createCompletingTemporaryCoverLetter(PostCompletingTemporaryCoverLetterReq request) throws BaseException {
        Long userInfoId = 1L;
        Long originalCoverLetterId = request.getOriginalCoverLetterId();
        String content = request.getCoverLetterContent();
        CoverLetter originalCoverLetter = coverLetterProvider.getCoverLetterById(originalCoverLetterId);
        CoverLetterCategory originalCoverLetterCategory = originalCoverLetter.getCoverLetterCategory();
        UserInfo userInfo = userInfoProvider.getUserInfoById(userInfoId);
        TemporaryCoverLetter requestedCompletingTemporaryCoverLetter = TemporaryCoverLetter.builder()
                .coverLetterCategory(originalCoverLetterCategory)
                .originalCoverLetterId(originalCoverLetterId)
                .content(content)
                .state(State.ACTIVE)
                .type(CoverLetterType.COMPLETING)
                .build();
        userInfo.addTemporaryCoverLetter(requestedCompletingTemporaryCoverLetter);
        TemporaryCoverLetter savedTemporaryCoverLetter = temporaryCoverLetterRepository.save(requestedCompletingTemporaryCoverLetter);
        return savedTemporaryCoverLetter.getId();
    }

    public Long updateWritingTemporaryCoverLetterById(Long temporaryCoverLetterId, PatchWritingTemporaryCoverLetterReq request) throws BaseException {
        Long userInfoId = 1L;
        UserInfo userInfo = userInfoProvider.getUserInfoById(userInfoId);
        Long coverLetterCategoryId = request.getCoverLetterCategoryId();
        String coverLetterContent = request.getCoverLetterContent();
        CoverLetterCategory coverLetterCategory = coverLetterCategoryProvider
                .getCoverLetterCategoryById(coverLetterCategoryId);
        TemporaryCoverLetter temporaryCoverLetter = temporaryCoverLetterProvider
                .getTemporaryCoverLetterById(temporaryCoverLetterId);
        validateUser(userInfo, temporaryCoverLetter);
        temporaryCoverLetter.setCoverLetterCategory(coverLetterCategory);
        temporaryCoverLetter.setContent(coverLetterContent);
        temporaryCoverLetterRepository.save(temporaryCoverLetter);
        return temporaryCoverLetterId;
    }

    public Long updateCompletingTemporaryCoverLetterById(Long temporaryCoverLetterId, PatchCompletingTemporaryCoverLetterReq request) throws BaseException {
        Long userInfoId = 1L;
        UserInfo userInfo = userInfoProvider.getUserInfoById(userInfoId);
        String coverLetterContent = request.getCoverLetterContent();
        TemporaryCoverLetter temporaryCoverLetter = temporaryCoverLetterProvider
                .getTemporaryCoverLetterById(temporaryCoverLetterId);
        validateUser(userInfo, temporaryCoverLetter);
        temporaryCoverLetter.setContent(coverLetterContent);
        temporaryCoverLetterRepository.save(temporaryCoverLetter);
        return temporaryCoverLetterId;
    }

    private void validateUser(UserInfo userInfo, TemporaryCoverLetter temporaryCoverLetter) throws BaseException {
        if (!temporaryCoverLetter.getUserInfo().equals(userInfo)) {
            throw new BaseException(BaseResponseStatus.DO_NOT_HAVE_PERMISSION);
        }
    }
}
