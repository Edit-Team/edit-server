package com.app.edit.domain.sympathy;

import com.app.edit.config.BaseEntity;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.enums.State;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "sympathy")
public class Sympathy extends BaseEntity {

    /*
     * 공감 ID
     * 복합키 -> coverLetterId + userInfoId
     **/
    @EmbeddedId
    private SympathyId sympathyId;

    @MapsId(value = "coverLetterId")
    @ManyToOne
    @JoinColumn(name = "coverLetterId", nullable = false)
    private CoverLetter coverLetter;

    /*
     * 공감 여부
     * default - ACTIVE
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, columnDefinition = "varchar(10) default 'ACTIVE'")
    private State state;

    @Builder
    public Sympathy(SympathyId sympathyId, CoverLetter coverLetter, State state) {
        this.sympathyId = sympathyId;
        this.coverLetter = coverLetter;
        this.state = state;
    }
}
