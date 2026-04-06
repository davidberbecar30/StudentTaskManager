package studentTaskManager.App.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import studentTaskManager.App.model.AppUser;
import studentTaskManager.App.testData.InMemoryStorage;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final InMemoryStorage storage;

    public List<AppUser> getAllUsers(){
        return storage.users;
    }

    public Optional<AppUser> getUserById(Long id){
        return storage.users.stream().filter(user->user.getId().equals(id)).findFirst();
    }
}
