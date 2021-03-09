package com.app.edit.provider;

import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.coverletter.CoverLetterRepository;
import com.app.edit.enums.IsAdopted;
import com.app.edit.enums.State;
import com.app.edit.response.coverletter.GetCoverLettersRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.app.edit.config.Constant.*;
import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@Service
public class CoverLetterProvider {

    private final CoverLetterRepository coverLetterRepository;
    private final SympathyProvider sympathyProvider;

    @Autowired
    public CoverLetterProvider(CoverLetterRepository coverLetterRepository, SympathyProvider sympathyProvider) {
        this.coverLetterRepository = coverLetterRepository;
        this.sympathyProvider = sympathyProvider;
    }

    /*
     * 오늘의 문장 조회
     **/
    public List<GetCoverLettersRes> retrieveTodayCoverLetters(Pageable pageable) {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        LocalDateTime startOfTomorrow = startOfToday.plusDays(ONE);
        return coverLetterRepository.findCoverLettersOnToday(pageable, startOfToday, startOfTomorrow).stream()
                .map(coverLetter -> convertToGetCoverLettersRes(coverLetter))
                .collect(toList());
    }

    /*
     * 코멘트를 기다리고 있어요 조회
     **/
    public List<GetCoverLettersRes> retrieveWaitingForCommentCoverLetters(Pageable pageable) {
        return coverLetterRepository.findCoverLettersHasNotComment(pageable).stream()
                .map(coverLetter -> convertToGetCoverLettersRes(coverLetter))
                .collect(toList());
    }

    /*
     * 채택이 완료되었어요 조회
     **/
    public List<GetCoverLettersRes> retrieveAdoptedCoverLetters(Pageable pageable) {
        return coverLetterRepository.findCoverLettersHasAdoptedComment(pageable, IsAdopted.YES).stream()
                .sorted(Comparator.comparing(CoverLetter::getAdoptedTime).reversed())
                .map(coverLetter -> convertToGetCoverLettersRes(coverLetter))
                .collect(toList());
    }

    /*
     * 많은 분들이 공감하고 있어요 조회
     **/
    public List<GetCoverLettersRes> retrieveManySympathiesCoverLetters(Pageable pageable) {
        LocalDateTime beforeThreeDays = LocalDateTime.now().minusDays(CAN_STAY_DAY);
        return coverLetterRepository.findCoverLettersHasManySympathies(pageable, beforeThreeDays, State.ACTIVE.name()).stream()
                .map(coverLetter -> convertToGetCoverLettersRes(coverLetter))
                .collect(toList());
    }

    /*
     * 자소서 -> 자소서 조회 응답 객체로 변환
     **/
    private GetCoverLettersRes convertToGetCoverLettersRes(CoverLetter coverLetter) {
        GetCoverLettersRes getCoverLettersRes = coverLetter.toGetCoverLetterRes();
        Long sympathiesCount = sympathyProvider.getSympathiesCount(coverLetter);
        getCoverLettersRes.setSympathiesCount(sympathiesCount);
        return getCoverLettersRes;
    }
}
