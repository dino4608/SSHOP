package dmon.SSHOP_springboot_backend.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {
    public AppException(ExceptionCode exceptionCode)  {
        super(exceptionCode.getMessage());
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public AppException(ExceptionCode exceptionCode, String object)  {
        super(object + " " + exceptionCode.getMessage());
        this.code = exceptionCode.getCode();
        this.message = object + " " + exceptionCode.getMessage();
    }

    private int code;
    private String message;
}
