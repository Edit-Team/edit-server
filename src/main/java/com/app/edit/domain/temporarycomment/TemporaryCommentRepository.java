package com.app.edit.domain.temporarycomment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporaryCommentRepository extends JpaRepository<TemporaryComment, Long> {
}
