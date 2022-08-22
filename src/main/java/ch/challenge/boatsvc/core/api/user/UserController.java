package ch.challenge.boatsvc.core.api.user;

import ch.challenge.boatsvc.configuration.annotations.RolesAllowedAdmin;
import ch.challenge.boatsvc.configuration.annotations.RolesAllowedAnonymous;
import ch.challenge.boatsvc.core.common.exception.BaseException;
import ch.challenge.boatsvc.core.jsonview.View.UserDetail;
import ch.challenge.boatsvc.core.jsonview.View.UserList;
import com.fasterxml.jackson.annotation.JsonView;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RolesAllowedAdmin
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }


  @GetMapping("/ping")
  @RolesAllowedAnonymous
  public ResponseEntity<String> pingService() {
    LOGGER.debug("[pingService] Ping method invoked for boats with currentTime : {}",
        Instant.now());
    return ResponseEntity.ok(UserController.class.getName());
  }

  @GetMapping
  @JsonView({UserList.class})
  public ResponseEntity<List<User>> findAll() {
    final List<User> all = this.userService.findAll();
    return ResponseEntity.ok(all);
  }

  @GetMapping("/{id}")
  @JsonView({UserDetail.class})
  public ResponseEntity<User> findById(@PathVariable("id") @NotNull Long id) {
    if (id < 1) {
      final String error = "ID les than 1";
      LOGGER.error("[findById] method not possible: ID null or equals zero ! {}", error);
      return ResponseEntity.badRequest().build();
    }

    final User one = this.userService.findOne(id);
    LOGGER.debug("[findById] method invoked for boats with currentTime : {}", Instant.now());

    if (Objects.isNull(one)) {
      final String error = "User not found";
      LOGGER.warn(
          "[findById] method not possible: boats not found in database with id [{}] ! {}", id,
          error);
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(one);
  }

  @PostMapping
  @JsonView({UserDetail.class})
  public ResponseEntity<User> create(@RequestBody @Valid User dto) {
    final User created;
    try {

      created = this.userService.create(dto);
      LOGGER.debug("[create] method invoked for boats with currentTime : {}", Instant.now());

    } catch (BaseException e) {
      LOGGER.error("[create] method not possible: {}", e.getLocalizedMessage());
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(created);
  }

  @DeleteMapping("/{id}")
  @RolesAllowedAdmin
  @JsonView({UserDetail.class})
  public ResponseEntity<User> delete(@PathVariable("id") @NotNull Long id) {
    if (id == 0) {
      final String error = "ID equals Zero";
      LOGGER.debug("[delete] method not possible: ID null or equals zero ! {}", error);
      return ResponseEntity.badRequest().build();
    }

    if (!this.userService.exists(id)) {
      final String error = "User not found !!";
      LOGGER.debug("[delete] method not possible: User not found in database ! {}", error);
      return ResponseEntity.notFound().build();
    }

    final User deleted;
    try {

      deleted = this.userService.delete(id);
      LOGGER.debug("[delete] method invoked for boats with currentTime : {}", Instant.now());

    } catch (BaseException e) {
      final String error = "Remove error";
      final String msg =
          "Delete not possible : User exists but the action does not be achieved ! %s";
      LOGGER.debug(String.format(msg, error));
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(deleted);
  }

  @PutMapping("/{id}")
  @JsonView({UserDetail.class})
  public ResponseEntity<User> update(@PathVariable("id") @NotNull Long id,
      @Valid @RequestBody User dto) {
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

    if (!this.userService.exists(dto)) {
      final String error = "User not found !!";
      LOGGER.debug("[update] - {} not found in database !", error);
      return ResponseEntity.notFound().build();
    }

    final User updated;
    try {

      updated = this.userService.update(dto);
      LOGGER.debug("[update] method invoked for boats with currentTime : {}", Instant.now());
    } catch (BaseException be) {
      LOGGER.error("[update] method not possible: {}", be.getLocalizedMessage());
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(updated);
  }
}
