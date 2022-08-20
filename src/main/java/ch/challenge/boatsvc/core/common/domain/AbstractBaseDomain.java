package ch.challenge.boatsvc.core.common.domain;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public abstract class AbstractBaseDomain extends AbstractDomain<Long> {

  private static final long serialVersionUID = 3254689334757864931L;

  /**
   * Custom constructor
   *
   * @param id
   */
  protected AbstractBaseDomain(Long id) {
    super(id);
  }
}
