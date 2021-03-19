package com.app.edit.response.user;


import com.app.edit.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetRoleRes {

    private final String name;
    private final UserRole userRole;
}