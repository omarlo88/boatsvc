package ch.challenge.boatsvc.configuration.properties;

import java.io.Serializable;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtPropertiesConfig implements Serializable {

  private static final long serialVersionUID = -245817719390402134L;

  private String secretKey;
  private String tokenPrefix;
  private Integer tokenExpirationAfterDays;
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
