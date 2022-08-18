package ch.challenge.boatsvc.core.common.model;

import ch.challenge.boatsvc.core.util.Constants;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractBaseEntity implements Serializable {

  private static final long serialVersionUID = -7045255715671214736L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = Constants.DEFAULT_PK)
  private Long id;

}
