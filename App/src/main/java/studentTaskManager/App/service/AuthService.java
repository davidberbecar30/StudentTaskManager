package studentTaskManager.App.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import studentTaskManager.App.dtos.UserDTO;
import studentTaskManager.App.model.AppUser;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;

    public AppUser register(UserDTO userDTO){
        if(userService.getUserByName(userDTO.getUsername()).isPresent()){
            throw new RuntimeException("User already exists");
        }
        return userService.createUser(userDTO);
    }
}
