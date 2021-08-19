package com.finda.server.mydata.auth.domain.repository;

import com.finda.server.mydata.auth.domain.entity.StateSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface StateSessionRepository extends JpaRepository<StateSessionEntity, String> {
}
