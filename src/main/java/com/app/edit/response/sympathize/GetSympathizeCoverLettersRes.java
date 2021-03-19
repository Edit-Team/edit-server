package com.app.edit.response.sympathize;

import com.app.edit.response.user.GetSympathizeUserRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetSympathizeCoverLettersRes {

    private final Long id;
    private final GetSympathizeUserRes getSympathizeUserRes;
    private final GetSympathizeCoverLetterRes getSympathizeCoverLetterRes;



}
