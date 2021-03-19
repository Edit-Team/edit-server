package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.domain.user.UserInfoRepository;
import com.app.edit.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.app.edit.config.BaseResponseStatus.ALREADY_DELETED_USER;
import static com.app.edit.config.BaseResponseStatus.NOT_FOUND_USER_INFO;

@Transactional(readOnly = true)
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
        if (userInfo.get().getState().equals(State.INACTIVE)) {
            throw new BaseException(ALREADY_DELETED_USER);
        }
        return userInfo.get();
    }
}
