package com.app.edit.domain.comment;

import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /*
     * 자소서에 달린 코멘트 조회 쿼리
     **/
    @Query(value = "select c from Comment c where c.coverLetter = :coverLetter and c.state = :state order by c.createdAt desc")
    Page<Comment> findCommentsByCoverLetter(Pageable pageable, @Param("coverLetter") CoverLetter coverLetter, @Param("state") State state);
}
