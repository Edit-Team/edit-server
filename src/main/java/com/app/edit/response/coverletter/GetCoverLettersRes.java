package com.app.edit.response.coverletter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetCoverLettersRes {

    private Long coverLetterId;
    private String userProfile;
    private String nickName;
    private String jobName;
    private String coverLetterCategoryName;
    private String coverLetterContent;
    private boolean isSympathy;
    private Long sympathiesCount;
    private boolean isMine;
}
