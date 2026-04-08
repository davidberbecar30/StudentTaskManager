package studentTaskManager.App.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import studentTaskManager.App.model.AppUser;
import studentTaskManager.App.model.Task;
import studentTaskManager.App.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TaskService {

    private final UserService userService;
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public List<Task> getTasksByOwnerId(Long id){
        return taskRepository.findByOwnerUser(userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    public Task createTask(String title, String description, Long userId) {
        AppUser owner = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = Task.builder()
                .title(title)
                .description(description)
                .createdAt(LocalDateTime.now())
                .ownerUser(owner)
                .build();

        return taskRepository.save(task);
    }

    public Task deleteTask(Long id){
        Optional<Task> task=taskRepository.findById(id);
        if(task.isPresent()){
            taskRepository.deleteById(task.get().getId());
            return task.get();
        }
        return null;
    }
}
