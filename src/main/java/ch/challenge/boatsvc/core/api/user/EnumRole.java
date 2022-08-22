package ch.challenge.boatsvc.core.api.user;

public enum EnumRole {
  /**
   * ANONYMOUS
   */
  ANONYMOUS(Values.VAL_ANONYMOUS),

  /**
   * USER.
   */
  USER(Values.VAL_USER),

  /**
   * Admin.
   */
  ADMIN(Values.VAL_ADMIN);

  /**
   * @param roleCode role code with prefix Spring Security
   */
  EnumRole(String roleCode) {
    if (!this.getRoleCode().equals(roleCode)) {
      final String msgError =
          String.format("Role value %s must be equals to name %s", roleCode, this.getRoleCode());
      throw new RuntimeException(msgError);
    }
  }

  /**
   * Prefix of role attribute Spring Security.
   */
  private static final String ROLE_PREFIX = "ROLE_";

  /**
   * @return Code role for Spring-Security
   */
  public String getRoleCode() {
    return ROLE_PREFIX + this.name();
  }

  /**
   * Enum values. Inner class that expose roles values used into annotations.
   */
  public static class Values {

    /**
     * Anonymous.
     */
    public static final String VAL_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * User.
     */
    public static final String VAL_USER = "ROLE_USER";

    /**
     * Admin.
     */
    public static final String VAL_ADMIN = "ROLE_ADMIN";

    /**
     * Private constructor for static class.
     */
    private Values() {
      super();
    }

  }
}
