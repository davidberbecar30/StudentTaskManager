package studentTaskManager.App.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import studentTaskManager.App.dtos.UserDTO;
import studentTaskManager.App.model.AppUser;
import studentTaskManager.App.securityConfig.JwtUtil;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AppUser register(UserDTO userDTO){
        if(userService.getUserByName(userDTO.getUsername()).isPresent()){
            throw new RuntimeException("User already exists");
        }
        return userService.createUser(userDTO);
    }

    public String login(UserDTO userdto) {
        AppUser user=userService.getUserByName(userdto.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!passwordEncoder.matches(userdto.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        return jwtUtil.generateToken(user.getUsername(),user.getRole().name());
    }
}
