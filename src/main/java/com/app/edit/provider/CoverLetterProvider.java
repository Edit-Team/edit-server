package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.config.PageRequest;
import com.app.edit.domain.comment.Comment;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.coverletter.CoverLetterRepository;
import com.app.edit.enums.CoverLetterType;
import com.app.edit.enums.IsAdopted;
import com.app.edit.enums.State;
import com.app.edit.response.coverletter.GetCoverLetterToCompleteRes;
import com.app.edit.response.coverletter.GetCoverLettersRes;
import com.app.edit.response.coverletter.GetMainCoverLettersRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.app.edit.config.Constant.*;
import static java.util.Comparator.comparing;
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
     * 메인 페이지 자소서 조회
     **/
    public GetMainCoverLettersRes retrieveMainCoverLetters() {
        Pageable pageableForToday = PageRequest.of(ONE, MAIN_TODAY_COVER_LETTERS_COUNT);
        Pageable pageableForAnother = PageRequest.of(ONE, MAIN_ANOTHER_COVER_LETTERS_COUNT);
        return new GetMainCoverLettersRes(retrieveTodayCoverLetters(pageableForToday),
                retrieveWaitingForCommentCoverLetters(pageableForAnother),
                retrieveAdoptedCoverLetters(pageableForAnother),
                retrieveManySympathiesCoverLetters(pageableForAnother));
    }

    /*
     * 오늘의 문장 조회
     **/
    public List<GetCoverLettersRes> retrieveTodayCoverLetters(Pageable pageable) {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        LocalDateTime startOfTomorrow = startOfToday.plusDays(ONE);
        Page<CoverLetter> coverLettersOnToday = coverLetterRepository
                .findCoverLettersOnToday(pageable, startOfToday, startOfTomorrow, State.ACTIVE);
        return getCoverLettersResponses(coverLettersOnToday);
    }

    /*
     * 코멘트를 기다리고 있어요 조회
     **/
    public List<GetCoverLettersRes> retrieveWaitingForCommentCoverLetters(Pageable pageable) {
        Page<CoverLetter> coverLettersHasNotComment = coverLetterRepository.findCoverLettersHasNotComment(pageable, State.ACTIVE);
        return getCoverLettersResponses(coverLettersHasNotComment);
    }

    /*
     * 채택이 완료되었어요 조회
     **/
    public List<GetCoverLettersRes> retrieveAdoptedCoverLetters(Pageable pageable) {
        Page<CoverLetter> coverLettersHasAdoptedComment = coverLetterRepository.findCoverLettersHasAdoptedComment(pageable, IsAdopted.YES, State.ACTIVE);
        return getCoverLettersResponses(coverLettersHasAdoptedComment);
    }

    /*
     * 많은 분들이 공감하고 있어요 조회
     **/
    public List<GetCoverLettersRes> retrieveManySympathiesCoverLetters(Pageable pageable) {
        LocalDateTime beforeThreeDays = LocalDateTime.now().minusDays(CAN_STAY_DAY);
        Page<CoverLetter> coverLettersHasManySympathies = coverLetterRepository.findCoverLettersHasManySympathies(pageable, beforeThreeDays, State.ACTIVE);
        return getCoverLettersResponses(coverLettersHasManySympathies).stream()
                .sorted(comparing(GetCoverLettersRes::getSympathiesCount).reversed())
                .collect(toList());
    }

    private List<GetCoverLettersRes> getCoverLettersResponses(Page<CoverLetter> coverLetterPage) {
        return coverLetterPage.stream()
                .map(coverLetter -> {
                    GetCoverLettersRes getCoverLettersRes = coverLetter.toGetCoverLetterRes();
                    Long sympathiesCount = sympathyProvider.getSympathiesCount(coverLetter);
                    CoverLetter.setSympathiesCountInCoverLettersRes(getCoverLettersRes, sympathiesCount);
                    return getCoverLettersRes;
                })
                .collect(toList());
    }

    public CoverLetter getCoverLetterById(Long coverLetterId) throws BaseException {
        Optional<CoverLetter> coverLetter = coverLetterRepository.findById(coverLetterId);
        if (coverLetter.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND_COVER_LETTER);
        }
        if (coverLetter.get().getState().equals(State.INACTIVE)) {
            throw new BaseException(BaseResponseStatus.ALREADY_DELETED_COVER_LETTER);
        }
        return coverLetter.get();
    }

    /**
     * 내가 등록한 자소서 목록 조회
     * @param pageable
     * @return
     */
    public List<GetCoverLettersRes> retrieveMyWritingCoverLetters(Pageable pageable) {
        Long userInfoId = 1L;
        Page<CoverLetter> myCoverLetters = coverLetterRepository
                .findMyCoverLetters(pageable, userInfoId, State.ACTIVE, CoverLetterType.WRITING);
        return getCoverLettersResponses(myCoverLetters);
    }

    /**
     * 내가 완성한 자소서 목록 조회
     * @param pageable
     * @return
     */
    public List<GetCoverLettersRes> retrieveMyCompletingCoverLetters(Pageable pageable) {
        Long userInfoId = 1L;
        Page<CoverLetter> completingCoverLetters = coverLetterRepository
                .findMyCoverLetters(pageable, userInfoId, State.ACTIVE, CoverLetterType.COMPLETING);
        return getCoverLettersResponses(completingCoverLetters);
    }

    /**
     * 유저가 오늘 작성한 자소서 개수 조회
     * @return
     */
    public Long retrieveTodayWritingCoverLetterCount() {
        Long userInfoId = 1L;
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        LocalDateTime startOfTomorrow = startOfToday.plusDays(ONE);
        return coverLetterRepository
                .getTodayWritingCoverLetterCount(userInfoId, startOfToday, startOfTomorrow, State.ACTIVE);
    }

    public GetCoverLetterToCompleteRes retrieveCoverLetterToComplete(Long coverLetterId) throws BaseException {
        CoverLetter originalCoverLetter = getCoverLetterById(coverLetterId);
        Long originalCoverLetterCategoryId = originalCoverLetter.getCoverLetterCategory().getId();
        String originalCoverLetterContent = originalCoverLetter.getContent();
        Comment optionalAdoptedComment = getAdoptedComment(originalCoverLetter);
        String adoptedCommentContent = optionalAdoptedComment.getContent();
        return new GetCoverLetterToCompleteRes(coverLetterId, originalCoverLetterCategoryId,
                originalCoverLetterContent, adoptedCommentContent);
    }

    private Comment getAdoptedComment(CoverLetter originalCoverLetter) throws BaseException {
        Optional<Comment> adoptedComment = originalCoverLetter.getComments().stream()
                .filter(comment -> comment.getIsAdopted().equals(IsAdopted.YES))
                .findFirst();
        if (adoptedComment.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NOT_FOUND_ADOPTED_COMMENT);
        }
        return adoptedComment.get();
    }
}
