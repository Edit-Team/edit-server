package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.provider.CoverLetterProvider;
import com.app.edit.request.coverletter.PostCoverLetterReq;
import com.app.edit.response.coverletter.GetCoverLettersRes;
import com.app.edit.response.coverletter.GetMainCoverLettersRes;
import com.app.edit.service.CoverLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.edit.config.BaseResponseStatus.COVER_LETTER_CONTENT_LENGTH_CAN_NOT_BE_GREATER_THAN_LENGTH_LIMIT;
import static com.app.edit.config.Constant.COVER_LETTER_CONTENT_LIMIT_LENGTH;
import static com.app.edit.config.Constant.DEFAULT_PAGE_SIZE;


@RequestMapping("/api")
@RestController
public class CoverLetterController {

    private final CoverLetterProvider coverLetterProvider;
    private final CoverLetterService coverLetterService;

    @Autowired
    public CoverLetterController(CoverLetterProvider coverLetterProvider, CoverLetterService coverLetterService) {
        this.coverLetterProvider = coverLetterProvider;
        this.coverLetterService = coverLetterService;
    }

    /*
     * 메인 화면 조회 API
     **/
    @GetMapping("/main")
    public BaseResponse<GetMainCoverLettersRes> getMainCoverLetters() {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, coverLetterProvider.retrieveMainCoverLetters());
    }

    /*
     * 오늘의 문장 조회 API
     * 먼저 등록된 순서대로 정렬
     **/
    @GetMapping("/today-cover-letters")
    public BaseResponse<List<GetCoverLettersRes>> getTodayCoverLetters(@RequestParam Integer page) {
        PageRequest pageRequest = com.app.edit.config.PageRequest
                .of(page, DEFAULT_PAGE_SIZE, Sort.by("createdAt"));
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, coverLetterProvider.retrieveTodayCoverLetters(pageRequest));
    }

    /*
     * 코멘트를 기다리고 있어요 조회 API
     * 코멘트가 없는 자소서 -> 먼저 등록된 순서대로 정렬
     **/
    @GetMapping("/waiting-for-comment-cover-letters")
    public BaseResponse<List<GetCoverLettersRes>> getWaitingForCommentCoverLetters(@RequestParam Integer page) {
        PageRequest pageRequest = com.app.edit.config.PageRequest
                .of(page, DEFAULT_PAGE_SIZE, Sort.by("createdAt"));
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                coverLetterProvider.retrieveWaitingForCommentCoverLetters(pageRequest));
    }

    /*
     * 채택이 완료되었어요 조회 API
     * 조회 시점으로부터 가장 가까운 시점에 채택된 순서대로 정렬
     **/
    @GetMapping("/adopted-cover-letters")
    public BaseResponse<List<GetCoverLettersRes>> getAdoptedCoverLetters(@RequestParam Integer page) {
        PageRequest pageRequest = com.app.edit.config.PageRequest
                .of(page, DEFAULT_PAGE_SIZE);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                coverLetterProvider.retrieveAdoptedCoverLetters(pageRequest));
    }

    /*
     * 많은 분들이 공감하고 있어요 조회 API
     * 조회시점으로부터 3일 전에 등록된 자소서까지만 조회
     * 공감 수가 많은 순서대로 정렬
     **/
    @GetMapping("/many-sympathies-cover-letters")
    public BaseResponse<List<GetCoverLettersRes>> getManySympathiesCoverLetters(@RequestParam Integer page) {
        PageRequest pageRequest = com.app.edit.config.PageRequest.of(page, DEFAULT_PAGE_SIZE);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                coverLetterProvider.retrieveManySympathiesCoverLetters(pageRequest));
    }

    /*
     * 작성중인 자소서 등록 API
     **/
    @PostMapping("/writing-cover-letters")
    public BaseResponse<Long> postWritingCoverLetter(@RequestBody PostCoverLetterReq request) throws BaseException {
        if (request.getCoverLetterContent().length() > COVER_LETTER_CONTENT_LIMIT_LENGTH) {
            throw new BaseException(COVER_LETTER_CONTENT_LENGTH_CAN_NOT_BE_GREATER_THAN_LENGTH_LIMIT);
        }
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, coverLetterService.createWritingCoverLetter(request));
    }
}
