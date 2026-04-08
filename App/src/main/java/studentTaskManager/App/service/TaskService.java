package studentTaskManager.App.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import studentTaskManager.App.model.Task;
import studentTaskManager.App.testData.InMemoryStorage;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {

    private final InMemoryStorage storage;

    public List<Task> getAllTasks(){
        return storage.tasks;
    }

    public List<Task> getTasksByOwnerId(Long id){
        return storage.tasks.stream().filter(task -> task.getOwnerUserId().equals(id)).toList();
    }

    public Task createTask(String title, String description, Long userId){
        Task task=Task.builder()
                .id((long)storage.tasks.size()+1)
                .title(title)
                .description(description)
                .createdAt(LocalDateTime.now())
                .ownerUserId(userId)
                .build();
        storage.tasks.add(task);
        return task;
    }

    public Task deleteTask(Long id){
        for (Task t : storage.tasks){
            if(t.getId().equals(id)){
                storage.tasks.remove(t);
                return t;
            }
        }
        return null;
    }
}
