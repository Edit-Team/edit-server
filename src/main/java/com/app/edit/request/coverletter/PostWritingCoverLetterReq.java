package com.app.edit.request.coverletter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class PostWritingCoverLetterReq {

    @NotNull(message = "자소서 종류를 선택해주세요.")
    private Long coverLetterCategoryId;

    @NotBlank(message = "자소서 내용은 공백일 수 없습니다.")
    @Size(max = 150, message = "자소서 내용의 길이는 {max}자를 초과할 수 없습니다.")
    private String coverLetterContent;
}
