package com.app.edit;

import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.UserRole;

public class enumTest {
    public static void main(String[] args){
        UserInfo userInfo = UserInfo.builder()
                .id(1L)
                .email("test")
                .etcJobName("test")
                .isCertificatedEmail(null)
                .isCertificatedMentor(null)
                .name("test")
                .coinCount(1L)
                .nickName("test")
                .phoneNumber("test")
                .userRole(UserRole.MENTEE)
                .state(null)
                .withdrawal("test")
                .build();
        System.out.println(userInfo.getUserRole() == UserRole.MENTEE);
    }
}
