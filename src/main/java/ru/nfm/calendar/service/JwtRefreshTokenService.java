package ru.nfm.calendar.service;

import ru.nfm.calendar.model.RefreshToken;
import ru.nfm.calendar.model.User;

public interface JwtRefreshTokenService {
    RefreshToken createRefreshToken(User user);
    RefreshToken verifyExpiration(RefreshToken refreshToken);
}
