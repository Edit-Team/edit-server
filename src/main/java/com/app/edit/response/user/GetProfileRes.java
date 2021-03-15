package com.app.edit.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetProfileRes {

    private final String name;
    private final String emotionName;
    private final String colorName;
}
