package com.app.edit.request.user;

import com.app.edit.enums.UserRole;
import lombok.*;

@AllArgsConstructor
@Getter
@Builder
public class PostUserReq {
    private final String name;
    private final String nickname;
    private final String jobName;
    private final String email;
    private final String etcJobName;
    private final String phoneNumber;
    private final String password;
    private final String authenticationPassword;
    private final String userRole;
}
