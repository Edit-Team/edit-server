package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.provider.CoverLetterProvider;
import com.app.edit.provider.SympathyProvider;
import com.app.edit.request.coverletter.PostCompletingCoverLetterReq;
import com.app.edit.request.coverletter.PostWritingCoverLetterReq;
import com.app.edit.response.coverletter.GetCoverLettersRes;
import com.app.edit.response.coverletter.GetMainCoverLettersRes;
import com.app.edit.response.sympathize.GetSympathizeCoverLettersRes;
import com.app.edit.service.CoverLetterService;
import com.app.edit.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.app.edit.config.BaseResponseStatus.EMPTY_USERID;
import static com.app.edit.config.Constant.DEFAULT_PAGE_SIZE;


@RequestMapping("/api")
@RestController
public class CoverLetterController {

    private final CoverLetterProvider coverLetterProvider;
    private final CoverLetterService coverLetterService;
    private final SympathyProvider sympathyProvider;
    private final JwtService jwtService;

    @Autowired
    public CoverLetterController(CoverLetterProvider coverLetterProvider,
                                 CoverLetterService coverLetterService,
                                 SympathyProvider sympathyProvider,
                                 JwtService jwtService) {
        this.coverLetterProvider = coverLetterProvider;
        this.coverLetterService = coverLetterService;
        this.sympathyProvider = sympathyProvider;
        this.jwtService = jwtService;
    }

    /*
     * 메인 화면 조회 API
     **/
    @ApiOperation(value = "메인 화면 조회 API")
    @GetMapping("/main")
    public BaseResponse<GetMainCoverLettersRes> getMainCoverLetters() throws BaseException {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, coverLetterProvider.retrieveMainCoverLetters());
    }

    /*
     * 오늘의 문장 조회 API
     * 먼저 등록된 순서대로 정렬
     **/
    @ApiOperation(value = "오늘의 문장 조회 API")
    @GetMapping("/today-cover-letters")
    public BaseResponse<List<GetCoverLettersRes>> getTodayCoverLetters(@RequestParam Integer page) throws BaseException {
        PageRequest pageRequest = com.app.edit.config.PageRequest
                .of(page, DEFAULT_PAGE_SIZE, Sort.by("createdAt"));
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, coverLetterProvider.retrieveTodayCoverLetters(pageRequest));
    }

    /*
     * 코멘트를 기다리고 있어요 조회 API
     * 코멘트가 없는 자소서 -> 먼저 등록된 순서대로 정렬
     **/
    @ApiOperation(value = "코멘트를 기다리고 있어요 조회 API")
    @GetMapping("/waiting-for-comment-cover-letters")
    public BaseResponse<List<GetCoverLettersRes>> getWaitingForCommentCoverLetters(@RequestParam Integer page) throws BaseException {
        PageRequest pageRequest = com.app.edit.config.PageRequest
                .of(page, DEFAULT_PAGE_SIZE, Sort.by("createdAt"));
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                coverLetterProvider.retrieveWaitingForCommentCoverLetters(pageRequest));
    }

    /*
     * 채택이 완료되었어요 조회 API
     * 조회 시점으로부터 가장 가까운 시점에 채택된 순서대로 정렬
     **/
    @ApiOperation(value = "채택이 완료되었어요 조회 API")
    @GetMapping("/adopted-cover-letters")
    public BaseResponse<List<GetCoverLettersRes>> getAdoptedCoverLetters(@RequestParam Integer page) throws BaseException {
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
    @ApiOperation(value = "많은 분들이 공감하고 있어요 조회 API")
    @GetMapping("/many-sympathies-cover-letters")
    public BaseResponse<List<GetCoverLettersRes>> getManySympathiesCoverLetters(@RequestParam Integer page) throws BaseException {
        PageRequest pageRequest = com.app.edit.config.PageRequest.of(page, DEFAULT_PAGE_SIZE);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                coverLetterProvider.retrieveManySympathiesCoverLetters(pageRequest));
    }

    /*
     * 작성중인 자소서 등록 API
     **/
    @ApiOperation(value = "작성중인 자소서 등록 API")
    @PostMapping("/writing-cover-letters")
    public BaseResponse<Long> postWritingCoverLetter(@RequestBody @Valid PostWritingCoverLetterReq request) throws BaseException {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, coverLetterService.createWritingCoverLetter(request));
    }

    /*
     * 완성중인 자소서 등록 API
     **/
    @ApiOperation(value = "완성중인 자소서 등록 API")
    @PostMapping("/completing-cover-letters")
    public BaseResponse<Long> postCompletingCoverLetter(@RequestBody @Valid PostCompletingCoverLetterReq request) throws BaseException {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, coverLetterService.createCompletingCoverLetter(request));
    }

    /*
     * 내가 등록한 자소서 조회 API
     * 먼저 등록한 순서대로 정렬
     **/
    @ApiOperation(value = "내가 등록한 자소서 조회 API")
    @GetMapping("/my-writing-cover-letters")
    public BaseResponse<List<GetCoverLettersRes>> getMyCoverLetters(@RequestParam Integer page) throws BaseException {
        PageRequest pageRequest = com.app.edit.config.PageRequest.of(page, DEFAULT_PAGE_SIZE);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, coverLetterProvider.retrieveMyWritingCoverLetters(pageRequest));
    }

    /**
     * 등록/완성한 자소서 삭제하기 API
     * @param coverLetterId
     * @return
     * @throws BaseException
     */
    @ApiOperation(value = "등록/완성한 자소서 삭제하기 API")
    @DeleteMapping("/cover-letters/{cover-letters-id}")
    public BaseResponse<Long> deleteCoverLetter(@PathVariable("cover-letters-id") Long coverLetterId) throws BaseException {
        return new BaseResponse<>(BaseResponseStatus.SUCCESS, coverLetterService.deleteCoverLetterById(coverLetterId));
    }

    /**
     * 완성한 자소서 목록 조회 API
     * @param page
     * @return
     */
    @ApiOperation(value = "완성한 자소서 목록 조회 API")
    @GetMapping("/my-completing-cover-letters")
    public BaseResponse<List<GetCoverLettersRes>> getCompletingCoverLetters(@RequestParam Integer page) throws BaseException {
        PageRequest pageRequest = com.app.edit.config.PageRequest.of(page, DEFAULT_PAGE_SIZE);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS,
                coverLetterProvider.retrieveMyCompletingCoverLetters(pageRequest));
    }

    /**
     * 내가 공감한 자소서 조회 API
     * @param
     * @return
     */
    @ApiOperation(value = "내가 공감한 자소서 조회 API")
    @GetMapping("/sympathize-coverletters")
    public BaseResponse<List<GetSympathizeCoverLettersRes>> getSympathizeCoverLetters(
            @RequestHeader(value = "X-ACCESS-TOKEN") String jwt,
            @RequestParam("page") Integer pageNum) {

        try{

            Long userId = jwtService.getUserInfo().getUserId();

            if (userId == null || userId <= 0) {
                return new BaseResponse<>(EMPTY_USERID);
            }

            return new BaseResponse<>(BaseResponseStatus.SUCCESS, coverLetterProvider.retrieveMySympathizeCoverLetters(userId, pageNum));
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }
}
