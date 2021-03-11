package com.app.edit.service;

import com.app.edit.config.BaseException;
import com.app.edit.config.secret.Secret;
import com.app.edit.domain.job.JobRepository;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.domain.user.UserInfoRepository;
import com.app.edit.enums.State;
import com.app.edit.enums.UserRole;
import com.app.edit.provider.UserProvider;
import com.app.edit.request.user.PostUserReq;
import com.app.edit.response.user.PostUserRes;
import com.app.edit.utils.AES128;
import com.app.edit.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static com.app.edit.config.BaseResponseStatus.*;

@Service
public class UserService {

    private final UserInfoRepository userInfoRepository;
    private final EmailSenderService emailSenderService;
    private final JobRepository jobRepository;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserInfoRepository userRepository,
                       EmailSenderService emailSenderService,
                       JobRepository jobRepository,
                       UserProvider userProvider,
                       JwtService jwtService) {
        this.userInfoRepository = userRepository;
        this.emailSenderService = emailSenderService;
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

    /**
     * 비밀번호 찾기
     * @param name
     * @param email
     * @param phoneNumber
     */
    @Transactional
    public void searchPassword(String name, String email, String phoneNumber) throws BaseException {

        UserInfo user = userInfoRepository
                .findByStateAndNameAndPhoneNumberAndEmail(State.ACTIVE, name,phoneNumber,email)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_USER));

        //임시 비밀번호 발급
        String temporaryPassword = emailSenderService.createKey("password");

        ArrayList<String> to = new ArrayList<>();
        to.add(email);
        String subject = "<임시 비밀번호 발급>";
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<!DOCTYPE html>");
        emailContent.append("<html>");
        emailContent.append("<head>");
        emailContent.append("</head>");
        emailContent.append("<body>");
        emailContent.append(
                " <div" 																																																	+
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">"		+
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"																															+
                        "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">EDIT.</span><br />"																													+
                        "		<span style=\"color: #02b875\">임시 비밀번호 발급</span> 안내입니다."																																				+
                        "	</h1>\n"																																																+
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">"																													+
                        "		안녕하세요.<br />"																																													+
                        "		EDIT. 임시 비밀번호 발급 관련된 메일 전송입니다..<br />"																																						+
                        "		인증코드는 "+ temporaryPassword + "입니다<br />"																													+
                        "		감사합니다."																																															+
                        "	</p>"																																																	+
                        "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>"																																		+
                        " </div>"
        );
        emailContent.append("</body>");
        emailContent.append("</html>");

        // when
        String encodingPassword;
        try {
            encodingPassword = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(temporaryPassword);
        }catch (Exception ignored) {
            throw new BaseException(FAILED_TO_UPDATE_USER);
        }
        emailSenderService.send(subject, emailContent.toString(), to);
        user.setPassword(encodingPassword);
    }
}
