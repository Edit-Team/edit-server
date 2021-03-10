package com.app.edit.request.temporarycoverletter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostTemporaryCoverLetterReq {

    private Long coverLetterCategoryId;
    private String coverLetterContent;
}
