package ch.challenge.boatsvc.configuration.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import ch.challenge.boatsvc.configuration.properties.JwtPropertiesConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

  private final AuthenticationManager authenticationManager;
  private final JwtPropertiesConfig jwtPropertiesConfig;

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
      JwtPropertiesConfig jwtPropertiesConfig) {
    this.authenticationManager = authenticationManager;
    this.jwtPropertiesConfig = jwtPropertiesConfig;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    String username = request.getParameter(SPRING_SECURITY_FORM_USERNAME_KEY);
    String password = request.getParameter(SPRING_SECURITY_FORM_PASSWORD_KEY);
    LOGGER.info("Username {} is found in the request !!", username);
    return this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
    );
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authentication) throws IOException, ServletException {
    User userSpring = (User) authentication.getPrincipal();

    final String username = userSpring.getUsername();
    String access_token = JWT.create()
        .withSubject(username)
        .withExpiresAt(LocalDate.now()
            .plusDays(this.jwtPropertiesConfig.getTokenExpirationAfterDays())
            .atStartOfDay().atZone(ZoneId.systemDefault())
            .toInstant()) // Using Instant of java time instead of Date java util
//        .withExpiresAt(java.sql.Date.valueOf(
//            LocalDate.now().plusDays(this.jwtPropertiesConfig.getTokenExpirationAfterDays())))
//        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // For testing
        .withIssuer(request.getRequestURL().toString())
        .withClaim("roles",
            userSpring.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
        .sign(Algorithm.HMAC256(this.jwtPropertiesConfig.getSecretKey().getBytes()));

    String refresh_token = JWT.create()
        .withSubject(username)
        .withExpiresAt(LocalDate.now()
            .plusDays(this.jwtPropertiesConfig.getTokenRefreshExpirationAfterDays())
            .atStartOfDay().atZone(ZoneId.systemDefault())
            .toInstant()) // Using Instant of java time instead of Date java util
//        .withExpiresAt(java.sql.Date.valueOf(LocalDate.now()
//            .plusDays(this.jwtPropertiesConfig.getTokenRefreshExpirationAfterDays())))
        .withIssuer(request.getRequestURL().toString())
        .sign(Algorithm.HMAC256(this.jwtPropertiesConfig.getSecretKey().getBytes()));

//    response.setHeader("access_token", access_token);
//    response.setHeader("refresh_token", refresh_token);
    Map<String, String> tokens = new HashMap<>();
    tokens.put("accessToken", access_token);
    tokens.put("refreshToken", refresh_token);
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
  }
}
