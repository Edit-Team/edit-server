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
@Table(name = "TEMPORARY_COVER_LETTER")
public class TemporaryCoverLetter extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "categoryId", nullable = false, updatable = false)
    private Long categoryId;

    @Column(name = "userInfoId", nullable = false, updatable = false)
    private Long userInfoId;

    @Column(name = "content", nullable = false, length = 90)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 15)
    private CoverLetterType type;

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
