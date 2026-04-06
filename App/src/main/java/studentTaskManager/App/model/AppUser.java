package studentTaskManager.App.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    private Long id;
    private String username;
    private String password;
    private Role role;
}
