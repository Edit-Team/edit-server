package com.app.edit.controller;

import com.app.edit.config.BaseException;
import com.app.edit.config.BaseResponse;
import com.app.edit.config.BaseResponseStatus;
import com.app.edit.provider.RankProvider;
import com.app.edit.provider.UserProvider;
import com.app.edit.response.user.GetRankRes;
import com.app.edit.response.user.GetUserRes;
import com.app.edit.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.app.edit.config.BaseResponseStatus.SUCCESS;

@Slf4j
@RequestMapping("/api")
@RestController
public class RankController {

    public final RankProvider rankProvider;
    public final JwtService jwtService;

    @Autowired
    public RankController(RankProvider rankProvider,
                          JwtService jwtService){
        this.rankProvider = rankProvider;
        this.jwtService = jwtService;
    }


    @GetMapping("/ranks")
    public BaseResponse<GetRankRes> getRanks(
            @RequestParam(value = "requestRole") String requestRole) {

        try{
            GetRankRes getRankRes = rankProvider.getRank(requestRole);
            return new BaseResponse<>(SUCCESS, getRankRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
