package ch.challenge.boatsvc.core.common.domain;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractDomain implements Serializable {

  private static final long serialVersionUID = -5184289651018484946L;

  private Long id;
}
