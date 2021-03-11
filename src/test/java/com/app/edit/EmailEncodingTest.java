package com.app.edit;

import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.UserRole;
import com.app.edit.response.user.GetEmailRes;

import java.util.Arrays;

public class EmailEncodingTest {
    public static void main(String[] args){
        UserInfo userInfo = UserInfo.builder()
                .id(1L)
                .email("a34@naver.com")
                .etcJobName("test")
                .isCertificatedMentor(null)
                .name("test")
                .coinCount(1L)
                .nickName("test")
                .phoneNumber("test")
                .userRole(UserRole.MENTEE)
                .state(null)
                .withdrawal("test")
                .build();
        String email = userInfo.getEmail();
        StringBuffer sb = new StringBuffer();
        sb.append(email);
        int size = email.indexOf('@');
        int start = size - (size / 3);
        char[] stars = new char[size / 3];
        //4 이하는 그냥 보여주기
        //5 = 1자리
        //6 = 2자리
        //7 = 2자리
        //8 = 2자리
        //9 = 3자리
        for(int i = size - (size / 3); i < size ; i++)
            sb.replace(i,i + 1, "*");
       // sb.replace(size - (size / 3), size, "*");
        System.out.println(sb.toString());

    }
}
