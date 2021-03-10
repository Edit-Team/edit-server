package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.domain.user.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.app.edit.config.BaseResponseStatus.NOT_FOUND_USER_INFO;

@Service
public class UserInfoProvider {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoProvider(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfo getUserInfoById(Long userInfoId) throws BaseException {
        Optional<UserInfo> userInfo = userInfoRepository.findById(userInfoId);
        if (userInfo.isEmpty()) {
            throw new BaseException(NOT_FOUND_USER_INFO);
        }
        return userInfo.get();
    }
}
