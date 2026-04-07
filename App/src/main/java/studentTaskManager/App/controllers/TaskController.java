package studentTaskManager.App.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import studentTaskManager.App.dtos.TaskDTO;
import studentTaskManager.App.model.Task;
import studentTaskManager.App.service.TaskService;
import studentTaskManager.App.service.UserService;
import studentTaskManager.App.testData.InMemoryStorage;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final UserService userService;
    private final TaskService taskService;

    @GetMapping("/my")
    public ResponseEntity<List<Task>> getMyTasks(Authentication authentication){
        String username=authentication.getName();
        return userService.getUserByName(username)
                .map(user->ResponseEntity.ok(taskService.getTasksByOwnerId(user.getId())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO task, Authentication authentication) {
        String username = authentication.getName();
        return userService.getUserByName(username).map(user -> {
            Task created = taskService.createTask(task.getTitle(), task.getDescription(), user.getId());
            return ResponseEntity.ok().body(created);
        }).orElse(ResponseEntity.notFound().build());
    }
}
