package com.app.edit.request.coverletter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class PostCoverLetterDeclarationReq {

    @NotNull
    private Long coverLetterId;
}
