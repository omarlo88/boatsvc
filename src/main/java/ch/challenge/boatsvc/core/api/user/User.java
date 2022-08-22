package ch.challenge.boatsvc.core.api.user;

import ch.challenge.boatsvc.core.common.domain.AbstractBaseDomain;
import ch.challenge.boatsvc.core.jsonview.View.UserDetail;
import ch.challenge.boatsvc.core.jsonview.View.UserList;
import com.fasterxml.jackson.annotation.JsonView;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User extends AbstractBaseDomain {

  private static final long serialVersionUID = -7591050560123548562L;

  @JsonView({UserList.class, UserDetail.class})
  private Long id;

  @JsonView({UserList.class, UserDetail.class})
  @NotNull
  @Size(min = 3, max = 25, message
      = "Username must be between 3 and 25 characters")
  private String username;

  @Exclude
  @NotEmpty
  private String password;

  @JsonView({UserList.class, UserDetail.class})
  @Email(message = "Email should be valid")
  private String email;

  @JsonView({UserList.class, UserDetail.class})
  private String firstName;

  @JsonView({UserList.class, UserDetail.class})
  private String lastName;

  @JsonView({UserList.class, UserDetail.class})
  @NotNull
  private EnumRole role = EnumRole.USER;

  @JsonView({UserList.class, UserDetail.class})
  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }
}
