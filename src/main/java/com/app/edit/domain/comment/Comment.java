package com.app.edit.domain.comment;

import com.app.edit.config.BaseEntity;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.FlagYN;
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
@Table(name = "comment")
public class Comment extends BaseEntity {

    /*
     * 코멘트 ID
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /*
     * 코멘트 등록한 유저 ID
     **/
    @ManyToOne
    @JoinColumn(name = "userInfoId", nullable = false, updatable = false)
    private UserInfo userInfo;

    /*
     * 자소서 ID
     **/
    @ManyToOne
    @JoinColumn(name = "coverLetterId", nullable = false, updatable = false)
    private CoverLetter coverLetter;

    /*
     * 문장에 대한 전체 평가
     * default - NONE
     **/
    @Column(name = "sentence_evaluation", nullable = false, columnDefinition = "varchar(10) default 'NONE'")
    private String sentenceEvaluation;

    /*
     * 구체성과 논리성
     * default - NONE
     **/
    @Column(name = "concreteness_logic", nullable = false, columnDefinition = "varchar(10) default 'NONE'")
    private String concretenessLogic;

    /*
     * 성실성
     * default - NONE
     **/
    @Column(name = "sincerity", nullable = false, columnDefinition = "varchar(10) default 'NONE'")
    private String sincerity;

    /*
     * 활동성
     * default - NONE
     **/
    @Column(name = "activity", nullable = false, columnDefinition = "varchar(10) default 'NONE'")
    private String activity;

    /*
     * 코멘트 내용
     **/
    @Column(name = "content", nullable = false, length = 90)
    private String content;

    /*
     * 코멘트 채택 여부
     * default - NO
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "isAdopted", nullable = false, columnDefinition = "varchar(3) default 'NO'")
    private FlagYN isAdopted;

    /*
     * 삭제 여부
     * default - ACTIVE
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, columnDefinition = "varchar(10) default 'ACTIVE'")
    private State state;

    @Builder
    public Comment(UserInfo userInfo, CoverLetter coverLetter, String sentenceEvaluation, String concretenessLogic,
                   String sincerity, String activity, String content, FlagYN isAdopted, State state) {
        this.userInfo = userInfo;
        this.coverLetter = coverLetter;
        this.sentenceEvaluation = sentenceEvaluation;
        this.concretenessLogic = concretenessLogic;
        this.sincerity = sincerity;
        this.activity = activity;
        this.content = content;
        this.isAdopted = isAdopted;
        this.state = state;
    }
}
