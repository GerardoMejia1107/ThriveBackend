package com.gerardo.thrive.auth.mappers;

import com.gerardo.thrive.auth.dtos.request.RefreshTokenCreateDto;
import com.gerardo.thrive.auth.entities.RefreshTokenModel;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {
    public RefreshTokenModel toModel(RefreshTokenCreateDto request) {
        if (request == null) return null;

        RefreshTokenModel model = new RefreshTokenModel();
        model.setFamilyId(request.familyId());
        model.setTokenHash(request.tokenHash());
        model.setUserId(request.user());
        model.setExpiresAt(request.expiresAt());

        return model;
    }
}
