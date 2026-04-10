package studentTaskManager.App.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import studentTaskManager.App.dtos.UserDTO;
import studentTaskManager.App.model.AppUser;
import studentTaskManager.App.model.Role;
import studentTaskManager.App.repositories.AppUserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password");

        appUser = AppUser.builder()
                .id(1L)
                .username("testuser")
                .password("encodedPassword")
                .role(Role.ROLE_USER)
                .build();
    }

    @Test
    void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(List.of(appUser));
        List<AppUser> users = userService.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("testuser", users.get(0).getUsername());
    }

    @Test
    void getUserByIdTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(appUser));
        Optional<AppUser> user = userService.getUserById(1L);
        assertTrue(user.isPresent());
        assertEquals("testuser", user.get().getUsername());
    }

    @Test
    void getUserByNameTest() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(appUser));
        Optional<AppUser> user = userService.getUserByName("testuser");
        assertTrue(user.isPresent());
        assertEquals(1L, user.get().getId());
    }

    @Test
    void createUserSuccessTest() {
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(AppUser.class))).thenReturn(appUser);

        AppUser created = userService.createUser(userDTO);

        assertNotNull(created);
        assertEquals("testuser", created.getUsername());
        verify(userRepository).save(any(AppUser.class));
    }

    @Test
    void createUserAlreadyExistsTest() {
        when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(appUser));

        assertThrows(RuntimeException.class, () -> userService.createUser(userDTO));
        verify(userRepository, never()).save(any(AppUser.class));
    }
}
