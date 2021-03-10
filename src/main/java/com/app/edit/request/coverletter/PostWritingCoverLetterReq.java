package com.app.edit.request.coverletter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostWritingCoverLetterReq {

    private Long coverLetterCategoryId;
    private String coverLetterContent;
}
