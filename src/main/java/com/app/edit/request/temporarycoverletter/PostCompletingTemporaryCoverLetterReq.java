package com.app.edit.request.temporarycoverletter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class PostCompletingTemporaryCoverLetterReq {

    @NotNull
    private Long originalCoverLetterId;

    @NotBlank
    private String coverLetterContent;
}
