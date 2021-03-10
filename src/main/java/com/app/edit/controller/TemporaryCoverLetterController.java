package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.request.temporarycoverletter.PostTemporaryCoverLetterReq;
import com.app.edit.service.TemporaryCoverLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class TemporaryCoverLetterController {

    private final TemporaryCoverLetterService temporaryCoverLetterService;

    @Autowired
    public TemporaryCoverLetterController(TemporaryCoverLetterService temporaryCoverLetterService) {
        this.temporaryCoverLetterService = temporaryCoverLetterService;
    }

    /*
     * 작성중인 자소서 등록 API
     **/
    @PostMapping("/writing-temporary-cover-letters")
    public BaseResponse<Long> postTemporaryCoverLetters(@RequestBody PostTemporaryCoverLetterReq request) throws BaseException {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                temporaryCoverLetterService.createWritingTemporaryCoverLetter(request));
    }
}
