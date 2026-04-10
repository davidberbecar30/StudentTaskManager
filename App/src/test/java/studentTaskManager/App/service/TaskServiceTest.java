package studentTaskManager.App.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import studentTaskManager.App.model.AppUser;
import studentTaskManager.App.model.Task;
import studentTaskManager.App.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskService taskService;

    @Test
    void createTaskSuccessTest(){
        String title="test";
        String description="test";
        Long userId=1L;

        AppUser user=AppUser.builder().id(userId).build();

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(
                Task.builder()
                        .title(title)
                        .description(description)
                        .ownerUser(user)
                        .build()
        );
        Task created = taskService.createTask(title, description, userId);

        assertNotNull(created);
        assertEquals(title, created.getTitle());
        assertEquals(description, created.getDescription());
        assertEquals(userId, created.getOwnerUserId());
    }

    @Test
    void createTaskFailTest(){
        String title="test";
        String description="test";
        Long userId=1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> taskService.createTask(title, description, userId));
        Mockito.verify(userService, Mockito.times(1)).getUserById(userId);
        Mockito.verify(taskRepository, Mockito.never()).save(any(Task.class));
    }

    @Test
    void getTasksByOwnerIdTest(){
        Long userId=1L;

        AppUser user=AppUser.builder().id(userId).build();
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        
        Task task=Task.builder().ownerUser(user).build();
        when(taskRepository.findByOwnerUser(user)).thenReturn(List.of(task));

        assertEquals(List.of(task), taskService.getTasksByOwnerId(userId));
    }

    @Test
    void getAllTasksTest() {
        Task task1 = Task.builder().id(1L).title("Task 1").build();
        Task task2 = Task.builder().id(2L).title("Task 2").build();
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        assertEquals(task1, result.get(0));
        assertEquals(task2, result.get(1));
    }

    @Test
    void deleteTaskSuccessTest() {
        Long taskId = 1L;
        Task task = Task.builder().id(taskId).title("Task to delete").build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task deleted = taskService.deleteTask(taskId);

        assertNotNull(deleted);
        assertEquals(taskId, deleted.getId());
        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(taskId);
    }

    @Test
    void deleteTaskNotFoundTest() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        Task deleted = taskService.deleteTask(taskId);

        assertNull(deleted);
        Mockito.verify(taskRepository, Mockito.never()).deleteById(any());
    }
}