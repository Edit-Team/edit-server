package com.app.edit.domain.userprofile;

import com.app.edit.domain.user.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileRepository extends CrudRepository<UserProfile, UserInfo> {
}
