package studentTaskManager.App;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import studentTaskManager.App.model.AppUser;
import studentTaskManager.App.model.Role;
import studentTaskManager.App.repositories.AppUserRepository;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
	@Bean
	CommandLineRunner init(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername("admin").isEmpty()) {
				AppUser admin = AppUser.builder()
						.username("admin")
						.password(passwordEncoder.encode("admin_password"))
						.role(Role.ROLE_ADMIN)
						.build();

				userRepository.save(admin);
				System.out.println("Admin user created: admin / admin_password");
			}
		};
	}
}
