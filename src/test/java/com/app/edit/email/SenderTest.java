package com.app.edit.email;

import com.app.edit.config.BaseException;
import com.app.edit.service.EmailSenderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmailEmailSenderServiceTest {

    @Autowired
    private EmailSenderService sesEmailEmailSender;

    @Test
    @DisplayName("AWS SES 이메일전송 테스트")
    void sesSendTest() throws BaseException {
        // given
        ArrayList<String> to = new ArrayList<>();
        to.add("ast3138@naver.com");
        String subject = "<이메일 인증>";
        StringBuilder emailcontent = new StringBuilder();
        emailcontent.append("<!DOCTYPE html>");
        emailcontent.append("<html>");
        emailcontent.append("<head>");
        emailcontent.append("</head>");
        emailcontent.append("<body>");
        emailcontent.append(
                " <div" 																																																	+
                        "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">"		+
                        "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"																															+
                        "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">EDIT.</span><br />"																													+
                        "		<span style=\"color: #02b875\">메일인증</span> 안내입니다."																																				+
                        "	</h1>\n"																																																+
                        "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">"																													+
                        "에딧"																																																+
                        "		님 안녕하세요.<br />"																																													+
                        "		EDIT.에 가입해 주셔서 진심으로 감사드립니다.<br />"																																						+
                        "		인증코드는 "+ sesEmailEmailSender.createKey()+"<br />"																													+
                        "		감사합니다."																																															+
                        "	</p>"																																																	+
                        "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>"																																		+
                        " </div>"
        );
        emailcontent.append("</body>");
        emailcontent.append("</html>");
        String content = "SES 메일발송 테스트입니다";

        // when
        sesEmailEmailSender.send(subject, emailcontent.toString(), to);

        // then
    }
}
