package com.app.edit.domain.temporarycomment;

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
@Table(name = "TEMPORARY_COMMENT")
public class TemporaryComment extends BaseEntity {

    /*
     * 임시 저장된 코멘트 ID
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /*
     * 코멘트를 임시 저장한 유저 ID
     **/
    @Column(name = "userInfoId", nullable = false, updatable = false)
    private Long userInfoId;

    /*
     * 임시 저장된 코멘트를 달려고 했던 자소서 ID
     **/
    @Column(name = "coverLetterId", nullable = false, updatable = false)
    private Long coverLetterId;

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
     * 삭제 여부
     * default - ACTIVE
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, columnDefinition = "varchar(10) default 'ACTIVE'")
    private State state;

    @Builder
    public TemporaryComment(Long userInfoId, Long coverLetterId, String sentenceEvaluation, String concretenessLogic, String sincerity, String activity, String content, State state) {
        this.userInfoId = userInfoId;
        this.coverLetterId = coverLetterId;
        this.sentenceEvaluation = sentenceEvaluation;
        this.concretenessLogic = concretenessLogic;
        this.sincerity = sincerity;
        this.activity = activity;
        this.content = content;
        this.state = state;
    }
}
