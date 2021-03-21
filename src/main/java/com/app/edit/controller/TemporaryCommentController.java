package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.provider.TemporaryCommentProvider;
import com.app.edit.response.comment.GetMyCommentsRes;
import com.app.edit.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.app.edit.config.BaseResponseStatus.EMPTY_USERID;
import static com.app.edit.config.BaseResponseStatus.SUCCESS;

@RequestMapping("/api")
@RestController
public class TemporaryCommentController {

    private final JwtService jwtService;
    private final TemporaryCommentProvider temporaryCommentProvider;

    @Autowired
    public TemporaryCommentController(JwtService jwtService,
                                      TemporaryCommentProvider temporaryCommentProvider){

        this.jwtService = jwtService;
        this.temporaryCommentProvider = temporaryCommentProvider;
    }


    @ApiOperation(value = "내 임시저장한 코멘트 조회")
    @PostMapping("/temporary-comments")
    public BaseResponse<GetMyCommentsRes> getTemporaryComments() throws BaseException {

        try {
            Long userId = jwtService.getUserInfo().getUserId();

            if (userId == null || userId <= 0) {
                return new BaseResponse<>(EMPTY_USERID);
            }
            GetMyCommentsRes getMyCommentsRes = temporaryCommentProvider.getMyTemporaryComment(userId);

            return new BaseResponse<>(SUCCESS, getMyCommentsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
