package dmon.SSHOP_springboot_backend.dto.response;

import dmon.SSHOP_springboot_backend.util.enumerate.GenderEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponse {
    private Long userId;
    private String name;
    private String photo;
    private Date dob;
    private GenderEnum gender;


}
