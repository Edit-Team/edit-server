package com.app.edit.provider;

import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.coverletter.CoverLetterRepository;
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


    public List<GetCoverLettersRes> retrieveTodayCoverLetters(Pageable pageable) {
        Page<CoverLetter> coverLetters = getCoverLettersWithPage(pageable);
        return getCoverLetterInfos(coverLetters);
    }

    /*
     * 자소서 목록 조회(기본)
     * Pageable -> 정렬 방식 지정
     **/
    private Page<CoverLetter> getCoverLettersWithPage(Pageable pageable) {
        return coverLetterRepository.findAll(pageable);
    }

    private List<GetCoverLettersRes> getCoverLetterInfos(Page<CoverLetter> coverLetters) {
        return coverLetters.getContent().stream()
                .map(CoverLetter::toGetCoverLetterInfoRes)
                .collect(toList());
    }
}
