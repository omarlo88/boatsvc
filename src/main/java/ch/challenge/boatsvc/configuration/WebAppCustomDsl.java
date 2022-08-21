package ch.challenge.boatsvc.configuration;

import ch.challenge.boatsvc.configuration.filter.CustomAuthenticationFilter;
import ch.challenge.boatsvc.configuration.filter.CustomAuthorizationFilter;
import ch.challenge.boatsvc.configuration.properties.JwtPropertiesConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

public class WebAppCustomDsl extends AbstractHttpConfigurer<WebAppCustomDsl, HttpSecurity> {

  private JwtPropertiesConfig jwtPropertiesConfig;
  private PasswordEncoder passwordEncoder;
  private CustomUserDetailsService customUserDetailsService;

  @Override
  public void init(HttpSecurity http) throws Exception {
    // Enable CORS
    http.cors();

    // Disable CSRF protection
    http.csrf().disable();

    // No session created in the serveur
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Block XSS-Protection
    http.headers().xssProtection().block(false);

    // Spring Security doesn’t add Referrer Policy header by default
    http.headers().referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(
        authenticationManager, this.jwtPropertiesConfig);
    customAuthenticationFilter.setFilterProcessesUrl("/api/login"); // Override login path
    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(new CustomAuthorizationFilter(this.jwtPropertiesConfig),
        UsernamePasswordAuthenticationFilter.class);
  }

  public WebAppCustomDsl jwtPropertiesConfig(JwtPropertiesConfig value) {
    this.jwtPropertiesConfig = value;
    return this;
  }

  public WebAppCustomDsl passwordEncoder(PasswordEncoder value) {
    this.passwordEncoder = value;
    return this;
  }

  public WebAppCustomDsl customUserDetailsService(CustomUserDetailsService value) {
    this.customUserDetailsService = value;
    return this;
  }

  public static WebAppCustomDsl customDsl() {
    return new WebAppCustomDsl();
  }
}
