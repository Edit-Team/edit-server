package com.app.edit.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
}
