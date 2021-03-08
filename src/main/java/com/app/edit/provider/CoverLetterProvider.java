package com.app.edit.provider;

import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.coverletter.CoverLetterRepository;
import com.app.edit.response.coverletter.GetCoverLettersRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.app.edit.config.Constant.ONE;
import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@Service
public class CoverLetterProvider {
    private final static LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
    private final static LocalDateTime startOfTomorrow = startOfToday.plusDays(ONE);

    private final CoverLetterRepository coverLetterRepository;

    @Autowired
    public CoverLetterProvider(CoverLetterRepository coverLetterRepository) {
        this.coverLetterRepository = coverLetterRepository;
    }

    /*
     * 오늘의 문장 조회
     **/
    public List<GetCoverLettersRes> retrieveTodayCoverLetters(Pageable pageable) {
        return coverLetterRepository.findCoverLettersOnToday(pageable, startOfToday, startOfTomorrow).stream()
                .map(CoverLetter::toGetCoverLetterInfoRes)
                .collect(toList());
    }

    /*
     * 코멘트를 기다리고 있어요 조회
     **/
    public List<GetCoverLettersRes> retrieveWaitingForCommentCoverLetters(Pageable pageable) {
        return coverLetterRepository.findCoverLettersHasNotComment(pageable).stream()
                .map(CoverLetter::toGetCoverLetterInfoRes)
                .collect(toList());
    }
}
