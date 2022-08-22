package ch.challenge.boatsvc.core.api.user;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class RefreshTokenController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;
  private final JwtPropertiesConfig jwtPropertiesConfig;

  public RefreshTokenController(UserService userService, JwtPropertiesConfig jwtPropertiesConfig) {
    this.userService = userService;
    this.jwtPropertiesConfig = jwtPropertiesConfig;
  }

  @GetMapping
  public ResponseEntity<Void> refrestToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String authorizationHeader = request.getHeader(AUTHORIZATION);
    if (authorizationHeader == null || !authorizationHeader.startsWith(
        this.jwtPropertiesConfig.getTokenPrefix())) {
      LOGGER.debug("[refrestToken] method: Token is missing !!");
      throw new RuntimeException("");
    }

    String refresh_token = authorizationHeader.substring(
        this.jwtPropertiesConfig.getTokenPrefix().length());

    try {
      JWTVerifier verifier = JWT.require(
          Algorithm.HMAC256(this.jwtPropertiesConfig.getSecretKey().getBytes())).build();
      DecodedJWT decodedJWT = verifier.verify(refresh_token);
      String username = decodedJWT.getSubject();
      this.userService.getUserByUsername(username).map(user -> {
            final String userUsername = user.getUsername();
            String access_token = JWT.create()
                .withSubject(userUsername)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", List.of(user.getRole().getRoleCode()))
                .sign(Algorithm.HMAC256(this.jwtPropertiesConfig.getSecretKey().getBytes()));

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", access_token);
            tokens.put("refreshToken", refresh_token);
            response.setContentType(APPLICATION_JSON_VALUE);
            try {
              new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (IOException e) {
              LOGGER.error("Error from reflesh token {} :", e.getLocalizedMessage());
              throw new RuntimeException(e);
            }
            return user;
          })
          .orElseThrow(RuntimeException::new);

    } catch (Exception ex) {
      final String localizedMessage = ex.getLocalizedMessage();
      LOGGER.error("Error from verify refresh token {} :", localizedMessage);
      response.setHeader("error", localizedMessage);
      response.setStatus(FORBIDDEN.value());
      Map<String, String> error = new HashMap<>();
      error.put("error_message", localizedMessage);
      response.setContentType(APPLICATION_JSON_VALUE);
      new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    return ResponseEntity.noContent().build();
  }
}
