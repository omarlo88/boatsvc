package ch.challenge.boatsvc.configuration.properties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "jwt")
@Validated
/*@ConstructorBinding // not need setters
public class JwtPropertiesConfig {

  @NotEmpty
  private final String secretKey;
  @NotEmpty
  private final String tokenPrefix;
  @NotNull
  private final Integer tokenExpirationAfterDays;
  @NotNull
  private final Integer tokenRefreshExpirationAfterDays;

  public JwtPropertiesConfig(String secretKey, String tokenPrefix, Integer tokenExpirationAfterDays,
      Integer tokenRefreshExpirationAfterDays) {
    this.secretKey = secretKey;
    this.tokenPrefix = tokenPrefix;
    this.tokenExpirationAfterDays = tokenExpirationAfterDays;
    this.tokenRefreshExpirationAfterDays = tokenRefreshExpirationAfterDays;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public String getTokenPrefix() {
    return tokenPrefix;
  }

  public Integer getTokenExpirationAfterDays() {
    return tokenExpirationAfterDays;
  }

  public Integer getTokenRefreshExpirationAfterDays() {
    return tokenRefreshExpirationAfterDays;
  }

  *//*public JwtPropertiesConfig() {
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
  }*//*
}*/

public record JwtPropertiesConfig(@NotEmpty String secretKey, @NotEmpty String tokenPrefix,
                                  @NotNull Integer tokenExpirationAfterDays,
                                  @NotNull Integer tokenRefreshExpirationAfterDays) {

}
