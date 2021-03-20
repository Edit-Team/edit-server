package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.UserRole;
import com.app.edit.provider.CommentProvider;
import com.app.edit.request.comment.PostCommentReq;
import com.app.edit.response.comment.GetCommentsRes;
import com.app.edit.response.comment.GetMyCommentWithCoverLetterRes;
import com.app.edit.response.comment.GetMyCommentsRes;
import com.app.edit.response.comment.GetNotAdoptedCommentContentsRes;
import com.app.edit.response.user.GetUserInfo;
import com.app.edit.service.CommentService;
import com.app.edit.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.edit.config.BaseResponseStatus.*;
import static com.app.edit.config.Constant.DEFAULT_PAGE_SIZE;

@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentProvider commentProvider;
    private final CommentService commentService;
    private final JwtService jwtService;

    @Autowired
    public CommentController(CommentProvider commentProvider, CommentService commentService,
                             JwtService jwtService) {
        this.commentProvider = commentProvider;
        this.commentService = commentService;
        this.jwtService = jwtService;
    }


    @ApiOperation(value = "코멘트 등록하기")
    @PostMapping("/api/comments")
    public BaseResponse<Void> createComment(
            @RequestBody PostCommentReq parameters) throws BaseException{

            GetUserInfo userInfo = jwtService.getUserInfo();

            if(userInfo.getRole().equals(UserRole.MENTOR.name()))
                throw new BaseException(UNAUTHORIZED_AUTHORITY);

            Long userId = userInfo.getUserId();

            if(userId == null || userId < 0)
                throw new BaseException(EMPTY_USERID);

            try{
                commentService.createComment(userInfo.getUserId(),parameters);
                return new BaseResponse<>(SUCCESS);
            }catch (BaseException exception){
                return new BaseResponse<>(exception.getStatus());
            }
    }

    /**
     /*
     * 내가 작성한 코멘트 조회 API
     **/
    @ApiOperation(value = "내가 작성한 코멘트 조회 API")
    @GetMapping("/comments")
    public BaseResponse<List<GetMyCommentsRes>> getMyComments(@RequestParam Integer page) throws BaseException {

        Long userId = jwtService.getUserInfo().getUserId();

        if(userId == null || userId <= 0)
            throw new BaseException(EMPTY_USERID);

        PageRequest pageRequest = com.app.edit.config.PageRequest.of(page, DEFAULT_PAGE_SIZE);
        return new BaseResponse<>(SUCCESS,
                commentProvider.retrieveMyComments(pageRequest, userId));
    }



    /**
    /*
     * 자소서에 달린 코멘트 조회 API
     **/
    @ApiOperation(value = "자소서에 달린 코멘트 조회 API")
    @GetMapping("/cover-letters/{cover-letter-id}/comments")
    public BaseResponse<GetCommentsRes> getComments(@PathVariable("cover-letter-id") Long coverLetterId,
                                                    @RequestParam Integer page) throws BaseException {
        PageRequest pageRequest = com.app.edit.config.PageRequest.of(page, DEFAULT_PAGE_SIZE);
        return new BaseResponse<>(SUCCESS,
                commentProvider.retrieveCommentsByCoverLetterId(pageRequest, coverLetterId));
    }

    /**
     * 코멘트 채택하기 API
     */
    @ApiOperation(value = "코멘트 채택하기 API")
    @PatchMapping("/comments/{comment-id}/adopt-comments")
    public BaseResponse<Long> patchCommentAdopted(@PathVariable("comment-id") Long commentId) throws BaseException {
        return new BaseResponse<>(SUCCESS, commentService.updateCommentAdoptedById(commentId));
    }

    /**
     * 자소서에서 채택되지 않은 코멘트 내용 목록 조회 API
     * @param coverLetterId
     * @param page
     * @return
     * @throws BaseException
     */
    @ApiOperation(value = "자소서에서 채택되지 않은 코멘트 내용 목록 조회 API")
    @GetMapping("/cover-letters/{cover-letter-id}/not-adopted-comments")
    public BaseResponse<GetNotAdoptedCommentContentsRes> getNotAdoptedCommentContents(@PathVariable("cover-letter-id") Long coverLetterId,
                                                                                      @RequestParam Integer page) throws BaseException {
        PageRequest pageRequest = com.app.edit.config.PageRequest.of(page, DEFAULT_PAGE_SIZE,
                Sort.by(Sort.Order.desc("createdAt")));
        return new BaseResponse<>(SUCCESS,
                commentProvider.getNotAdoptedCommentContentsById(coverLetterId, pageRequest));
    }

    /**
     * 내가 작성한 코멘트의 문장 보기 API
     * @param commentId
     * @return
     * @throws BaseException
     */
    @ApiOperation(value = "내가 작성한 코멘트의 문장 보기 API")
    @GetMapping("/comments/{comment-id}/cover-letters")
    public BaseResponse<GetMyCommentWithCoverLetterRes> getMyCommentWithCoverLetter(
            @PathVariable("comment-id") Long commentId) throws BaseException {

        Long userId = jwtService.getUserInfo().getUserId();

        if(userId == null || userId <=0)
            throw new BaseException(EMPTY_USERID);

        return new BaseResponse<>(SUCCESS, commentProvider.getMyCommentWithCoverLetter(commentId,userId));
    }
}
