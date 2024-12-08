package dmon.SSHOP_springboot_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreateRequest {
    @Pattern(message = "EMAIL_INVALID", regexp = "^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}$")
    private String email;
    @Pattern(message = "PHONE_INVALID", regexp = "^[0-9]{10}$")
    private String phone;
    @Size(message = "PASSWORD_INVALID", min = 6)
    private String password;
}
