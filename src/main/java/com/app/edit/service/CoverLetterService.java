package com.app.edit.service;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.coverletter.CoverLetterRepository;
import com.app.edit.domain.coverlettercategory.CoverLetterCategory;
import com.app.edit.domain.temporarycoverletter.TemporaryCoverLetter;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.CoverLetterType;
import com.app.edit.enums.State;
import com.app.edit.provider.CoverLetterCategoryProvider;
import com.app.edit.provider.CoverLetterProvider;
import com.app.edit.provider.UserInfoProvider;
import com.app.edit.request.coverletter.PostCompletingCoverLetterReq;
import com.app.edit.request.coverletter.PostWritingCoverLetterReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.app.edit.config.Constant.DEFAULT_ORIGINAL_COVER_LETTER_ID;

@Transactional
@Service
public class CoverLetterService {

    private final CoverLetterRepository coverLetterRepository;
    private final UserInfoProvider userInfoProvider;
    private final CoverLetterProvider coverLetterProvider;
    private final CoverLetterCategoryProvider coverLetterCategoryProvider;

    @Autowired
    public CoverLetterService(CoverLetterRepository coverLetterRepository, UserInfoProvider userInfoProvider,
                              CoverLetterProvider coverLetterProvider,
                              CoverLetterCategoryProvider coverLetterCategoryProvider) {
        this.coverLetterRepository = coverLetterRepository;
        this.userInfoProvider = userInfoProvider;
        this.coverLetterProvider = coverLetterProvider;
        this.coverLetterCategoryProvider = coverLetterCategoryProvider;
    }

    public Long createWritingCoverLetter(PostWritingCoverLetterReq request) throws BaseException {
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

    public Long createCompletingCoverLetter(PostCompletingCoverLetterReq request) throws BaseException {
        Long userInfoId = 1L;
        Long originalCoverLetterId = request.getOriginalCoverLetterId();
        String content = request.getCoverLetterContent();
        CoverLetter originalCoverLetter = coverLetterProvider.getCoverLetterById(originalCoverLetterId);
        CoverLetterCategory originalCoverLetterCategory = originalCoverLetter.getCoverLetterCategory();
        UserInfo userInfo = userInfoProvider.getUserInfoById(userInfoId);
        CoverLetter requestedCompletingCoverLetter = CoverLetter.builder()
                .coverLetterCategory(originalCoverLetterCategory)
                .originalCoverLetterId(originalCoverLetterId)
                .content(content)
                .state(State.ACTIVE)
                .type(CoverLetterType.COMPLETING)
                .build();
        userInfo.addCoverLetter(requestedCompletingCoverLetter);
        CoverLetter savedTemporaryCoverLetter = coverLetterRepository.save(requestedCompletingCoverLetter);
        return savedTemporaryCoverLetter.getId();
    }

    public Long deleteCoverLetterById(Long coverLetterId) throws BaseException {
        Long userInfoId = 1L;
        CoverLetter selectedCoverLetter = coverLetterProvider.getCoverLetterById(coverLetterId);
        validateUser(userInfoId, selectedCoverLetter);
        selectedCoverLetter.setState(State.INACTIVE);
        coverLetterRepository.save(selectedCoverLetter);
        return selectedCoverLetter.getId();
    }

    private void validateUser(Long userInfoId, CoverLetter selectedCoverLetter) throws BaseException {
        if (!selectedCoverLetter.getUserInfo().getId().equals(userInfoId)) {
            throw new BaseException(BaseResponseStatus.DO_NOT_HAVE_PERMISSION);
        }
    }
}
