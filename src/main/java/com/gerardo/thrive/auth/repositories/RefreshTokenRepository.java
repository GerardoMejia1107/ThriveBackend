package com.gerardo.thrive.auth.repositories;

import com.gerardo.thrive.auth.entities.RefreshTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, Long> {
}
