package ch.challenge.boatsvc.core.api.boat;

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
public class Boat {

  private static final long serialVersionUID = 7326482169800794396L;

  private Long id;
  @NotNull
  private String name;
  @NotNull
  private String description;
  private BigDecimal priceDutyFree;
  private BigDecimal totalAmount;


}
