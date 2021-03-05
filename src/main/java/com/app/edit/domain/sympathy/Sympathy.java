package com.app.edit.domain.sympathy;

import com.app.edit.config.BaseEntity;
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
@Table(name = "SYMPATHY")
public class Sympathy extends BaseEntity {

    /*
     * 공감 ID
     * 복합키 -> commentId + userInfoId
     **/
    @EmbeddedId
    private SympathyId sympathyId;

    /*
     * 공감 여부
     * default - ACTIVE
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, columnDefinition = "varchar(10) default 'ACTIVE'")
    private State state;

    @Builder
    public Sympathy(SympathyId sympathyId, State state) {
        this.sympathyId = sympathyId;
        this.state = state;
    }
}
