package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.provider.UserProvider;
import com.app.edit.response.user.PostUserRes;
import com.app.edit.service.UserService;
import com.app.edit.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.app.edit.config.BaseResponseStatus.SUCCESS;

@Slf4j
@RequestMapping("/api")
@RestController
public class LoginController {

    private final UserProvider userProvider;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public LoginController(UserProvider userProvider,
                           UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 로그인
     * [GET] /api/login
     */
    @GetMapping(value = "/login")
    @ApiOperation(value = "로그인", notes = "로그인")
    public BaseResponse<PostUserRes> login(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {

        try {
            PostUserRes postUserRes = userProvider.login(email,password);
            return new BaseResponse<>(SUCCESS, postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //TODO 로그아웃 구현하기
//    /**
//     * 로그아웃
//     * [GET] /api/login
//     */
//    @GetMapping(value = "/logout")
//    @ApiOperation(value = "로그아웃(미완성)", notes = "로그아웃(미완성)")
//    public BaseResponse<PostUserRes> logout(
//            @RequestHeader(value = "X-ACCESS-TOKEN") String jwt){
//
//        try {
//            PostUserRes postUserRes = userProvider.logout();
//            return new BaseResponse<>(SUCCESS, postUserRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }

}
