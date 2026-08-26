package br.com.redae.auth.config;

import br.com.redae.identity.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  private final SecretKey signingKey;
  private final long accessTokenSeconds;

  public JwtService(
      @Value("${security.jwt.secret}") String secret,
      @Value("${security.jwt.access-token-minutes:15}") long accessTokenMinutes) {
    if (secret.length() < 32) {
      throw new IllegalArgumentException("JWT secret must have at least 32 characters");
    }
    signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    accessTokenSeconds = accessTokenMinutes * 60;
  }

  public String createAccessToken(User user) {
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(user.getId().toString())
        .claim("role", user.getRole().name())
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusSeconds(accessTokenSeconds)))
        .signWith(signingKey)
        .compact();
  }

  public UUID extractUserId(String token) {
    return UUID.fromString(parse(token).getSubject());
  }

  public boolean isValid(String token) {
    try {
      parse(token);
      return true;
    } catch (RuntimeException exception) {
      return false;
    }
  }

  public long getAccessTokenSeconds() {
    return accessTokenSeconds;
  }

  private Claims parse(String token) {
    return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
  }
}
