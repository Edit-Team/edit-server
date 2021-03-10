package com.app.edit.request.temporarycoverletter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class PostWritingTemporaryCoverLetterReq {

    @NotNull
    private Long coverLetterCategoryId;

    @NotBlank
    private String coverLetterContent;
}
