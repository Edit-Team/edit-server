package com.app.edit.service;

import com.app.edit.config.BaseException;
import com.app.edit.config.secret.Secret;
import com.app.edit.domain.job.JobRepository;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.domain.user.UserInfoRepository;
import com.app.edit.enums.UserRole;
import com.app.edit.provider.UserProvider;
import com.app.edit.request.user.PostUserReq;
import com.app.edit.response.user.PostUserRes;
import com.app.edit.utils.AES128;
import com.app.edit.utils.JwtService;
import com.app.edit.utils.UserRoleConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.app.edit.config.BaseResponseStatus.*;

@Service
public class UserService {

    private final UserInfoRepository userInfoRepository;
    private final JobRepository jobRepository;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserInfoRepository userRepository,
                       JobRepository jobRepository,
                       UserProvider userProvider,
                       JwtService jwtService) {
        this.userInfoRepository = userRepository;
        this.jobRepository = jobRepository;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }

    public PostUserRes createUserInfo(PostUserReq parameters) throws BaseException {

        UserInfo existsUser = null;
        try {
            // 1-1. 이미 존재하는 회원이 있는지 조회
            existsUser = userProvider.retrieveUserByEmail(parameters.getEmail());
        } catch (BaseException exception) {
            // 1-2. 이미 존재하는 회원이 없다면 그대로 진행
            if (exception.getStatus() != NOT_FOUND_USER) {
                throw exception;
            }
        }
        // 1-3. 이미 존재하는 회원이 있다면 return DUPLICATED_USER
        if (existsUser != null) {
            throw new BaseException(DUPLICATED_USER);
        }

        // 2. 유저 정보 생성
        String EncodingPassword = "";
        try {
            EncodingPassword = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(parameters.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_POST_USER);
        }
        UserRoleConverter userRoleConverter = new UserRoleConverter();
        UserInfo newUser = UserInfo.builder()
                .name(parameters.getName())
                .nickName(parameters.getNickname())
                .password(EncodingPassword)
                .email(parameters.getEmail())
                .userRole(UserRole.MENTEE)
                .coinCount(0L)
                .job(jobRepository.findById(1L).orElse(null))
                .etcJobName(parameters.getEtcJobName().equals("NONE") ? "NONE": parameters.getEtcJobName())
                .phoneNumber(parameters.getPhoneNumber())
                .build();

        // 3. 유저 정보 저장
        try {
            newUser = userInfoRepository.save(newUser);
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_POST_USER);
        }

        // 4. JWT 생성
        String jwt = jwtService.createJwt(newUser.getId(),newUser.getUserRole());


        return PostUserRes.builder()
                .jwt(jwt)
                .build();
    }
}
