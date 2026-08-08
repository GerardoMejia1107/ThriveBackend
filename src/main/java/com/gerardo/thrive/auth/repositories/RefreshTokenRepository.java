package com.gerardo.thrive.auth.repositories;

import com.gerardo.thrive.auth.entities.RefreshTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, Long> {
    Optional<RefreshTokenModel> findByTokenHash(String tokenHash);

    List<RefreshTokenModel> findAllByFamilyId(UUID familyId);
}
