package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.enums.AuthenticationCheck;
import com.app.edit.provider.UserProvider;
import com.app.edit.request.user.PostUserReq;
import com.app.edit.response.user.GetUserRes;
import com.app.edit.response.user.PostUserRes;
import com.app.edit.response.user.DuplicationCheck;
import com.app.edit.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.edit.config.BaseResponseStatus.*;
import static com.app.edit.utils.ValidationRegex.isRegexEmail;
import static com.app.edit.utils.ValidationRegex.isRegexPhoneNumber;

@Slf4j
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserProvider userProvider;
    private final UserService userService;

    @Autowired
    public UserController(UserProvider userProvider,
                          UserService userService) {
        this.userProvider = userProvider;
        this.userService = userService;
    }

    /**
     * 회원 전체 조회 API
     * [GET] /users
     * @return BaseResponse<List<GetUsersRes>>
     */
    @GetMapping("/admin/users")
    @ApiOperation(value = "회원 전체 조회", notes = "회원 전체 조회")
    public BaseResponse<List<GetUserRes>> getUsers() {
        try {
            List<GetUserRes> getUsersResList = userProvider.retrieveUserList();
            return new BaseResponse<>(SUCCESS, getUsersResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 추가
     * [POST] /users
     * @return BaseResponse<List<GetUsersRes>>
     */
    @PostMapping(value = "/users")
    @ApiOperation(value = "회원 추가", notes = "회원 추가")
    public BaseResponse<PostUserRes> createUsers(
            @RequestBody PostUserReq parameters) {

        // 1. Body Parameter Validation
        if (parameters.getEmail() == null || parameters.getEmail().length() == 0) {
            return new BaseResponse<>(EMPTY_EMAIL);
        }
        if (!isRegexEmail(parameters.getEmail())){
            return new BaseResponse<>(INVALID_EMAIL);
        }
        if (parameters.getNickname() == null || parameters.getNickname().length() == 0) {
            return new BaseResponse<>(EMPTY_NICKNAME);
        }

        // 1. Body Parameter Validation
        if (parameters.getPassword() == null || parameters.getPassword().length() == 0) {
            return new BaseResponse<>(EMPTY_PASSWORD);
        }

        if (parameters.getAuthenticationPassword() == null || parameters.getAuthenticationPassword().length() == 0) {
            return new BaseResponse<>(EMPTY_CONFIRM_PASSWORD);
        }

        if (!parameters.getPassword().equals(parameters.getAuthenticationPassword())) {
            return new BaseResponse<>(DO_NOT_MATCH_PASSWORD);
        }

        if(parameters.getPhoneNumber() != null) {
            if(!isRegexPhoneNumber(parameters.getPhoneNumber())) {
                return new BaseResponse<>(INVALID_PHONENUMBER);
            }
        }

        try {
            PostUserRes postUserRes = userService.createUserInfo(parameters);
            return new BaseResponse<>(SUCCESS, postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 중복 이메일 및 닉네임 검증
     * [GET] /users/duplication
     */
    @GetMapping(value = "/users/duplication")
    @ApiOperation(value = "중복 이메일 및 닉네임 검증", notes = "중복 이메일 및 닉네임 검증" +
            "\n YES - 중복된 닉네임, 이메일 존재, NO - 중복된 닉네임,이메일 없음")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "이메일 검사일 경우 nickName 비워서 전송"),
            @ApiImplicitParam(name = "nickName", value = "닉네임 검사일 경우 email 비워서 전송")
    })
    public BaseResponse<DuplicationCheck> duplicationEmail(
            @RequestParam(value = "email",required = false) String email,
            @RequestParam(value = "nickName",required = false) String nickName) {

        try {
            DuplicationCheck duplicationCheck = userProvider.checkDuplication(email,nickName);
            return new BaseResponse<>(SUCCESS, duplicationCheck);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 이메일 인증
     * [GET] /api/users/authentication-email
     */
    @GetMapping(value = "/api/users/authentication-email")
    @ApiOperation(value = "이메일 인증", notes = "이메일 인증")
    public BaseResponse<Void> AuthenticationEmail(
            @RequestParam(value = "email") String email) {

        try {
            userProvider.authenticationEmail(email);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 이메일 인증 코드 검증
     * [GET] /api/users/authentication-code
     */
    @GetMapping(value = "/api/users/authentication-code")
    @ApiOperation(value = "이메일 인증 코드 검증", notes = "이메일 인증 코드 검증")
    public BaseResponse<AuthenticationCheck> AuthenticationCode(
            @RequestParam(value = "authenticationCode") String authenticationCode) {

        try {
            AuthenticationCheck authenticationCheck = userProvider.authenticationCode(authenticationCode);
            return new BaseResponse<>(SUCCESS, authenticationCheck);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
