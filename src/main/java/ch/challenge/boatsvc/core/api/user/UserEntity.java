package ch.challenge.boatsvc.core.api.user;

import static ch.challenge.boatsvc.core.util.Constants.DB_NAMING_PREFIX;
import static ch.challenge.boatsvc.core.util.Constants.DEFAULT_PK;
import static ch.challenge.boatsvc.core.util.Constants.PK_NAME_SUFFIX;

import ch.challenge.boatsvc.core.common.model.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@AttributeOverride(name = DEFAULT_PK, column = @Column(name = UserEntity.COLUMN_ID_NAME))
@Table(name = UserEntity.TBL_NAME, uniqueConstraints = {
    @UniqueConstraint(name = "UK_CHA_USER_USERNAME", columnNames = {"CHA_USR_USERNAME"}),//
    @UniqueConstraint(name = "UK_CHA_USER_EMAIL", columnNames = {"CHA_USR_EMAIL"}) //
})
public class UserEntity extends AbstractBaseEntity {

  private static final long serialVersionUID = -3104072190082206904L;

  static final String TABLE_PREFIX = "USR_";
  static final String TBL_NAME = DB_NAMING_PREFIX + "USER";
  static final String COLUMN_PREFIX = DB_NAMING_PREFIX + TABLE_PREFIX;
  static final String COLUMN_ID_NAME = COLUMN_PREFIX + PK_NAME_SUFFIX;

  @Column(name = COLUMN_PREFIX + "USERNAME", nullable = false)
  private String username;

  @JsonIgnore
  @Column(name = COLUMN_PREFIX + "PASSWORD", nullable = false)
  private String password;

  @Column(name = COLUMN_PREFIX + "EMAIL", nullable = false)
  private String email;

  @Column(name = COLUMN_PREFIX + "FIRSTNAME")
  private String firstName;

  @Column(name = COLUMN_PREFIX + "LASTNAME")
  private String lastName;

  @Column(name = COLUMN_PREFIX + "ROLE", nullable = false, unique = true)
  @Enumerated(EnumType.STRING)
  private EnumRole role = EnumRole.USER;
}
