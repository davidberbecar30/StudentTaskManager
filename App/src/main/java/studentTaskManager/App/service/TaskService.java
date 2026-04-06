package studentTaskManager.App.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import studentTaskManager.App.model.Task;
import studentTaskManager.App.testData.InMemoryStorage;
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
}
