package dmon.SSHOP_springboot_backend.dto.response;

import dmon.SSHOP_springboot_backend.util.enumerate.GenderEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    private Long userId;
    private String name;
    private String photo;
    private Date dob;
    private GenderEnum gender;


}
