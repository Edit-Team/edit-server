package com.app.edit.domain.coverletter;

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
@Table(name = "cover_letter")
public class CoverLetter extends BaseEntity {

    /*
     * 자소서 ID
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /*
     * 자소서 작성한 유저 ID
     **/
    @Column(name = "userInfoId", nullable = false, updatable = false)
    private Long userInfoId;

    /*
     * 자소서 내용
     **/
    @Column(name = "content", nullable = false, length = 90)
    private String content;

    /*
     * 자소서 종류(카테고리) ID
     **/
    @Column(name = "categoryId", nullable = false, updatable = false)
    private Long categoryId;

    /*
     * 자소서 삭제 여부
     * default - ACTIVE
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, columnDefinition = "varchar(10) default 'ACTIVE'")
    private State state;

    @Builder
    public CoverLetter(Long userInfoId, String content, Long categoryId, State state) {
        this.userInfoId = userInfoId;
        this.content = content;
        this.categoryId = categoryId;
        this.state = state;
    }
}
