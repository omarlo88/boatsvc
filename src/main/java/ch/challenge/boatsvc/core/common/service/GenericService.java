package ch.challenge.boatsvc.core.common.service;

import ch.challenge.boatsvc.core.common.domain.AbstractDomain;
import ch.challenge.boatsvc.core.common.exception.BoatException;
import ch.challenge.boatsvc.core.common.model.AbstractBaseEntity;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

public interface GenericService<T, E extends AbstractBaseEntity, D extends AbstractDomain> {

  /**
   * Convert a list of beans entities to a list of DTO.
   *
   * @param list bean list
   * @return DTO list
   */
  List<D> toDto(final List<E> list);

  /**
   * Convert a bean entity to a DTO
   *
   * @param bean the bean
   * @return DTO the DTO
   */
  D toDto(final E bean);

  /**
   * Convert a DTO to its bean entity
   *
   * @param dto l'objet DTO
   * @return l'objet modèle
   */
  E fromDto(final D dto);

  /**
   * Check if it exists
   *
   * @param id
   * @return
   */
  boolean exists(final T id);

  /**
   * Check if it exists
   *
   * @param dto the DTO
   * @return
   */
  boolean exists(final D dto);

  /**
   * Find all instances
   *
   * @return list of all instances DTO
   */
  @Transactional
  List<D> findAll();

  /**
   * Create a new instance
   *
   * @param dto the DTO
   * @return the created DTO or null if action failed
   */
  @Transactional
  D create(final D dto) throws BoatException;

  /**
   * Create all new instances
   *
   * @param dtos the DTOs to create
   * @return the created DTOs or an empty list if action failed
   */
  @Transactional
  List<D> createAll(final List<D> dtos) throws BoatException;

  /**
   * Find a single instance by its ID
   *
   * @param id the ID
   * @return
   */
  @Transactional
  Optional<E> findById(final T id);

  /**
   * Remove an instance by its ID
   *
   * @param id the ID
   * @return deleted DTO or null if action failed
   */
  @Transactional
  D delete(final T id) throws BoatException;

  /**
   * Update a given instance
   *
   * @param dto the DTO
   * @return updated DTO or null if action failed
   */
  @Transactional
  D update(D dto) throws BoatException;
}
