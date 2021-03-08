package com.app.edit.domain.coverletter;

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
}
