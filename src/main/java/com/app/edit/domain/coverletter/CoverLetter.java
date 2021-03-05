package com.app.edit.domain.coverletter;

import com.app.edit.config.BaseEntity;
import com.app.edit.domain.comment.Comment;
import com.app.edit.domain.coverlettercategory.CoverLetterCategory;
import com.app.edit.domain.temporarycomment.TemporaryComment;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.State;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "coverLetter", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "coverLetter", cascade = CascadeType.ALL)
    private List<TemporaryComment> temporaryComments;

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setCoverLetter(this);
    }

    public void addTemporaryComment(TemporaryComment temporaryComment) {
        this.temporaryComments.add(temporaryComment);
        temporaryComment.setCoverLetter(this);
    }

    @Builder
    public CoverLetter(UserInfo userInfo, String content, Long categoryId, State state,
                       CoverLetterCategory coverLetterCategory, List<Comment> comments,
                       List<TemporaryComment> temporaryComments) {
        this.userInfo = userInfo;
        this.content = content;
        this.categoryId = categoryId;
        this.state = state;
        this.coverLetterCategory = coverLetterCategory;
        this.comments = comments;
        this.temporaryComments = temporaryComments;
    }
}
