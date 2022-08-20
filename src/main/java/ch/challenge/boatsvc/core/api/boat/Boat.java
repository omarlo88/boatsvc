package ch.challenge.boatsvc.core.api.boat;

import ch.challenge.boatsvc.core.common.domain.AbstractBaseDomain;
import ch.challenge.boatsvc.core.jsonview.View.BoatDetail;
import ch.challenge.boatsvc.core.jsonview.View.BoatList;
import com.fasterxml.jackson.annotation.JsonView;
import java.math.BigDecimal;
import javax.validation.constraints.NotEmpty;
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
public class Boat extends AbstractBaseDomain {

  private static final long serialVersionUID = 7326482169800794396L;

  @JsonView({BoatList.class, BoatDetail.class})
  private Long id;

  @JsonView({BoatList.class, BoatDetail.class})
  @NotEmpty
  private String name;

  @JsonView({BoatDetail.class})
  @NotEmpty
  private String description;

  @JsonView({BoatDetail.class})
  private BigDecimal priceDutyFree;

  @JsonView({BoatList.class, BoatDetail.class})
  private BigDecimal totalAmount;


}
