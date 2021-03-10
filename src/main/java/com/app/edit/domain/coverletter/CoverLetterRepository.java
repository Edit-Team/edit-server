package com.app.edit.domain.coverletter;

import com.app.edit.enums.IsAdopted;
import com.app.edit.enums.State;
import com.app.edit.response.coverletter.GetCoverLettersRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CoverLetterRepository extends JpaRepository<CoverLetter, Long> {

    /*
     * 오늘의 문장 조회 쿼리
     **/
    @Query(value = "select cl from CoverLetter cl where cl.createdAt >= :startOfToday and cl.createdAt < :startOfTomorrow")
    Page<CoverLetter> findCoverLettersOnToday(Pageable pageable, @Param("startOfToday") LocalDateTime startOfToday,
                                              @Param("startOfTomorrow") LocalDateTime startOfTomorrow);

    /*
     * 코멘트를 기다리고 있어요 조회 쿼리
     **/
    @Query(value = "select cl from CoverLetter cl where size(cl.comments) = 0")
    Page<CoverLetter> findCoverLettersHasNotComment(Pageable pageable);

    /*
     * 채택이 완료되었어요 조회 쿼리
     **/
    @Query(value = "select cl from CoverLetter cl where exists(select c from Comment c where c.isAdopted = :isAdopted and c.coverLetter = cl)")
    Page<CoverLetter> findCoverLettersHasAdoptedComment(Pageable pageable, @Param("isAdopted") IsAdopted isAdopted);

    /*
     * 많은 분들이 공감하고 있어요 조회 쿼리
     **/
    @Query(value = "select * " +
            "from cover_letter cl " +
            "where cl.created_At >= :beforeThreeDays " +
            "order by (select count(*) from sympathy s where s.cover_letter_id = cl.id and s.state = :state) desc ",
            nativeQuery = true)
    Page<CoverLetter> findCoverLettersHasManySympathies(Pageable pageable, @Param("beforeThreeDays") LocalDateTime beforeThreeDays, @Param("state") String state);

    /*
     * 내가 등록한 자소서 목록 조회 쿼리
     **/
    @Query(value = "select c from CoverLetter c where c.userInfo.id = :userInfoId and c.state = :state")
    Page<CoverLetter> findMyCoverLetters(Pageable pageable, @Param("userInfoId") Long userInfoId, @Param("state") State state);
}
