package com.app.edit.domain.user;

import com.app.edit.config.BaseEntity;
import com.app.edit.domain.certificationRequest.CertificationRequest;
import com.app.edit.domain.changerolecategory.ChangeRoleCategory;
import com.app.edit.domain.changerolereqeust.ChangeRoleRequest;
import com.app.edit.domain.job.Job;
import com.app.edit.domain.mentor.MentorInfo;
import com.app.edit.domain.sympathy.Sympathy;
import com.app.edit.domain.temporarycomment.TemporaryComment;
import com.app.edit.domain.temporarycoverletter.TemporaryCoverLetter;
import com.app.edit.enums.AuthenticationCheck;
import com.app.edit.enums.State;
import com.app.edit.enums.UserRole;
import com.app.edit.domain.appreciate.Appreciate;
import com.app.edit.domain.comment.Comment;
import com.app.edit.domain.commentdeclaration.CommentDeclaration;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.domain.coverletterdeclaration.CoverLetterDeclaration;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Entity
@Table(name = "user_info")
public class UserInfo extends BaseEntity{

    /**
     * 유저 ID
     */
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 유저 이름
     */
    @Column(name = "name", nullable = false, length = 10)
    private String name;

    /**
     * 유저 닉네임
     */
    @Column(name = "nickName", nullable = false, length = 20)
    private String nickName;

    /**
     * 유저 이메일
     */
    @Column(name = "email", nullable = false, length = 60)
    private String email;

    /**
     * 유저 휴대폰 번호
     */
    @Column(name = "phoneNumber", nullable = false, length = 15)
    private String phoneNumber;
    /**
     * 유저 역할
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role",length = 6)
    private UserRole userRole;

    /**
     * 멘토 인증 여부  d
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "isCertificatedMentor", columnDefinition = "varchar(3) default 'NO'")
    private AuthenticationCheck isCertificatedMentor;

//    /**
//     * 이메일 인증 여부
//     */
//    @Enumerated(EnumType.STRING)
//    @Column(name = "isCertificatedEmail", columnDefinition = "varchar(3) default 'NO'")
//    private AuthenticationCheck isCertificatedEmail;

    /**
     * 기타 입력 직군 이름
     */
    @Column(name = "etcJobName", columnDefinition = "varchar(45) default 'NONE'")
    private String etcJobName;

    /**
     * 코인개수
     */
    @Column(name = "coinCount", columnDefinition = "bigint default '0'")
    private Long coinCount;

    /**
     * 탈퇴 사유
     */
    @Column(name = "withdrawal", columnDefinition = "varchar(100) default 'NONE'")
    private String withdrawal;

    /**
     * 회원 상태
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state", columnDefinition = "varchar(10) default 'ACTIVE'")
    private State state;


    @OneToOne(mappedBy = "userInfo",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private MentorInfo mentorInfo;

    @ManyToOne
    @JoinColumn(name = "jobId",nullable = false)
    private Job job;

    @OneToMany(mappedBy = "userInfo",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CertificationRequest> certificationRequestList;

    @OneToMany(mappedBy = "userInfo",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ChangeRoleRequest> changeRoleRequestList;
  
    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<CoverLetter> coverLetters;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<TemporaryCoverLetter> temporaryCoverLetters;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<TemporaryComment> temporaryComments;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<Sympathy> sympathies;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<Appreciate> appreciates;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<CoverLetterDeclaration> coverLetterDeclarations;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private List<CommentDeclaration> commentDeclarations;

    public void addCoverLetter(CoverLetter coverLetter) {
        this.coverLetters.add(coverLetter);
        coverLetter.setUserInfo(this);
    }

    public void addTemporaryCoverLetter(TemporaryCoverLetter temporaryCoverLetter) {
        this.temporaryCoverLetters.add(temporaryCoverLetter);
        temporaryCoverLetter.setUserInfo(this);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setUserInfo(this);
    }

    public void addTemporaryComment(TemporaryComment temporaryComment) {
        this.temporaryComments.add(temporaryComment);
        temporaryComment.setUserInfo(this);
    }

    public void addSympathy(Sympathy sympathy) {
        this.sympathies.add(sympathy);
        sympathy.setUserInfo(this);
    }

    public void addAppreciate(Appreciate appreciate) {
        this.appreciates.add(appreciate);
        appreciate.setUserInfo(this);
    }

    public void addCoverLetterDeclaration(CoverLetterDeclaration coverLetterDeclaration) {
        this.coverLetterDeclarations.add(coverLetterDeclaration);
        coverLetterDeclaration.setUserInfo(this);
    }

    public void addCommentDeclaration(CommentDeclaration commentDeclaration) {
        this.commentDeclarations.add(commentDeclaration);
        commentDeclaration.setUserInfo(this);
    }


}
