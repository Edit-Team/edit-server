package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.request.coverletter.PostCoverLetterDeclarationReq;
import com.app.edit.service.CoverLetterDeclarationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class CoverLetterDeclarationController {

    private CoverLetterDeclarationService coverLetterDeclarationService;

    @Autowired
    public CoverLetterDeclarationController(CoverLetterDeclarationService coverLetterDeclarationService) {
        this.coverLetterDeclarationService = coverLetterDeclarationService;
    }

    /*
     * 자소서 신고 API
     **/
    @PostMapping("/declare-cover-letters")
    public BaseResponse<Long> postCoverLetterDeclaration(@RequestBody PostCoverLetterDeclarationReq request) throws BaseException {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                coverLetterDeclarationService.createCoverLetterDeclaration(request));
    }
}