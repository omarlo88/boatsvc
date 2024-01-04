package ch.challenge.boatsvc.core.api.boat;

import static ch.challenge.boatsvc.core.util.Constants.DB_NAMING_PREFIX;
import static ch.challenge.boatsvc.core.util.Constants.DEFAULT_PK;
import static ch.challenge.boatsvc.core.util.Constants.PK_NAME_SUFFIX;

import ch.challenge.boatsvc.core.common.model.AbstractBaseEntity;
import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = BoatEntity.TBL_NAME)
@AttributeOverride(name = DEFAULT_PK, column = @Column(name = BoatEntity.COLUMN_ID_NAME))
@EntityListeners(AuditingEntityListener.class)
public class BoatEntity extends AbstractBaseEntity {

  private static final long serialVersionUID = -4224495297605902484L;

  static final String TABLE_PREFIX = "BT_";
  static final String TBL_NAME = DB_NAMING_PREFIX + "BOAT";
  static final String COLUMN_PREFIX = DB_NAMING_PREFIX + TABLE_PREFIX;
  static final String COLUMN_ID_NAME = COLUMN_PREFIX + PK_NAME_SUFFIX;


  @Column(name = COLUMN_PREFIX + "NAME", nullable = false, length = 50)
  private String name;

  @Column(name = COLUMN_PREFIX + "DESCRIPTION", nullable = false, length = 4000)
  private String description;

  @Column(name = COLUMN_PREFIX + "PRICE", columnDefinition = "NUMBER(12,2)")
  private BigDecimal price;
}
