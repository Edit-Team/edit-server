package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.AuthenticationCheck;
import com.app.edit.provider.UserProvider;
import com.app.edit.request.user.DeleteUserReq;
import com.app.edit.request.user.PostUserReq;
import com.app.edit.response.user.*;
import com.app.edit.service.UserService;
import com.app.edit.utils.JwtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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
    private final JwtService jwtService;

    @Autowired
    public UserController(UserProvider userProvider,
                          UserService userService,
                          JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
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
    @GetMapping(value = "/users/authentication-email")
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
    @GetMapping(value = "/users/authentication-code")
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

    /**
     * 이메일 찾기
     * [GET] /api/users/email
     */
    @GetMapping(value = "/users/email")
    @ApiOperation(value = "이메일 찾기", notes = "이메일 찾기")
    public BaseResponse<GetEmailRes> searchEmail(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "phoneNumber") String phoneNumber) {

        try {
            GetEmailRes getEmailRes = userProvider.searchEmail(name,phoneNumber);
            return new BaseResponse<>(SUCCESS , getEmailRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 비밀번호 찾기
     * [GET] /api/users/temporary-password
     */
    @PatchMapping(value = "/users/temporary-password")
    @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기")
    public BaseResponse<Void> searchPassword(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "phoneNumber") String phoneNumber) {

        try {
            userService.searchPassword(name, email, phoneNumber);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 비밀번호 인증
     * [GET] /api/users/authentication-password
     */
    @GetMapping(value = "/users/authentication-password")
    @ApiOperation(value = "비밀번호 인증", notes = "비밀번호 인증\n"+"YES = 인증됨, NO = 인증안됨")
    public BaseResponse<AuthenticationCheck> authenticationPassword(
            @RequestHeader(value = "X-ACCESS-TOKEN") String jwt,
            @RequestParam(value = "password") String password) {

        try {
            Long userId = jwtService.getUserInfo().getUserId();

            if (userId == null || userId <= 0) {
                return new BaseResponse<>(EMPTY_USERID);
            }

            AuthenticationCheck authenticationCheck = userProvider.authenticationPassword(userId,password);
            return new BaseResponse<>(SUCCESS, authenticationCheck);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 비밀번호 수정
     * [PATCH] /api/users/password
     */
    @PatchMapping(value = "/users/password")
    @ApiOperation(value = "비밀번호 수정", notes = "비밀번호 수정")
    public BaseResponse<Void> UpdatePassword(
            @RequestHeader(value = "X-ACCESS-TOKEN") String jwt,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "authenticationPassword") String authenticationPassword) {

        try {
            Long userId = jwtService.getUserInfo().getUserId();

            if (password == null || password.length() == 0) {
                return new BaseResponse<>(EMPTY_PASSWORD);
            }

            if (authenticationPassword == null || authenticationPassword.length() == 0) {
                return new BaseResponse<>(EMPTY_CONFIRM_PASSWORD);
            }

            if (!password.equals(authenticationPassword)) {
                return new BaseResponse<>(DO_NOT_MATCH_PASSWORD);
            }

            if (userId == null || userId <= 0) {
                return new BaseResponse<>(EMPTY_USERID);
            }
            userService.updatePassword(userId,password);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 내 프로필 조회
     * [GET] /api/users/profile
     */
    @GetMapping(value = "/users/profile")
    @ApiOperation(value = "내 프로필 조회", notes = "내 프로필 조회")
    public BaseResponse<GetProfileRes> getProfile(
            @RequestHeader(value = "X-ACCESS-TOKEN") String jwt){

        try {
            Long userId = jwtService.getUserInfo().getUserId();

            if (userId == null || userId <= 0) {
                return new BaseResponse<>(EMPTY_USERID);
            }
            GetProfileRes getProfileRes = userProvider.retrieveProfile(userId);
            return new BaseResponse<>(SUCCESS, getProfileRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 캐릭터 및 색상 변경
     * [PATCH] /api/users/profile
     */
    @PatchMapping(value = "/users/profile")
    @ApiOperation(value = "캐릭터 및 색상 변경", notes = "캐릭터 및 색상 변경")
    public BaseResponse<Void> UpdateProfile(
            @RequestHeader(value = "X-ACCESS-TOKEN") String jwt,
            @RequestParam(value = "colorName") String colorName,
            @RequestParam(value = "emotionName") String emotionName
            ){

        try {
            Long userId = jwtService.getUserInfo().getUserId();

            if (userId == null || userId <= 0) {
                return new BaseResponse<>(EMPTY_USERID);
            }
            userService.updateProfile(userId,colorName,emotionName);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 멘토 인증
     * [POST] /users/authentication
     * @return BaseResponse<Void>
     */
    @PostMapping(value = "/users/authentication")
    @ApiOperation(value = "멘토 인증", notes = "멘토 인증")
    public BaseResponse<Void> userAuthentication(
            @RequestHeader("X-ACCESS-TOKEN") String jwt,
            @RequestParam(value = "authenticationImage") MultipartFile multipartFile) throws IOException {
        try {

            Long userId = jwtService.getUserInfo().getUserId();

            if (userId == null || userId <= 0) {
                return new BaseResponse<>(EMPTY_USERID);
            }

            userService.AuthenticationMentor(userId,multipartFile);

            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 멘토 인증 상태 조회
     * [GET] /users/authentication
     * @return BaseResponse<Void>
     */
    @GetMapping(value = "/users/authentication")
    @ApiOperation(value = "멘토 인증 상태 조회", notes = "멘토 인증")
    public BaseResponse<GetAuthenticationRes> userAuthentication(
            @RequestHeader("X-ACCESS-TOKEN") String jwt){

        try {

            Long userId = jwtService.getUserInfo().getUserId();

            if (userId == null || userId <= 0) {
                return new BaseResponse<>(EMPTY_USERID);
            }

            GetAuthenticationRes getAuthenticationRes = userProvider.AuthenticationMentor(userId);

            return new BaseResponse<>(SUCCESS, getAuthenticationRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 탈퇴
     * [DELETE] /api/users
     */
    @DeleteMapping(value = "/users")
    @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴")
    public BaseResponse<Void> UpdateProfile(
            @RequestHeader(value = "X-ACCESS-TOKEN") String jwt,
            @Valid
            @RequestBody DeleteUserReq parameters){

        try {
            Long userId = jwtService.getUserInfo().getUserId();

            if (userId == null || userId <= 0) {
                return new BaseResponse<>(EMPTY_USERID);
            }
            userService.deleteUser(userId,parameters);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
