package rs.webshop.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rs.webshop.domain.User;

@Component
public class JwtTokenUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

  private final TokenBlacklist tokenBlacklist;

  @Value("${app.jwt.secret}")
  private String jwtSecret;

  @Value("${security.jwt.expiration-time}")
  private long jwtExpiration;

  @Value("${security.jwt.refresh-expiration-time}")
  private long refreshExpiration;

  private static final String JWT_ISSUER = "example.io";
  private static final String JWT_REFRESH_ISSUER = "example.io1";

  public JwtTokenUtil(TokenBlacklist tokenBlacklist) {
    this.tokenBlacklist = tokenBlacklist;
  }

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  public String generateAccessToken(User user, List<String> roles) {
    return generateToken(user, roles, JWT_ISSUER, jwtExpiration);
  }

  public String generateRefreshToken(User user, List<String> roles) {
    return generateToken(user, roles, JWT_REFRESH_ISSUER, refreshExpiration);
  }

  private String generateToken(User user, List<String> roles, String issuer,
      long expirationMillis) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("roles", roles);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(user.getId() + "," + user.getUsername())
        .setIssuer(issuer)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
        .signWith(getSigningKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  private Claims extractAllClaims(String token) throws JwtException {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public LocalDateTime getExpirationDate(String token) {
    Date exp = extractClaim(token, Claims::getExpiration);
    return exp != null ? LocalDateTime.ofInstant(exp.toInstant(), java.time.ZoneId.systemDefault())
        : null;
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    try {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
    } catch (JwtException ex) {
      LOGGER.error("JWT claim extraction error: {}", ex.getMessage());
      return null;
    }
  }

  public boolean validateAccessToken(String token) {
    try {
      Claims claims = extractAllClaims(token);
      if (JWT_REFRESH_ISSUER.equals(claims.getIssuer())) {
        throw new UnsupportedJwtException("Refresh token cannot be used as access token");
      }
      return !isBlacklisted(token);
    } catch (JwtException ex) {
      LOGGER.error("JWT access validation error: {}", ex.getMessage());
      return false;
    }
  }

  private boolean isBlacklisted(String token) {
    return tokenBlacklist.isTokenBlacklisted(token);
  }

  public boolean validateRefresh(String token) {
    try {
      Claims claims = extractAllClaims(token);
      if ("example.io".equals(claims.getIssuer())) { // ako je access token, ne može refresh
        throw new UnsupportedJwtException("Access token cannot be used to refresh");
      }
      return !isBlacklisted(token);
    } catch (JwtException ex) {
      LOGGER.error("JWT refresh validation error: {}", ex.getMessage());
      return false;
    }
  }

  public String getUsername(String token) {
    String subject = extractClaim(token, Claims::getSubject);
    return subject != null ? subject.split(",")[1] : null;
  }
}
