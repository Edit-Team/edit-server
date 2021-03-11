package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.provider.UserProvider;
import com.app.edit.request.user.PostUserReq;
import com.app.edit.response.user.GetUserRes;
import com.app.edit.response.user.PostUserRes;
import com.app.edit.service.UserService;
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

}
