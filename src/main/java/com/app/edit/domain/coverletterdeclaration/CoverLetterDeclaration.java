package com.app.edit.domain.coverletterdeclaration;

import com.app.edit.config.BaseEntity;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.enums.IsProcessing;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "cover_letter_declaration")
public class CoverLetterDeclaration extends BaseEntity {

    /*
     * 자소서 신고 ID
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    /*
     * 신고당한 자소서 ID
     **/
    @ManyToOne
    @JoinColumn(name = "coverLetterId", nullable = false, updatable = false)
    private CoverLetter coverLetter;

    /*
     * 신고한 유저 ID
     **/
    @Column(name = "userInfoId", nullable = false, updatable = false)
    private Long userInfoId;

    /*
     * 신고 처리 여부
     * default - NO
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "isProcessing", nullable = false, columnDefinition = "varchar(3) default 'NO'")
    private IsProcessing isProcessing;

    @Builder
    public CoverLetterDeclaration(CoverLetter coverLetter, Long userInfoId, IsProcessing isProcessing) {
        this.coverLetter = coverLetter;
        this.userInfoId = userInfoId;
        this.isProcessing = isProcessing;
    }
}
