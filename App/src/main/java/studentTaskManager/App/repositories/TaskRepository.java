package studentTaskManager.App.repositories;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import studentTaskManager.App.model.AppUser;
import studentTaskManager.App.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    Optional<Task> findById(Long id, Limit limit);

    List<Task> findByOwnerUser(AppUser ownerUser);

    List<Task> findByOwnerUserId(Long ownerUserId);
}
