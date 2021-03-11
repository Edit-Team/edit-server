package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.provider.UserProvider;
import com.app.edit.response.GetUserRes;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.app.edit.config.BaseResponseStatus.SUCCESS;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserProvider userProvider;

    @Autowired
    public UserController(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    /**
     * 회원 전체 조회 API
     * [GET] /users
     * @return BaseResponse<List<GetUsersRes>>
     */
    @RequestMapping(value = "/admin/users",method = RequestMethod.GET)
    @ApiOperation(value = "회원 전체 조회", notes = "회원 전체 조회")
    public BaseResponse<List<GetUserRes>> getUsers() {
        try {
            List<GetUserRes> getUsersResList = userProvider.retrieveUserList();
            return new BaseResponse<>(SUCCESS, getUsersResList);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

//    /**
//     * 회원 추가
//     * [POST] /users
//     * @return BaseResponse<List<GetUsersRes>>
//     */
//    @RequestMapping(value = "/users",method = RequestMethod.POST)
//    @ApiOperation(value = "회원 추가", notes = "회원 추가")
//    public BaseResponse<PostUserRes> createUsers() {
//        try {
//            List<GetUserRes> getUsersResList = userProvider.retrieveUserList();
//            return new BaseResponse<>(SUCCESS, getUsersResList);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }

}
