package com.app.edit.domain.commentdeclaration;

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
@Table(name = "comment_declaration")
public class CommentDeclaration extends BaseEntity {

    /*
     * 코멘트 신고 ID
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*
     * 신고당한 코멘트 ID
     **/
    @Column(name = "commentId", nullable = false, updatable = false)
    private Long commentId;

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
    public CommentDeclaration(Long commentId, Long userInfoId, FlagYN isProcessing) {
        this.commentId = commentId;
        this.userInfoId = userInfoId;
        this.isProcessing = isProcessing;
    }
}
