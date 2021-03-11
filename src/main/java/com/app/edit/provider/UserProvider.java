package com.app.edit.provider;

import com.app.edit.config.BaseException;
import com.app.edit.config.secret.Secret;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.domain.user.UserInfoRepository;
import com.app.edit.enums.AuthenticationCheck;
import com.app.edit.enums.State;
import com.app.edit.response.user.DuplicationCheck;
import com.app.edit.response.user.GetEmailRes;
import com.app.edit.response.user.GetUserRes;
import com.app.edit.response.user.PostUserRes;
import com.app.edit.service.EmailSenderService;
import com.app.edit.utils.AES128;
import com.app.edit.utils.GetDateTime;
import com.app.edit.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.app.edit.config.BaseResponseStatus.*;

@Service
@Transactional(readOnly = true)
public class UserProvider {

    private final UserInfoRepository userInfoRepository;
    private final EmailSenderService sesEmailEmailSender;
    private final GetDateTime getDateTime;
    private final HashMap<String,String> authenticationCodeRepository;
    private final JwtService jwtService;

    @Autowired
    public UserProvider(UserInfoRepository userRepository,
                        EmailSenderService sesEmailEmailSender,
                        GetDateTime getDateTime, HashMap<String, String> authenticationCodeRepository, JwtService jwtService) {
        this.userInfoRepository = userRepository;
        this.sesEmailEmailSender = sesEmailEmailSender;
        this.getDateTime = getDateTime;
        this.authenticationCodeRepository = authenticationCodeRepository;
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

    /**
     * 중복 이메일이 있는지 검사
     * @param email
     * @return
     * @throws BaseException
     */
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

    /**
     * 이메일, 닉네임 중복 검사
     * @param email
     * @return
     * @throws BaseException
     */
    public DuplicationCheck checkDuplication(String email, String nickName) throws BaseException{

        List<UserInfo> userInfoList = new LinkedList<>();

        //nickName이 null이면 이메일 검증
        if(nickName == null){
            userInfoList = userInfoRepository.findByStateAndEmailIsContaining(State.ACTIVE,email);
        }

        //email이 null이면 닉네임 검증
        if(email == null){
            userInfoList = userInfoRepository.findByStateAndEmailIsContaining(State.ACTIVE,nickName);
        }
        return userInfoList.size() == 0 ?
                DuplicationCheck.builder().duplicationCheck("NO").build() :
                DuplicationCheck.builder().duplicationCheck("YES").build() ;
        
    }

    /**
     * 이메일 인증
     * @param email
     * @throws BaseException
     */
    public void authenticationEmail(String email) throws BaseException {

        String authenticationCode = sesEmailEmailSender.createKey("code");
        ArrayList<String> to = new ArrayList<>();
        to.add(email);
        String subject = "<이메일 인증>";
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
                        "		<span style=\"color: #02b875\">메일인증</span> 안내입니다."																																				+
                        "	</h1>\n"																																																+
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">"																													+
                        "		안녕하세요.<br />"																																													+
                        "		EDIT.에 가입해 주셔서 진심으로 감사드립니다.<br />"																																						+
                        "		인증코드는 "+ authenticationCode + "입니다<br />"																													+
                        "		감사합니다."																																															+
                        "	</p>"																																																	+
                        "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>"																																		+
                        " </div>"
        );
        emailContent.append("</body>");
        emailContent.append("</html>");

        //인증코드 3분제한으로 저장
        authenticationCodeRepository.put(authenticationCode,getDateTime.getCustomDataTime("plus",3L));

        // when
        sesEmailEmailSender.send(subject, emailContent.toString(), to);
    }

    /**
     * 이메일 인증 코드 검증
     * @param authenticationCode
     * @return
     */
    public AuthenticationCheck authenticationCode(String authenticationCode) throws BaseException {

        String authenticationTime = authenticationCodeRepository.get(authenticationCode);

        if(authenticationTime == null)
            throw new BaseException(FAILED_TO_AUTHENTICATION_CODE);


        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime parsedTime = LocalDateTime.parse(authenticationTime, getDateTime.GetFormatter());

        //현재 시간이 인증 시간 이전이라면 true
        if(currentTime.isBefore(parsedTime))
            return AuthenticationCheck.YES;
        else
            throw new BaseException(AUTHENTICATION_TIME_EXPIRED);
    }

    /**
     * 로그인
     * @param email
     * @param password
     * @return
     */
    public PostUserRes login(String email, String password) throws BaseException{

        String EncodingPassword;
        try {
            EncodingPassword = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(password);
        }catch (Exception ignored) {
            throw new BaseException(FAILED_TO_ENCRYPT_PASSWORD);
        }

        UserInfo user = userInfoRepository.findByStateAndEmailAndPassword(State.ACTIVE, email, EncodingPassword)
                .orElseThrow(() -> new BaseException(FAILED_TO_LOGIN));

        return PostUserRes.builder()
                .jwt(jwtService.createJwt(user.getId(),user.getUserRole()))
                .build();

    }

    /**
     * 로그아웃
     * @return
     */
    public PostUserRes logout() {
        return null;
    }

    /**
     * 이메일 찾기
     * @param name
     * @param phoneNumber
     * @return
     */
    public GetEmailRes searchEmail(String name, String phoneNumber) throws BaseException{

        UserInfo userInfo = userInfoRepository.findByStateAndNameAndPhoneNumber(State.ACTIVE,name,phoneNumber)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_USER));

        String email = userInfo.getEmail();
        StringBuilder sb = new StringBuilder();
        sb.append(email);
        int index = email.indexOf('@');
        for(int i = index - (index / 3); i < index ; i++)
            sb.replace(i,i + 1, "*");
        return GetEmailRes.builder()
                .email(sb.toString())
                .build();
    }

    /**
     * 비밀번호 인증
     * YES가 인증됨, NO는 인증 안됨
     * @param password
     * @return
     */
    public AuthenticationCheck authenticationPassword(Long userId,String password) throws BaseException{

        UserInfo userInfo = userInfoRepository.findByStateAndId(State.ACTIVE,userId)
                .orElseThrow(() -> new BaseException(FAILED_TO_GET_USER));

        String encodingPassword;
        try{
            encodingPassword = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(password);
        }catch (Exception ignore){
            throw new BaseException(FAILED_TO_ENCRYPT_PASSWORD);
        }

        if(userInfo.getPassword().equals(encodingPassword))
            return AuthenticationCheck.YES;
        else
            return AuthenticationCheck.NO;
    }
}
