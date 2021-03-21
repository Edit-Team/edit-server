package com.app.edit.response.user;

import com.app.edit.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostUserRes {
    private final String jwt;
    private final UserRole userRole;
}
