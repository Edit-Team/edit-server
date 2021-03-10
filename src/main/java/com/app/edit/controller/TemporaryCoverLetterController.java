package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.request.temporarycoverletter.PostWritingTemporaryCoverLetterReq;
import com.app.edit.service.TemporaryCoverLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.app.edit.config.BaseResponseStatus.COVER_LETTER_CONTENT_LENGTH_CAN_NOT_BE_GREATER_THAN_LENGTH_LIMIT;
import static com.app.edit.config.Constant.COVER_LETTER_CONTENT_LIMIT_LENGTH;

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
    public BaseResponse<Long> postWritingTemporaryCoverLetter(@RequestBody PostWritingTemporaryCoverLetterReq request) throws BaseException {
        if (request.getCoverLetterContent().length() > COVER_LETTER_CONTENT_LIMIT_LENGTH) {
            throw new BaseException(COVER_LETTER_CONTENT_LENGTH_CAN_NOT_BE_GREATER_THAN_LENGTH_LIMIT);
        }
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                temporaryCoverLetterService.createWritingTemporaryCoverLetter(request));
    }
}
