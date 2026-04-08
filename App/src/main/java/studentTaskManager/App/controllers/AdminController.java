package studentTaskManager.App.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import studentTaskManager.App.dtos.UserDTO;
import studentTaskManager.App.model.AppUser;
import studentTaskManager.App.service.UserService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<AppUser> addUser(@RequestBody UserDTO userDTO){
        try {
            return ResponseEntity.ok().body( userService.createUser(userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<AppUser>> getUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }
}
