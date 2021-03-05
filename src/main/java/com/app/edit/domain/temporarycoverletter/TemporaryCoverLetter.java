package com.app.edit.domain.temporarycoverletter;

import com.app.edit.config.BaseEntity;
import com.app.edit.enums.CoverLetterType;
import com.app.edit.enums.State;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "temporary_cover_letter")
public class TemporaryCoverLetter extends BaseEntity {

    /*
     * 임시 자소서 ID
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /*
     * 자소서 카테고리 ID
     **/
    @Column(name = "categoryId", nullable = false, updatable = false)
    private Long categoryId;

    /*
     * 임시 자소서 작성 유저 ID
     **/
    @Column(name = "userInfoId", nullable = false, updatable = false)
    private Long userInfoId;

    /*
     * 임시 자소서 내용
     **/
    @Column(name = "content", nullable = false, length = 90)
    private String content;

    /*
     * 임시 자소서 타입
     * 작성 중인 자소서 - WRITING, 완성 중인 자소서 - COMPLETING
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 15)
    private CoverLetterType type;

    /*
     * 자소서 삭제 여부
     * ACTIVE - 삭제되지 않음, INACTIVE - 삭제됨
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, columnDefinition = "varchar(10) default 'ACTIVE'")
    private State state;

    @Builder
    public TemporaryCoverLetter(Long categoryId, Long userInfoId, String content, CoverLetterType type, State state) {
        this.categoryId = categoryId;
        this.userInfoId = userInfoId;
        this.content = content;
        this.type = type;
        this.state = state;
    }
}
