package dmon.SSHOP_springboot_backend.dto.request;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountCreateRequest {
    @Size(message = "USERNAME_INVALID", min = 4)
    String username;

    @Pattern(message = "EMAIL_INVALID", regexp = "^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}$")
    String email;

    @Pattern(message = "PHONE_INVALID", regexp = "^[0-9]{10}$")
    String phone;

    @Size(message = "PASSWORD_INVALID", min = 6)
    String password;
}
