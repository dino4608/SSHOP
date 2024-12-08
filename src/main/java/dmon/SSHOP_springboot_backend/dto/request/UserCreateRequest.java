package dmon.SSHOP_springboot_backend.dto.request;

import dmon.SSHOP_springboot_backend.util.enumerate.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    private String name;
    private String photo;
    private Date dob;
    private GenderEnum gender;
}
