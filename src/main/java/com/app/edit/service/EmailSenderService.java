package com.app.edit.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.app.edit.request.EmailRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Value("${ses.email}")
    private String from;

    /**
     * 이메일 전송
     */
    public void send(String subject, String content, List<String> receivers) {
        if(receivers.size() == 0) {
            log.error("메일을 전송할 대상이 없습니다: [{}]", subject);
            return;
        }

        EmailRequest senderDto = EmailRequest.builder()
                .from(from)
                .to(receivers)
                .subject(subject)
                .content(content)
                .build();

        SendEmailResult sendEmailResult = amazonSimpleEmailService.sendEmail(senderDto.toSendRequestDto());

        if(sendEmailResult.getSdkHttpMetadata().getHttpStatusCode() == 200) {
            log.info("[AWS SES] 메일전송완료 => " + senderDto.getTo());
        }else {
            log.error("[AWS SES] 메일전송 중 에러가 발생했습니다: {}", sendEmailResult.getSdkResponseMetadata().toString());
            log.error("발송실패 대상자: " + senderDto.getTo() + " / subject: " + senderDto.getSubject());
        }
    }
}