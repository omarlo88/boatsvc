package ch.challenge.boatsvc.core.api.user;

import java.util.Optional;

public interface UserRepositoryCustom {

  /**
   * Find an user by its userName
   *
   * @param userName
   * @return
   */
  Optional<UserEntity> findByUsername(final String userName);
}
