package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.request.temporarycoverletter.PostCompletingTemporaryCoverLetterReq;
import com.app.edit.request.temporarycoverletter.PostWritingTemporaryCoverLetterReq;
import com.app.edit.service.TemporaryCoverLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class TemporaryCoverLetterController {

    private final TemporaryCoverLetterService temporaryCoverLetterService;

    @Autowired
    public TemporaryCoverLetterController(TemporaryCoverLetterService temporaryCoverLetterService) {
        this.temporaryCoverLetterService = temporaryCoverLetterService;
    }

    /*
     * 작성중인 자소서 임시 저장 API
     **/
    @PostMapping("/writing-temporary-cover-letters")
    public BaseResponse<Long> postWritingTemporaryCoverLetter(@RequestBody @Valid PostWritingTemporaryCoverLetterReq request) throws BaseException {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                temporaryCoverLetterService.createWritingTemporaryCoverLetter(request));
    }

    /*
     * 완성중인 자소서 임시 저장 API
     **/
    @PostMapping("/completing-temporary-cover-letters")
    public BaseResponse<Long> postCompletingTemporaryCoverLetter(@RequestBody @Valid PostCompletingTemporaryCoverLetterReq request) throws BaseException {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                temporaryCoverLetterService.createCompletingTemporaryCoverLetter(request));
    }
}
