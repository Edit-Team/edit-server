package com.app.edit.domain.coverletter;

import com.app.edit.config.BaseEntity;
import com.app.edit.domain.comment.Comment;
import com.app.edit.domain.coverlettercategory.CoverLetterCategory;
import com.app.edit.domain.coverletterdeclaration.CoverLetterDeclaration;
import com.app.edit.domain.sympathy.Sympathy;
import com.app.edit.domain.temporarycomment.TemporaryComment;
import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.CoverLetterType;
import com.app.edit.enums.IsAdopted;
import com.app.edit.enums.State;
import com.app.edit.response.coverletter.GetCoverLettersRes;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.app.edit.config.Constant.*;

@Accessors(chain = true)
@NoArgsConstructor
@DynamicInsert
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
    @ManyToOne(fetch = FetchType.LAZY)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coverLetterCategoryId", nullable = false)
    private CoverLetterCategory coverLetterCategory;

    /*
     * 자소서 타입
     * 작성한(등록한) 자소서 - WRITING, 완성한 자소서 - COMPLETING
     **/
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 15)
    private CoverLetterType type;

    /*
     * 완성한 자소서일 경우, 기존에 작성한 자소서 ID
     **/
    @Column(name = "originalCoverLetterId", updatable = false, columnDefinition = "bigint default 0")
    private Long originalCoverLetterId;

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
                       CoverLetterType type, Long originalCoverLetterId, List<Comment> comments,
                       List<TemporaryComment> temporaryComments, List<CoverLetterDeclaration> coverLetterDeclarations,
                       List<Sympathy> sympathies) {
        this.userInfo = userInfo;
        this.content = content;
        this.state = state;
        this.coverLetterCategory = coverLetterCategory;
        this.type = type;
        this.originalCoverLetterId = originalCoverLetterId;
        this.comments = comments;
        this.temporaryComments = temporaryComments;
        this.coverLetterDeclarations = coverLetterDeclarations;
        this.sympathies = sympathies;
    }

    /*
     * 자소서 -> 자소서 조회 응답 객체로 변환
     * todo: 유저기능 및 직군 종류 데이터 결정되면 jobName, isSympathy 로직 수정할 것.
     **/
    public GetCoverLettersRes toGetCoverLetterRes() {
        Long coverLetterId = this.getId();
        String nickName = this.getUserInfo().getNickName();
        String jobName = this.getUserInfo().getJob().getName();
        String etcJobName = this.getUserInfo().getEtcJobName();
        if (!etcJobName.equals("NONE")) {
            jobName = etcJobName;
        }
        String coverLetterCategoryName = this.getCoverLetterCategory().getName();
        String coverLetterContent = this.getContent();
        boolean isSympathy = DEFAULT_SYMPATHY;
        Long sympathiesCount = DEFAULT_SYMPATHIES_COUNT;
        boolean isMine = DEFAULT_IS_MINE;
        return new GetCoverLettersRes(coverLetterId, nickName, jobName,
                coverLetterCategoryName, coverLetterContent, isSympathy, sympathiesCount, isMine);
    }

    /*
     * 응답에 공감 수 추가
     **/
    public static GetCoverLettersRes setSympathiesCountInCoverLettersRes(GetCoverLettersRes getCoverLettersRes, Long sympathiesCount) {
        getCoverLettersRes.setSympathiesCount(sympathiesCount);
        return getCoverLettersRes;
    }

    /*
     * 채택된 코멘트 존재 여부 확인
     **/
    public static boolean hasAdoptedComment(CoverLetter coverLetter) {
        return coverLetter.getComments().stream()
                .anyMatch(comment -> comment.getIsAdopted().equals(IsAdopted.YES));
    }

    /*
     * 자소서에 달린 코멘트가 채택된 시간 얻어오기
     **/
    public static LocalDateTime getAdoptedTime(CoverLetter coverLetter) {
        return coverLetter.getComments().stream()
                .filter(comment -> comment.getIsAdopted().equals(IsAdopted.YES))
                .findFirst().get().getUpdatedAt();
    }
}
