package com.app.edit.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetWritingTemporaryCoverLetterRes {

    private Long coverLetterCategoryId;
    private String content;
}
