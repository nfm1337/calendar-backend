package ru.nfm.calendar.service;

import ru.nfm.calendar.model.RefreshToken;

public interface JwtRefreshTokenService {

    RefreshToken createRefreshToken(int userId);

    RefreshToken verifyExpiration(RefreshToken refreshToken);
}
