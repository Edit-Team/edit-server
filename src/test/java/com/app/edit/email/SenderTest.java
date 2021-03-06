package com.app.edit.email;

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
    void sesSendTest() {
        // given

        ArrayList<String> to = new ArrayList<>();
        to.add("ast3138@naver.com");
        String subject = "[AWS SES] 테스트발송";
        String content = "SES 메일발송 테스트입니다";

        // when
        sesEmailEmailSender.send(subject, content, to);

        // then
    }
}
