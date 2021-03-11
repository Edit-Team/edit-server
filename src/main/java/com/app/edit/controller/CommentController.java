package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.provider.CommentProvider;
import com.app.edit.response.comment.GetCommentsRes;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.edit.config.Constant.DEFAULT_PAGE_SIZE;

@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentProvider commentProvider;

    @Autowired
    public CommentController(CommentProvider commentProvider) {
        this.commentProvider = commentProvider;
    }

    /*
     * 자소서에 달린 코멘트 조회 API
     **/
    @ApiOperation(value = "자소서에 달린 코멘트 조회 API")
    @GetMapping("/cover-letters/{cover-letter-id}/comments")
    public BaseResponse<List<GetCommentsRes>> getComments(@PathVariable("cover-letter-id") Long coverLetterId,
                                                          @RequestParam Integer page) throws BaseException {
        PageRequest pageRequest = com.app.edit.config.PageRequest.of(page, DEFAULT_PAGE_SIZE);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                commentProvider.retrieveCommentsByCoverLetterId(pageRequest, coverLetterId));
    }
}
