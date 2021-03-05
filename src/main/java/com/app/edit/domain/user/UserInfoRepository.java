package com.app.edit.domain.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserInfoRepository extends CrudRepository<UserInfo,Long> {
}
