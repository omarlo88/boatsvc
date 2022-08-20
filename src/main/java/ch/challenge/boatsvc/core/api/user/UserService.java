package ch.challenge.boatsvc.core.api.user;

import ch.challenge.boatsvc.core.common.exception.BaseException;
import ch.challenge.boatsvc.core.common.service.GenericService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService implements GenericService<Long, UserEntity, User>, UserRepositoryCustom {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Override
  public List<User> toDto(List<UserEntity> entities) {
    if (Objects.isNull(entities) || entities.isEmpty()) {
      return new ArrayList<>();
    }

    return entities.stream().filter(Objects::nonNull).map(this::toDto).toList();
  }

  @Override
  public User toDto(UserEntity bean) {
    return Objects.nonNull(bean) ? this.userMapper.map(bean) : null;
  }

  @Override
  public UserEntity fromDto(User dto) {
    return Objects.nonNull(dto) ? this.userMapper.map(dto) : null;
  }

  @Override
  public boolean exists(Long id) {
    return id != null && this.userRepository.existsById(id);
  }

  @Override
  public boolean exists(User dto) {
    final Long id = dto.getId();
    return id != null && this.exists(id);
  }

  @Override
  public List<User> findAll() {
    return this.toDto(this.userRepository.findAll());
  }

  @Override
  public User create(User dto) throws BaseException {
    final UserEntity object = this.createEntity(dto);
    final UserEntity createdObject = this.userRepository.save(object);
    return this.toDto(createdObject);
  }

  @Override
  public List<User> createAll(@NotNull List<User> dtos) throws BaseException {
    List<UserEntity> entities = new ArrayList<>();
    for (User dto : dtos) {
      UserEntity object = this.createEntity(dto);
      entities.add(object);
    }

    entities = this.userRepository.saveAll(entities);

    return entities.stream().map(this::toDto).toList();
  }

  @Override
  public UserEntity findById(@NotNull final Long id) {
    return this.userRepository.findById(id).orElse(null);
  }

  @Override
  public User findOne(@NotNull final Long id) {
    final UserEntity bean = this.findById(id);
    return this.toDto(bean);
  }

  @Override
  public User delete(@NotNull final Long id) throws BaseException {
    this.verifyBeforeDelete(id);
    final UserEntity bean = this.findById(id);
    this.userRepository.deleteById(id);
    return this.toDto(bean);
  }

  @Override
  public User update(@NotNull User dto) throws BaseException {
    final UserEntity object = this.updateEntity(dto);
    final UserEntity updatedObject = this.userRepository.save(object);
    return this.toDto(updatedObject);
  }

  @Override
  public Optional<UserEntity> findByUsername(String userName) {
    return Optional.empty();
  }

  // Private methods /////////////////////////////////////////////////////////////////////////////
  private UserEntity createEntity(@NotNull User dto) throws BaseException {
    final UserEntity object = this.fromDto(dto);
    final Long id = object.getId();
    if (Objects.nonNull(id)) {
      final String msg = "Impossible to create object of type %s : id %s should be null";
      final String error = String.format(msg, object.getClass().getName(), id);
      LOGGER.error(error);
      throw new BaseException(error);
    }

    return object;
  }

  private UserEntity updateEntity(@NotNull User dto) throws BaseException {
    final UserEntity object = this.fromDto(dto);
    if (!this.exists(dto)) {
      final String msg = "Impossible to update object of type %s : no object found in database.";
      final String error = String.format(msg, object.getClass().toString());
      LOGGER.error(error);
      throw new BaseException(error);
    }
    return object;
  }

  private void verifyBeforeDelete(@NotNull Long id) throws BaseException {
    if (!this.exists(id)) {
      final String msg = "Impossible to delete object with id %s: No existing object.";
      final String error = String.format(msg, id);
      LOGGER.error(error);
      throw new BaseException(error);
    }
  }
}
