package ch.challenge.boatsvc.core.api.boat;

import java.math.BigDecimal;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BoatMapper {

  @Mapping(target = "priceDutyFree", source = "price")
  @Mapping(target = "totalAmount", source = "bean")
  Boat map(BoatEntity bean);

  @Mapping(target = "price", source = "priceDutyFree")
  BoatEntity map(Boat dto);

  default BigDecimal toTotalAmount(BoatEntity bean) {
    if (Objects.isNull(bean)) {
      return BigDecimal.ZERO;
    }

    final BigDecimal price = bean.getPrice();
    return
        Objects.nonNull(price) ? BigDecimal.valueOf(price.doubleValue() * 1.077) : BigDecimal.ZERO;
  }
}
