package com.app.edit.provider;

import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.coverletter.CoverLetterRepository;
import com.app.edit.response.coverletter.CoverLetterInfo;
import com.app.edit.response.coverletter.GetCoverLettersRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@Service
public class CoverLetterProvider {

    private final CoverLetterRepository coverLetterRepository;

    @Autowired
    public CoverLetterProvider(CoverLetterRepository coverLetterRepository) {
        this.coverLetterRepository = coverLetterRepository;
    }

    /*
     * 자소서 목록 조회(기본)
     * todo: 유저기능 및 직군 종류 데이터 결정되면 jobName, isSympathy 로직 수정할 것.
     **/
    public GetCoverLettersRes retrieveCoverLetters(Pageable pageable) {
        Page<CoverLetter> coverLetters = getCoverLetters(pageable);
        List<CoverLetterInfo> coverLetterInfos = getCoverLetterInfos(coverLetters);
        return new GetCoverLettersRes(coverLetterInfos, coverLetters.getPageable());
    }

    private Page<CoverLetter> getCoverLetters(Pageable pageable) {
        Page<CoverLetter> coverLetters = coverLetterRepository.findAll(pageable);
        return coverLetters;
    }

    private List<CoverLetterInfo> getCoverLetterInfos(Page<CoverLetter> coverLetters) {
        List<CoverLetterInfo> coverLetterInfos = coverLetters.getContent().stream()
                .map(coverLetter -> {
                    Long coverLetterId = coverLetter.getId();
                    String nickName = coverLetter.getUserInfo().getNickName();
                    String jobName = coverLetter.getUserInfo().getJob().getName();
                    String coverLetterCategoryName = coverLetter.getCoverLetterCategory().getName();
                    String coverLetterContent = coverLetter.getContent();
                    boolean isSympathy = true;
                    return new CoverLetterInfo(coverLetterId, nickName, jobName,
                            coverLetterCategoryName, coverLetterContent, isSympathy);
                })
                .collect(toList());
        return coverLetterInfos;
    }

}
