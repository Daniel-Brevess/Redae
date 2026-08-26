package br.com.redae.auth.config;

import br.com.redae.identity.entity.User;
import br.com.redae.identity.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final ObjectProvider<UserRepository> userRepository;

  public JwtAuthenticationFilter(JwtService jwtService, ObjectProvider<UserRepository> userRepository) {
    this.jwtService = jwtService;
    this.userRepository = userRepository;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);
      if (jwtService.isValid(token)) {
        try {
          UserRepository repository = userRepository.getIfAvailable();
          User user = repository == null ? null : repository.findById(jwtService.extractUserId(token)).orElse(null);
          if (user != null) {
            var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        } catch (IllegalArgumentException ignored) {
          SecurityContextHolder.clearContext();
        }
      }
    }
    filterChain.doFilter(request, response);
  }
}
