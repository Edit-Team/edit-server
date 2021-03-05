package com.app.edit.domain.coverletterdeclaration;

import com.app.edit.config.BaseEntity;
import com.app.edit.enums.FlagYN;
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
    @Column(name = "coverLetterId", nullable = false, updatable = false)
    private Long coverLetterId;

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
    private FlagYN isProcessing;

    @Builder
    public CoverLetterDeclaration(Long coverLetterId, Long userInfoId, FlagYN isProcessing) {
        this.coverLetterId = coverLetterId;
        this.userInfoId = userInfoId;
        this.isProcessing = isProcessing;
    }
}
