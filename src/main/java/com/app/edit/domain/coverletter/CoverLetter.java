package com.app.edit.domain.coverletter;

import com.app.edit.config.BaseEntity;
import com.app.edit.domain.coverlettercategory.CoverLetterCategory;
import com.app.edit.domain.user.UserInfo;
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
    @ManyToOne
    @JoinColumn(name = "userInfoId", nullable = false, updatable = false)
    private UserInfo userInfo;

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

    @ManyToOne
    @JoinColumn(name = "coverLetterCategoryId", nullable = false)
    private CoverLetterCategory coverLetterCategory;

    @Builder
    public CoverLetter(UserInfo userInfo, String content, Long categoryId, State state, CoverLetterCategory coverLetterCategory) {
        this.userInfo = userInfo;
        this.content = content;
        this.categoryId = categoryId;
        this.state = state;
        this.coverLetterCategory = coverLetterCategory;
    }
}
