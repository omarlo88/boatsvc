package ch.challenge.boatsvc.core.api.boat;

import ch.challenge.boatsvc.core.common.exception.BoatException;
import ch.challenge.boatsvc.core.common.service.GenericService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
  public List<Boat> toDto(List<BoatEntity> list) {
    if (Objects.isNull(list) || list.isEmpty()) {
      return new ArrayList<>();
    }

    return list.stream().filter(Objects::nonNull).map(this::toDto).toList();
  }

  @Override
  public Boat toDto(BoatEntity bean) {
    if (Objects.isNull(bean)) {
      return null;
    }
    return this.boatMapper.map(bean);
  }

  @Override
  public BoatEntity fromDto(Boat dto) {
    if (Objects.isNull(dto)) {
      return null;
    }

    return this.boatMapper.map(dto);
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
    List<BoatEntity> all = this.boatRepository.findAll();
    return this.toDto(all);
  }

  @Override
  public Boat create(Boat dto) throws BoatException {
    final BoatEntity object = this.createEntity(dto);
    final BoatEntity createdObject = this.boatRepository.saveAndFlush(object);
    return this.toDto(createdObject);
  }

  @Override
  public List<Boat> createAll(List<Boat> dtos) throws BoatException {
    List<BoatEntity> entities = new ArrayList<>();
    for (Boat dto : dtos) {
      BoatEntity object = this.createEntity(dto);
      entities.add(object);
    }

    entities = this.boatRepository.saveAllAndFlush(entities);

    return entities.stream().map(this::toDto).toList();
  }

  @Override
  public Optional<BoatEntity> findById(Long id) {
    if (Objects.isNull(id)) {
      return Optional.empty();
    }

    return this.boatRepository.findById(id);
  }

  @Override
  public Boat delete(Long id) throws BoatException {
    this.verifyBeforeDelete(id);
    final BoatEntity bean = this.findById(id).orElseThrow(RuntimeException::new);
    this.boatRepository.deleteById(id);
    return this.toDto(bean);
  }

  @Override
  public Boat update(Boat dto) throws BoatException {
    final BoatEntity object = this.updateEntity(dto);
    final BoatEntity updatedObject = this.boatRepository.save(object);
    return this.toDto(updatedObject);
  }

  // Private methods /////////////////////////////////////////////////////////////////////////////
  private BoatEntity createEntity(Boat dto) throws BoatException {
    final BoatEntity object = this.fromDto(dto);
    final Long id = object.getId();
    if (Objects.nonNull(id)) {
      final String msg = "Impossible to create object of type %s : id %s should be null";
      final String error = String.format(msg, object.getClass().getName(), id);
      LOGGER.error(error);
      throw new BoatException(error);
    }

    return object;
  }

  private BoatEntity updateEntity(Boat dto) throws BoatException {
    final BoatEntity object = this.fromDto(dto);
    if (!this.exists(dto)) {
      final String msg = "Impossible to update object of type %s : no object found in database.";
      final String error = String.format(msg, object.getClass().toString());
      LOGGER.error(error);
      throw new BoatException(error);
    }
    return object;
  }

  private void verifyBeforeDelete(Long id) throws BoatException {
    if (Objects.isNull(id)) {
      final String msgError = "Impossible to delete object: ID not sent to the server.";
      LOGGER.error(msgError);
      throw new BoatException(msgError);
    }

    if (!this.exists(id)) {
      final String msg = "Impossible to delete object with id %s: No existing object.";
      final String error = String.format(msg, id);
      LOGGER.error(error);
      throw new BoatException(error);
    }
  }
}
