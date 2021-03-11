package com.app.edit.domain.user;

import com.app.edit.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    List<UserInfo> findByState(State state);

    List<UserInfo> findByStateAndEmailIsContaining(State active, String email);
}
