package com.cput.traabcobusinessplatform.users.repository;

import com.cput.traabcobusinessplatform.users.domain.UserEntity;
import com.cput.traabcobusinessplatform.users.domain.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<UserEntity> findByRole(UserRole role, Pageable pageable);

}
