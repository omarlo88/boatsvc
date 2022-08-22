package ch.challenge.boatsvc;

import ch.challenge.boatsvc.core.api.boat.Boat;
import ch.challenge.boatsvc.core.api.boat.BoatService;
import ch.challenge.boatsvc.core.api.user.EnumRole;
import ch.challenge.boatsvc.core.api.user.User;
import ch.challenge.boatsvc.core.api.user.UserService;
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
	CommandLineRunner run(BoatService boatService, UserService userService) {
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

			final User user1 = User.builder()
					.username("olo")
					.email("olo@gmail.com")
					.role(EnumRole.ADMIN)
					.firstName("Omar")
					.lastName("Lo")
					.password("123")
					.build();

			final User test1 = User.builder()
					.username("test1")
					.email("test1@gmail.com")
					.role(EnumRole.USER)
					.firstName("Test1")
					.lastName("Test1")
					.password("123")
					.build();

			final User test2 = User.builder()
					.username("test2")
					.email("test2@gmail.com")
					.role(EnumRole.ANONYMOUS)
					.firstName("Test2")
					.lastName("Test2")
					.password("123")
					.build();
			userService.createAll(List.of(user1, test1, test2));
		};
	}
}
