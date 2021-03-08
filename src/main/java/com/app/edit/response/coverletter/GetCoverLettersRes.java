package com.app.edit.response.coverletter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GetCoverLettersRes {

    List<CoverLetterInfo> coverLetterInfos;
    private Pageable pageable;
}
