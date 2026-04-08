package studentTaskManager.App.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import studentTaskManager.App.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByUsername(String username);
}
