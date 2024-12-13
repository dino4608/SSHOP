package dmon.SSHOP_springboot_backend.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    //SYSTEM EXCEPTION//
    UNCATEGORIZED_EXCEPTION(9000, "An uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    KEY_INVALID(1004, "Can not get value of enum key", HttpStatus.INTERNAL_SERVER_ERROR),
    //SECURITY//
    UNAUTHENTICATED(1006, "Not allowed", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "No permission", HttpStatus.FORBIDDEN),
    //DYNAMIC EXCEPTION//
    OBJECT_NOT_FOUND(1001, "not found", HttpStatus.NOT_FOUND),
    OBJECT_EXISTED(1005, "existed", HttpStatus.CONFLICT),
    //VALIDATION EXCEPTION//
    EMAIL_INVALID(1002, "Email should follow the email format", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(1003, "Phone should follow the phone format", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1006, "Password should be at least 4 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1005, "Password should be at least 6 characters", HttpStatus.BAD_REQUEST),
    //OTHERS//
    //MAX: 1007//
    ;

    private int code;
    private String message;
    private HttpStatusCode status;
}
