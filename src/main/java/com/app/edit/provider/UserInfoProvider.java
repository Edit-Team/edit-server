package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.domain.changerolereqeust.ChangeRoleRequest;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.domain.user.UserInfoRepository;
import com.app.edit.enums.State;
import com.app.edit.response.user.GetJoinedUserInfoRes;
import com.app.edit.service.ChangeRoleRequestService;
import com.app.edit.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.app.edit.config.BaseResponseStatus.ALREADY_DELETED_USER;
import static com.app.edit.config.BaseResponseStatus.NOT_FOUND_USER_INFO;

@Transactional
@Service
public class UserInfoProvider {

    private final UserInfoRepository userInfoRepository;
    private final ChangeRoleRequestProvider changeRoleRequestProvider;
    private final ChangeRoleRequestService changeRoleRequestService;
    private final JwtService jwtService;

    @Autowired
    public UserInfoProvider(UserInfoRepository userInfoRepository, ChangeRoleRequestProvider changeRoleRequestProvider,
                            ChangeRoleRequestService changeRoleRequestService, JwtService jwtService) {
        this.userInfoRepository = userInfoRepository;
        this.changeRoleRequestProvider = changeRoleRequestProvider;
        this.changeRoleRequestService = changeRoleRequestService;
        this.jwtService = jwtService;
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

    public GetJoinedUserInfoRes getJoinedUserInfo() throws BaseException {
        Long userInfoId = jwtService.getUserInfo().getUserId();
        UserInfo userInfo = getUserInfoById(userInfoId);
        Optional<ChangeRoleRequest> changeRoleRequest = changeRoleRequestProvider.getChangeRoleRequestByUserInfo(userInfo);
        changeRoleRequest.ifPresent(request -> {
            request.process();
            changeRoleRequestService.updateChangeRoleRequest(request);
            userInfo.changeUserRole();
            userInfoRepository.save(userInfo);
        });
        return new GetJoinedUserInfoRes(userInfo.getUserRole());
    }
}
