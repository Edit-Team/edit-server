package com.app.edit.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetUserRes {
    private final String name;
    private final String nickname;
    private final String withdrawal;
    private final Long coinCount;
    private final String email;
    private final String phoneNumber;
    private final String etcJobName;
}
