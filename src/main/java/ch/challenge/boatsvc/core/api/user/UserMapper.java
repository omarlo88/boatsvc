package ch.challenge.boatsvc.core.api.user;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User map(UserEntity bean);

  UserEntity map(User dto);
}
