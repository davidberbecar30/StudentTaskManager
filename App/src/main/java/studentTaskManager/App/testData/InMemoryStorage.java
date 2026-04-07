package studentTaskManager.App.testData;

import org.springframework.stereotype.Component;
import studentTaskManager.App.model.AppUser;
import studentTaskManager.App.model.Role;
import studentTaskManager.App.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryStorage {

    public List<AppUser> users= new ArrayList<>(List.of(
            AppUser.builder().id(1L).username("user").password("user").role(Role.ROLE_USER).build(),
            AppUser.builder().id(2L).username("admin").password("admin").role(Role.ROLE_ADMIN).build()
    ));

    public List<Task> tasks= new ArrayList<>(List.of(
            Task.builder().id(1L).title("Math homework").description("Page 42").createdAt(LocalDateTime.now()).ownerUserId(1L).build(),
            Task.builder().id(2L).title("History essay").description("WW2 summary").createdAt(LocalDateTime.now()).ownerUserId(1L).build(),
            Task.builder().id(3L).title("Admin task").description("Check reports").createdAt(LocalDateTime.now()).ownerUserId(2L).build()
    ));
}
