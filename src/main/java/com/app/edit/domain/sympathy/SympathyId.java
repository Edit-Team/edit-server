package com.app.edit.domain.sympathy;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Embeddable
public class SympathyId implements Serializable {

    @Column(name = "commentId", nullable = false, updatable = false)
    private Long commentId;

    @Column(name = "userInfoId", nullable = false, updatable = false)
    private Long userInfoId;

    @Builder
    public SympathyId(Long commentId, Long userInfoId) {
        this.commentId = commentId;
        this.userInfoId = userInfoId;
    }
}
