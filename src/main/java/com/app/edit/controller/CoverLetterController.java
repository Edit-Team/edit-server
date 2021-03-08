package com.app.edit.controller;

import com.app.edit.config.BaseResponse;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.provider.CoverLetterProvider;
import com.app.edit.response.coverletter.GetCoverLettersRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.app.edit.config.Constant.DEFAULT_PAGE_SIZE;
import static com.app.edit.config.Constant.ONE;


@RequestMapping("/api")
@RestController
public class CoverLetterController {

    private final CoverLetterProvider coverLetterProvider;

    @Autowired
    public CoverLetterController(CoverLetterProvider coverLetterProvider) {
        this.coverLetterProvider = coverLetterProvider;
    }

    /*
     * 오늘의 문장 조회 API
     * 먼저 등록된 순서대로 정렬
     **/
    @GetMapping("/today-cover-letters")
    public BaseResponse<List<GetCoverLettersRes>> getTodayCoverLetters(@RequestParam Integer pageNumber) {
        PageRequest pageRequest = com.app.edit.config.PageRequest
                .of(pageNumber - ONE, DEFAULT_PAGE_SIZE, Sort.by("createdAt"));
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, coverLetterProvider.retrieveTodayCoverLetters(pageRequest));
    }

    /*
     * 코멘트를 기다리고 있어요 조회 API
     * 코멘트가 없는 자소서 -> 먼저 등록된 순서대로 정렬
     **/
    @GetMapping("/waiting-for-comment-cover-letters")
    public BaseResponse<List<GetCoverLettersRes>> getWaitingForCommentCoverLetters(@RequestParam Integer pageNumber) {
        PageRequest pageRequest = com.app.edit.config.PageRequest
                .of(pageNumber - ONE, DEFAULT_PAGE_SIZE, Sort.by("createdAt"));
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                coverLetterProvider.retrieveWaitingForCommentCoverLetters(pageRequest));
    }
}
