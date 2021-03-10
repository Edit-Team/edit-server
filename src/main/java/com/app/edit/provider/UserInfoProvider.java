package com.app.edit.provider;

import com.app.edit.domain.user.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoProvider {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoProvider(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

}
