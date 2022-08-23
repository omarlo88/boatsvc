package ch.challenge.boatsvc;

import ch.challenge.boatsvc.core.api.boat.Boat;
import ch.challenge.boatsvc.core.api.boat.BoatService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BoatAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoatAppApplication.class, args);
	}

	@Bean
	CommandLineRunner run(BoatService boatService) {// Inject if nessary UserService userService
		return args -> {
			final Boat boat1 = Boat.builder()
					.name("Boat 1")
					.description("Description...")
					.priceDutyFree(BigDecimal.valueOf(15000L))
					.build();

			final Boat boat2 = Boat.builder()
					.name("Boat 2")
					.description("Description...")
					.priceDutyFree(BigDecimal.valueOf(15000L))
					.build();

			final Boat boat3 = Boat.builder()
					.name("Boat 3")
					.description("Description...")
					.priceDutyFree(BigDecimal.valueOf(15000L))
					.build();

			final Boat boat4 = Boat.builder()
					.name("Boat 4")
					.description("Description...")
					.priceDutyFree(BigDecimal.valueOf(15000L))
					.build();
			boatService.createAll(List.of(boat1, boat2, boat3, boat4));

			/*final User user1 = User.builder()
					.username("olo")
					.email("olo@gmail.com")
					.role(EnumRole.ADMIN)
					.firstName("Omar")
					.lastName("Lo")
					.password("123")
					.build();

			final User test1 = User.builder()
					.username("miyalley")
					.email("michelle@gmail.com")
					.role(EnumRole.USER)
					.firstName("Michelle")
					.lastName("Jalley")
					.password("123")
					.build();

			final User test2 = User.builder()
					.username("papond")
					.email("dupond@gmail.com")
					.role(EnumRole.ANONYMOUS)
					.firstName("Patrick")
					.lastName("Dupond")
					.password("123")
					.build();
			userService.createAll(List.of(user1, test1, test2));*/
		};
	}
}
