package studentTaskManager.App.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import studentTaskManager.App.dtos.UserDTO;
import studentTaskManager.App.model.AppUser;
import studentTaskManager.App.model.Role;
import studentTaskManager.App.repositories.AppUserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<AppUser> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<AppUser> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<AppUser> getUserByName(String name){
        return userRepository.findByUsername(name);
    }

    public AppUser createUser(UserDTO userdto){

        if(userRepository.findByUsername(userdto.getUsername()).isPresent()){
            throw new RuntimeException("User already exists");
        }

        AppUser user = AppUser.builder()
                .username(userdto.getUsername())
                .password(passwordEncoder.encode(userdto.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        return userRepository.save(user);
    }
}
