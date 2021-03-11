package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.domain.user.UserInfoRepository;
import com.app.edit.enums.State;
import com.app.edit.response.user.GetUserRes;
import com.app.edit.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

import static com.app.edit.config.BaseResponseStatus.FAILED_TO_GET_USER;
import static com.app.edit.config.BaseResponseStatus.NOT_FOUND_USER;

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
    @Transactional(readOnly = true)
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

    public UserInfo retrieveUserByEmail(String email) throws BaseException {
        List<UserInfo> existsUserList;

        // DB에 접근해서 email로 회원 정보 조회
        try{
            existsUserList = userInfoRepository.findByStateAndEmailIsContaining(State.ACTIVE,email);
        }catch (Exception e){
            throw new BaseException(FAILED_TO_GET_USER);
        }

        // userList에 중복된 회원이 있는지 검사
        UserInfo user;
        if (existsUserList != null && existsUserList.size() > 0) {
            user = existsUserList.get(0);
        } else {
            throw new BaseException(NOT_FOUND_USER);
        }

        return user;
    }
}
