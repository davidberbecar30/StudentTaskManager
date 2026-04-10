package studentTaskManager.App.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import studentTaskManager.App.dtos.UserDTO;
import studentTaskManager.App.model.AppUser;
import studentTaskManager.App.model.Role;
import studentTaskManager.App.repositories.AppUserRepository;
import studentTaskManager.App.service.AuthService;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(JacksonAutoConfiguration.class)
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private AppUserRepository userRepository;

    @Test
    void unauthenticatedAccessToAdminRouteReturns401or403() throws Exception {

        mockMvc.perform(get("/admin/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void userRoleAccessToAdminRouteReturns403() throws Exception {
        mockMvc.perform(get("/admin/user"))
                .andExpect(status().isForbidden());
    }

    @Test
    void userRegistrationReturnsOk() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("newuser");
        userDTO.setPassword("password");

        AppUser appUser = AppUser.builder()
                .username("newuser")
                .role(Role.ROLE_USER)
                .build();

        when(authService.register(any(UserDTO.class))).thenReturn(appUser);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }
}
