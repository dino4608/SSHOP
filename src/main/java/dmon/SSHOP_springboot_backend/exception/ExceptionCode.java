package dmon.SSHOP_springboot_backend.exception;

import lombok.*;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    //SYSTEM EXCEPTION
    UNCATEGORIZED_EXCEPTION(9000, "An uncategorized error"),
    KEY_INVALID(1004, "Can not get value of enum key"),
    //DYNAMIC EXCEPTION
    OBJECT_NOT_FOUND(1001, "not found"),
    OBJECT_EXISTED(1005, "existed"),
    //VALIDATION EXCEPTION
    EMAIL_INVALID(1002, "Email should follow the email format"),
    PHONE_INVALID(1003, "Phone should follow the phone format"),
    PASSWORD_INVALID(1004, "Password should be at least 6 characters"),
    //OTHERS
    ;

    private int code;
    private String message;
}
