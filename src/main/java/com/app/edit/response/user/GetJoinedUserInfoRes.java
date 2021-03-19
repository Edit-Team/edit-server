package com.app.edit.response.user;

import com.app.edit.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetJoinedUserInfoRes {

    private UserRole userRole;
}
