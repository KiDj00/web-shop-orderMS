package rs.webshop.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

  @Value("${app.jwt.secret}")
  private String secretKey;

  private final JwtDecoder keycloakJwtDecoder;

  public JwtTokenFilter(JwtDecoder keycloakJwtDecoder) {
    this.keycloakJwtDecoder = keycloakJwtDecoder;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = header.substring(7);

    // Pokusaj za HS512 token
    try {
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
          .build()
          .parseClaimsJws(token)
          .getBody();

      String username = claims.getSubject();
      List<String> roles = claims.get("roles", List.class);

      if (roles != null) {
        var authorities = roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
        var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (JwtException ex) {
      // Ako HS512 ne radi → pokusaj Keycloak RS256
      try {
        Jwt jwt = keycloakJwtDecoder.decode(token);

        String username = jwt.getClaimAsString("sub_custom");
        List<String> roles = jwt.getClaimAsStringList("roles");

        if (roles != null) {
          var authorities = roles.stream()
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());
          var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
          SecurityContextHolder.getContext().setAuthentication(auth);
        }

      } catch (org.springframework.security.oauth2.jwt.JwtException e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }
}
