package ch.challenge.boatsvc.configuration;

import ch.challenge.boatsvc.core.api.user.UserService;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);
  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userService.getUserByUsername(username).map(user -> {
          LOGGER.info("User found in database: {}", user.getFullName());

          Collection<GrantedAuthority> authorities = List.of(
              new SimpleGrantedAuthority(user.getRole().getRoleCode()));
          return new User(user.getUsername(), user.getPassword(), authorities);
        })
        .orElseThrow(() -> new UsernameNotFoundException(
            String.format("Username %s not found in the database", username)));
  }
}
