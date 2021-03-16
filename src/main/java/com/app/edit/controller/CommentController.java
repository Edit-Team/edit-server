package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.provider.CommentProvider;
import com.app.edit.response.comment.GetCommentsRes;
import com.app.edit.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import static com.app.edit.config.Constant.DEFAULT_PAGE_SIZE;

@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentProvider commentProvider;
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentProvider commentProvider, CommentService commentService) {
        this.commentProvider = commentProvider;
        this.commentService = commentService;
    }

    /*
     * 자소서에 달린 코멘트 조회 API
     **/
    @ApiOperation(value = "자소서에 달린 코멘트 조회 API")
    @GetMapping("/cover-letters/{cover-letter-id}/comments")
    public BaseResponse<GetCommentsRes> getComments(@PathVariable("cover-letter-id") Long coverLetterId,
                                                          @RequestParam Integer page) throws BaseException {
        PageRequest pageRequest = com.app.edit.config.PageRequest.of(page, DEFAULT_PAGE_SIZE);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                commentProvider.retrieveCommentsByCoverLetterId(pageRequest, coverLetterId));
    }

    /**
     * 코멘트 채택하기 API
     */
    @ApiOperation(value = "코멘트 채택하기 API")
    @PatchMapping("/comments/{comment-id}/adopt-comments")
    public BaseResponse<Long> patchCommentAdopted(@PathVariable("comment-id") Long commentId) throws BaseException {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, commentService.updateCommentAdoptedById(commentId));
    }
}
