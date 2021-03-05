package com.app.edit.domain.certificationRequest;

import com.app.edit.domain.mentor.MentorInfo;
import com.app.edit.domain.user.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface CertificationRequestRepository extends CrudRepository<CertificationRequest,Long> {
}
