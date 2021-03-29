package com.app.edit.domain.appreciate;

import com.app.edit.domain.user.UserInfo;
import com.app.edit.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppreciateRepository extends JpaRepository<Appreciate, AppreciateId> {

    Optional<Appreciate> findByUserInfoAndState(UserInfo userInfo, State state);
}
