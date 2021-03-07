package com.app.edit.domain.coverletter;

import com.app.edit.config.BaseEntity;
import com.app.edit.domain.comment.Comment;
import com.app.edit.domain.coverlettercategory.CoverLetterCategory;
import com.app.edit.domain.coverletterdeclaration.CoverLetterDeclaration;
import com.app.edit.domain.sympathy.Sympathy;
import com.app.edit.domain.temporarycomment.TemporaryComment;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.CoverLetterType;
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
     * 자소서 삭제 여부
     * default - ACTIVE
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, columnDefinition = "varchar(10) default 'ACTIVE'")
    private State state;

    /*
     * 자소서 종류(카테고리) ID
     **/
    @ManyToOne
    @JoinColumn(name = "coverLetterCategoryId", nullable = false)
    private CoverLetterCategory coverLetterCategory;

    /*
     * 자소서 타입
     * 작성한(등록한) 자소서 - WRITING, 완성한 자소서 - COMPLETING
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 15)
    private CoverLetterType type;

    @OneToMany(mappedBy = "coverLetter", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "coverLetter", cascade = CascadeType.ALL)
    private List<TemporaryComment> temporaryComments;

    @OneToMany(mappedBy = "coverLetter", cascade = CascadeType.ALL)
    private List<CoverLetterDeclaration> coverLetterDeclarations;

    @OneToMany(mappedBy = "coverLetter", cascade = CascadeType.ALL)
    private List<Sympathy> sympathies;

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setCoverLetter(this);
    }

    public void addTemporaryComment(TemporaryComment temporaryComment) {
        this.temporaryComments.add(temporaryComment);
        temporaryComment.setCoverLetter(this);
    }

    public void addCoverLetterDeclaration(CoverLetterDeclaration coverLetterDeclaration) {
        this.coverLetterDeclarations.add(coverLetterDeclaration);
        coverLetterDeclaration.setCoverLetter(this);
    }

    public void addSympathy(Sympathy sympathy) {
        this.sympathies.add(sympathy);
        sympathy.setCoverLetter(this);
    }

    @Builder
    public CoverLetter(UserInfo userInfo, String content, State state, CoverLetterCategory coverLetterCategory,
                       List<Comment> comments, List<TemporaryComment> temporaryComments,
                       List<CoverLetterDeclaration> coverLetterDeclarations, List<Sympathy> sympathies) {
        this.userInfo = userInfo;
        this.content = content;
        this.state = state;
        this.coverLetterCategory = coverLetterCategory;
        this.comments = comments;
        this.temporaryComments = temporaryComments;
        this.coverLetterDeclarations = coverLetterDeclarations;
        this.sympathies = sympathies;
    }
}
