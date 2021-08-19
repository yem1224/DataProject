package com.finda.server.mydata.auth.domain.repository;

import com.finda.server.mydata.auth.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>findByEncryptedCi(String encryptedCi);
}
