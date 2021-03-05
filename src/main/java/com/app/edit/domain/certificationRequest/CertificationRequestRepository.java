package com.app.edit.domain.certificationRequest;

import com.app.edit.domain.mentor.MentorInfo;
import com.app.edit.domain.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CertificationRequestRepository extends JpaRepository<CertificationRequest,Long> {
}
