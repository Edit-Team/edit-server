package com.app.edit.domain.sympathy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SympathyRepository extends JpaRepository<Sympathy, SympathyId> {
}
