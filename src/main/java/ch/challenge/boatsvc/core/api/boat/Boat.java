package ch.challenge.boatsvc.core.api.boat;

import ch.challenge.boatsvc.core.common.domain.AbstractDomain;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Boat extends AbstractDomain {

  private static final long serialVersionUID = 7326482169800794396L;

  @NotNull
  private String name;
  @NotNull
  private String description;
  private BigDecimal priceDutyFree;
  private BigDecimal totalAmount;
}
