package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.domain.temporarycoverletter.TemporaryCoverLetter;
import com.app.edit.domain.temporarycoverletter.TemporaryCoverLetterRepository;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.CoverLetterType;
import com.app.edit.enums.State;
import com.app.edit.response.temporarycoverletter.GetTemporaryCoverLettersRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@Service
public class TemporaryCoverLetterProvider {

    private final TemporaryCoverLetterRepository temporaryCoverLetterRepository;
    private final UserInfoProvider userInfoProvider;

    @Autowired
    public TemporaryCoverLetterProvider(TemporaryCoverLetterRepository temporaryCoverLetterRepository, UserInfoProvider userInfoProvider) {
        this.temporaryCoverLetterRepository = temporaryCoverLetterRepository;
        this.userInfoProvider = userInfoProvider;
    }

    /**
     * 임시 저장한 자소서 조회
     * @param pageable
     * @param coverLetterType
     * @return
     * @throws BaseException
     */
    public List<GetTemporaryCoverLettersRes> retrieveTemporaryCoverLetters(Pageable pageable, CoverLetterType coverLetterType) throws BaseException {
        Long userInfoId = 1L;
        UserInfo userInfo = userInfoProvider.getUserInfoById(userInfoId);
        Page<TemporaryCoverLetter> temporaryCoverLetters = temporaryCoverLetterRepository
                .findTemporaryCoverLetters(pageable, userInfoId, State.ACTIVE, coverLetterType);
        return getTemporaryCoverLettersResponses(userInfo, temporaryCoverLetters);
    }

    private List<GetTemporaryCoverLettersRes> getTemporaryCoverLettersResponses(UserInfo userInfo, Page<TemporaryCoverLetter> temporaryCoverLetters) {
        return temporaryCoverLetters.stream()
                .map(temporaryCoverLetter -> {
                    Long temporaryCoverLetterId = temporaryCoverLetter.getId();
                    String nickName = userInfo.getNickName();
                    String jobName = userInfo.getJob().getName();
                    String coverLetterCategoryName = temporaryCoverLetter.getCoverLetterCategory().getName();
                    String temporaryCoverLetterContent = temporaryCoverLetter.getContent();
                    return new GetTemporaryCoverLettersRes(temporaryCoverLetterId, nickName,
                            jobName, coverLetterCategoryName, temporaryCoverLetterContent);
                })
                .collect(toList());
    }
}
