package ch.challenge.boatsvc.core.api.boat;

import ch.challenge.boatsvc.core.common.exception.BaseException;
import ch.challenge.boatsvc.core.common.service.GenericService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BoatService implements GenericService<Long, BoatEntity, Boat> {

  private static final Logger LOGGER = LoggerFactory.getLogger(BoatService.class);

  private final BoatRepository boatRepository;
  private final BoatMapper boatMapper;

  public BoatService(BoatRepository boatRepository, BoatMapper boatMapper) {
    this.boatRepository = boatRepository;
    this.boatMapper = boatMapper;
  }

  @Override
  public List<Boat> toDto(List<BoatEntity> entities) {
    if (Objects.isNull(entities) || entities.isEmpty()) {
      return new ArrayList<>();
    }

    return entities.stream().filter(Objects::nonNull).map(this::toDto).toList();
  }

  @Override
  public Boat toDto(BoatEntity bean) {
    return Objects.nonNull(bean) ? this.boatMapper.map(bean) : null;
  }

  @Override
  public BoatEntity fromDto(Boat dto) {
    return Objects.nonNull(dto) ? this.boatMapper.map(dto) : null;
  }

  @Override
  public boolean exists(Long id) {
    return id != null && this.boatRepository.existsById(id);
  }

  @Override
  public boolean exists(Boat dto) {
    final Long id = dto.getId();
    return id != null && this.exists(id);
  }

  @Override
  public List<Boat> findAll() {
    return this.toDto(this.boatRepository.findAll());
  }

  @Override
  public Boat create(@NotNull Boat dto) throws BaseException {
    final BoatEntity object = this.createEntity(dto);
    final BoatEntity createdObject = this.boatRepository.saveAndFlush(object);
    return this.toDto(createdObject);
  }

  @Override
  public List<Boat> createAll(@NotNull List<Boat> dtos) throws BaseException {
    List<BoatEntity> entities = new ArrayList<>();
    for (Boat dto : dtos) {
      BoatEntity object = this.createEntity(dto);
      entities.add(object);
    }

    entities = this.boatRepository.saveAllAndFlush(entities);

    return entities.stream().map(this::toDto).toList();
  }

  @Override
  public BoatEntity findById(@NotNull final Long id) {
    return this.boatRepository.findById(id).orElse(null);
  }

  @Override
  public Boat findOne(@NotNull final Long id) {
    final BoatEntity bean = this.findById(id);
    return this.toDto(bean);
  }

  @Override
  public Boat delete(@NotNull final Long id) throws BaseException {
    this.verifyBeforeDelete(id);
    final BoatEntity bean = this.findById(id);
    this.boatRepository.deleteById(id);
    return this.toDto(bean);
  }

  @Override
  public Boat update(@NotNull Boat dto) throws BaseException {
    final BoatEntity object = this.updateEntity(dto);
    final BoatEntity updatedObject = this.boatRepository.save(object);
    return this.toDto(updatedObject);
  }

  // Private methods /////////////////////////////////////////////////////////////////////////////
  private BoatEntity createEntity(@NotNull Boat dto) throws BaseException {
    final BoatEntity object = this.fromDto(dto);
    final Long id = object.getId();
    if (Objects.nonNull(id)) {
      final String msg = "Impossible to create object of type %s : id %s should be null";
      final String error = String.format(msg, object.getClass().getName(), id);
      LOGGER.error(error);
      throw new BaseException(error);
    }

    return object;
  }

  private BoatEntity updateEntity(@NotNull Boat dto) throws BaseException {
    final BoatEntity object = this.fromDto(dto);
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
