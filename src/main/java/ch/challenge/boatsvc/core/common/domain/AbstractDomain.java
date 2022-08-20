package ch.challenge.boatsvc.core.common.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractDomain<T> implements Serializable {

  private static final long serialVersionUID = -5184289651018484946L;

  protected T id;
}
