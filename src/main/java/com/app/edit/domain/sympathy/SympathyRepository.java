package com.app.edit.domain.sympathy;

import com.app.edit.config.PageRequest;
import com.app.edit.domain.coverletter.CoverLetter;
import com.app.edit.enums.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SympathyRepository extends JpaRepository<Sympathy, SympathyId> {

    Long countSympathiesByCoverLetterAndState(CoverLetter coverLetter, State state);

    //List<GetSympathizeCoverLettersRes> MySympathiesByCoverLetter();

//    @Query(value = "select s from Sympathy s " +
//            "join fetch s.userInfo u join fetch CoverLetter c " +
//            "on s.userInfo.id = u.id and s.coverLetter.id = c.id group by u.id, c.id")
//    List<Sympathy> findBYUserAndCoverLetter();

    @Query(value = "select s from Sympathy s " +
            "where s.userInfo.id = :userInfoId and s.userInfo.state = :state " +
            "order by s.createdAt DESC"    )
    Page<Sympathy> findCoverLetterByUser(Pageable pageRequest, @Param("userInfoId") Long userInfoId, @Param("state") State state);
}
