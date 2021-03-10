package com.app.edit.request.coverletter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostCompletingCoverLetterReq {

    private Long originalCoverLetterId;
    private String coverLetterContent;
}
