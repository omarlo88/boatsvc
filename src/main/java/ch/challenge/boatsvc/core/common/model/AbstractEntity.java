package ch.challenge.boatsvc.core.common.model;

import java.io.Serializable;

public abstract class AbstractEntity<T> implements Serializable {

  private static final long serialVersionUID = 6395419488224653090L;

  public abstract T getId();

  public abstract void setId(T id);
}
