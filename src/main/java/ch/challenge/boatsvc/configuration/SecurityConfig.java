package ch.challenge.boatsvc.configuration;

import static ch.challenge.boatsvc.configuration.WebAppCustomDsl.customDsl;

import ch.challenge.boatsvc.configuration.properties.JwtPropertiesConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@EnableConfigurationProperties({JwtPropertiesConfig.class})
public class SecurityConfig {

  private static final String[] AUTH_WHITELIST = {
      // -- Login page spring
      "/boatsvc/api/login",
      // --refresh-token
      "/api/token",
      // -- h2 path
      "/h2/**",
      // other public endpoints of your API may be appended to this array
  };

  private final JwtPropertiesConfig jwtPropertiesConfig;
  private final PasswordEncoder passwordEncoder;
  private final CustomUserDetailsService customUserDetailsService;

  public SecurityConfig(JwtPropertiesConfig jwtPropertiesConfig,
      PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService) {
    this.jwtPropertiesConfig = jwtPropertiesConfig;
    this.passwordEncoder = passwordEncoder;
    this.customUserDetailsService = customUserDetailsService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http //
        .apply(customDsl())
        .jwtPropertiesConfig(this.jwtPropertiesConfig)
        .passwordEncoder(this.passwordEncoder)
        .customUserDetailsService(this.customUserDetailsService);

    http //
        .authorizeRequests()
        .antMatchers(AUTH_WHITELIST).permitAll()
        .anyRequest().authenticated();

    return http.build();
  }

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().antMatchers(AUTH_WHITELIST);
  }
}
