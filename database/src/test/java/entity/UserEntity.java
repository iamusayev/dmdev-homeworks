package entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Gender;
import model.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Gender gender;
    private Role role;
    private LocalDate birthday;
}
