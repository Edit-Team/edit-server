package com.app.edit.domain.appreciate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppreciateRepository extends JpaRepository<Appreciate, AppreciateId> {
}
