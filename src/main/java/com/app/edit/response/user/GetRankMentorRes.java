package com.app.edit.response.user;

import com.app.edit.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetRankMentorRes {

    private final Long rankId;
    private final String nickName;
    private final String emotionName;
    private final String colorName;
    private final UserRole userRole;
    private final Long CommentCount;
    private final Long CommentAdoptCount;
}
