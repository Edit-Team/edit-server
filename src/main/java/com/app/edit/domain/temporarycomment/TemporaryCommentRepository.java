package com.app.edit.domain.temporarycomment;

import com.app.edit.enums.State;
import com.app.edit.response.comment.GetMyCommentRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemporaryCommentRepository extends JpaRepository<TemporaryComment, Long> {

    /**
     * 내 임시 코멘트 조회
     * @return
     */
    @Query(value = "select new com.app.edit.response.comment.GetMyCommentRes(c.id, c.sentenceEvaluation, c.concretenessLogic, c.sincerity, c.activity,c.content) " +
            "from TemporaryComment c where c.userInfo.id = :userInfoId and c.state = :state order by c.createdAt desc")
    Optional<GetMyCommentRes> findMyTemporaryComment(@Param("userInfoId") Long userInfoId,@Param("state") State state);

    /**
     * ID로 임시 코멘트 조회
     * @param temporaryCommentId
     * @param state
     * @return
     */
    Optional<TemporaryComment> findByIdAndState(Long temporaryCommentId, State state);
}
