package ch.challenge.boatsvc.configuration.properties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "jwt")
@Validated
public class JwtPropertiesConfig {

  @NotEmpty
  private String secretKey;
  @NotEmpty
  private String tokenPrefix;
  @NotNull
  private Integer tokenExpirationAfterDays;
  @NotNull
  private Integer tokenRefreshExpirationAfterDays;

  public JwtPropertiesConfig() {
    super();
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getTokenPrefix() {
    return tokenPrefix;
  }

  public void setTokenPrefix(String tokenPrefix) {
    this.tokenPrefix = tokenPrefix;
  }

  public Integer getTokenExpirationAfterDays() {
    return tokenExpirationAfterDays;
  }

  public void setTokenExpirationAfterDays(Integer tokenExpirationAfterDays) {
    this.tokenExpirationAfterDays = tokenExpirationAfterDays;
  }

  public Integer getTokenRefreshExpirationAfterDays() {
    return tokenRefreshExpirationAfterDays;
  }

  public void setTokenRefreshExpirationAfterDays(Integer tokenRefreshExpirationAfterDays) {
    this.tokenRefreshExpirationAfterDays = tokenRefreshExpirationAfterDays;
  }
}
