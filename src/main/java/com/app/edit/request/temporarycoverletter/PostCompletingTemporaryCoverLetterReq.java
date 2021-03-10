package com.app.edit.request.temporarycoverletter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostCompletingTemporaryCoverLetterReq {

    private Long originalCoverLetterId;
    private String coverLetterContent;
}
