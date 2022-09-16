package ch.challenge.boatsvc.core.api.boat;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Disabled
@DataJpaTest
public class BoatRepositoryTest {

  @Autowired
  private BoatRepository boatRepository;

  @Test
  public void shouldSaveBoat() {// Before to run test comment code CommandLineRunner in main class
    final BoatEntity expected = new BoatEntity();
    expected.setName("Boat 1");
    expected.setDescription("Boat 1 Desc..");
    final BoatEntity actual = boatRepository.save(expected);
    assertThat(actual).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);
  }

}