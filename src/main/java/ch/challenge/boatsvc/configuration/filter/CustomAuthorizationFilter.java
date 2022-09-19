package ch.challenge.boatsvc.configuration.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import ch.challenge.boatsvc.configuration.properties.JwtPropertiesConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthorizationFilter.class);

  private final JwtPropertiesConfig jwtPropertiesConfig;

  public CustomAuthorizationFilter(JwtPropertiesConfig jwtPropertiesConfig) {
    this.jwtPropertiesConfig = jwtPropertiesConfig;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    if (request.getMethod().equals("OPTIONS")) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      final String requestURI = request.getRequestURI();
      if (requestURI.equals("/boatsvc/api/login") || requestURI.equals("/boatsvc/api/token")) {
        filterChain.doFilter(request, response);
        return;
      } else {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith(
            this.jwtPropertiesConfig.tokenPrefix())) {
          filterChain.doFilter(request, response);
          return;
        }

        String token = authorizationHeader.substring(
            this.jwtPropertiesConfig.tokenPrefix().length());

        try {
          JWTVerifier verifier = JWT.require(
              Algorithm.HMAC256(this.jwtPropertiesConfig.secretKey().getBytes())).build();
          DecodedJWT decodedJWT = verifier.verify(token);
          String username = decodedJWT.getSubject();
          final List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
          Collection<GrantedAuthority> authorities = new ArrayList<>();
          roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              username, null, authorities);
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception ex) {
          final String localizedMessage = ex.getLocalizedMessage();
          LOGGER.error("Error from verify token {} :", localizedMessage);
          response.setHeader("error", localizedMessage);
          response.setStatus(FORBIDDEN.value());
          Map<String, String> error = new HashMap<>();
          error.put("error_message", localizedMessage);
          response.setContentType(APPLICATION_JSON_VALUE);
          new ObjectMapper().writeValue(response.getOutputStream(), error);
        }

        filterChain.doFilter(request, response);
      }
    }
  }
}
