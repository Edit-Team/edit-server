package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.domain.user.UserInfoRepository;
import com.app.edit.enums.State;
import com.app.edit.response.GetUserRes;
import com.app.edit.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.app.edit.config.BaseResponseStatus.FAILED_TO_GET_USER;

@Service
public class UserProvider {

    private final UserInfoRepository userInfoRepository;
    private final JwtService jwtService;

    @Autowired
    public UserProvider(UserInfoRepository userRepository, JwtService jwtService) {
        this.userInfoRepository = userRepository;
        this.jwtService = jwtService;
    }



    /**
     * 전체 회원 조회
     * @return
     * @throws BaseException
     */
    public List<GetUserRes> retrieveUserList() throws BaseException {

        List<UserInfo> userList;

        // DB에 접근해서 전체 회원 조회
        try{
            userList = userInfoRepository.findByState(State.ACTIVE);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_GET_USER);
        }


        return userList.stream()
                .map(user -> GetUserRes.builder()
                        .name(user.getName())
                        .nickname(user.getNickName())
                        .phoneNumber(user.getPhoneNumber())
                        .etcJobName(user.getEtcJobName())
                        .email(user.getEmail())
                        .withdrawal(user.getWithdrawal())
                        .coinCount(user.getCoinCount())
                        .build())
                .collect(Collectors.toList());
    }
}
