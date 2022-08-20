package ch.challenge.boatsvc.core.api.boat;

import ch.challenge.boatsvc.core.common.exception.BaseException;
import ch.challenge.boatsvc.core.jsonview.View.BoatDetail;
import ch.challenge.boatsvc.core.jsonview.View.BoatList;
import com.fasterxml.jackson.annotation.JsonView;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/boats")
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
public class BoatController {

  private static final Logger LOGGER = LoggerFactory.getLogger(BoatController.class);

  private final BoatService boatService;

  public BoatController(BoatService boatService) {
    this.boatService = boatService;
  }

  @GetMapping("/ping")
  public ResponseEntity<String> pingService() {
    LOGGER.debug("[pingService] Ping method invoked for boats with currentTime : {}",
        Instant.now());
    return ResponseEntity.ok(BoatController.class.getName());
  }

  @GetMapping
  @JsonView({BoatList.class})
  public ResponseEntity<List<Boat>> findAll() {
    final List<Boat> all = this.boatService.findAll();
    return ResponseEntity.ok(all);
  }

  @GetMapping("/{id}")
  @JsonView({BoatDetail.class})
  public ResponseEntity<Boat> findById(@PathVariable("id") @NotNull Long id) {
    if (id < 1) {
      final String error = "ID les than 1";
      LOGGER.error("[findById] method not possible: ID null or equals zero ! {}", error);
      return ResponseEntity.badRequest().build();
    }

    final Boat one = this.boatService.findOne(id);
    LOGGER.debug("[findById] method invoked for boats with currentTime : {}", Instant.now());

    if (Objects.isNull(one)) {
      final String error = "Boat not found";
      LOGGER.warn(
          "[findById] method not possible: boats not found in database with id [{}] ! {}", id,
          error);
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(one);
  }

  @PostMapping
  @JsonView({BoatDetail.class})
  public ResponseEntity<Boat> create(@RequestBody @Valid Boat dto) {
    final Boat created;
    try {

      created = this.boatService.create(dto);
      LOGGER.debug("[create] method invoked for boats with currentTime : {}", Instant.now());

    } catch (BaseException e) {
      LOGGER.error("[create] method not possible: {}", e.getLocalizedMessage());
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(created);
  }

  @DeleteMapping("/{id}")
  @JsonView({BoatDetail.class})
  public ResponseEntity<Boat> delete(@PathVariable("id") @NotNull Long id) {
    if (id == 0) {
      final String error = "ID equals Zero";
      LOGGER.debug("[delete] method not possible: ID null or equals zero ! {}", error);
      return ResponseEntity.badRequest().build();
    }

    if (!this.boatService.exists(id)) {
      final String error = "Boat not found !!";
      LOGGER.debug("[delete] method not possible: Boat not found in database ! {}", error);
      return ResponseEntity.notFound().build();
    }

    final Boat deleted;
    try {

      deleted = this.boatService.delete(id);
      LOGGER.debug("[delete] method invoked for boats with currentTime : {}", Instant.now());

    } catch (BaseException e) {
      final String error = "Remove error";
      final String msg =
          "Delete not possible : Boat exists but the action does not be achieved ! %s";
      LOGGER.debug(String.format(msg, error));
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(deleted);
  }

  @PutMapping("/{id}")
  @JsonView({BoatDetail.class})
  public ResponseEntity<Boat> update(@PathVariable("id") @NotNull Long id,
      @Valid @RequestBody Boat dto) {
    final Long dtoId = dto.getId();
    final int idValue = id.intValue();
    final int dtoIdValue = dtoId.intValue();

    if (idValue <= 0 || dtoIdValue <= 0) {
      final String error = "ID missing";
      LOGGER.debug("[update] method not possible: ID null, less or equals zero ! {}", error);
      return ResponseEntity.badRequest().build();
    }

    if (idValue != dtoIdValue) {
      final String error = "Conflict ID";
      LOGGER.debug("[update] method not possible: ID  different from DTO ID ! {}", error);
      return ResponseEntity.badRequest().build();
    }

    if (!this.boatService.exists(dto)) {
      final String error = "Boat not found !!";
      LOGGER.debug("[update] - {} not found in database !", error);
      return ResponseEntity.notFound().build();
    }

    final Boat updated;
    try {

      updated = this.boatService.update(dto);
      LOGGER.debug("[update] method invoked for boats with currentTime : {}", Instant.now());
    } catch (BaseException be) {
      LOGGER.error("[update] method not possible: {}", be.getLocalizedMessage());
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(updated);
  }

}
