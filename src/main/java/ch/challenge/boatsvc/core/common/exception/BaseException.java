package ch.challenge.boatsvc.core.common.exception;

public class BaseException extends Exception {

  private static final long serialVersionUID = 6913392932768884108L;

  public BaseException(final String message) {
    super(message);
  }
}
