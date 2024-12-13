package dmon.SSHOP_springboot_backend.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class AppException extends RuntimeException {
    /**
     * constructor for fixed exceptions
     * @param: ExceptionCode
     */
    public AppException(ExceptionCode exceptionCode)  {
        super(exceptionCode.getMessage());
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
        this.status = exceptionCode.getStatus();
    }

    /**
     * constructor for dynamic exceptions
     * @param: ExceptionCode
     */
    public AppException(ExceptionCode exceptionCode, String object)  {
        super(object + " " + exceptionCode.getMessage());
        this.code = exceptionCode.getCode();
        this.message = object + " " + exceptionCode.getMessage();
        this.status = exceptionCode.getStatus();
    }

    private int code;
    private String message;
    private HttpStatusCode status;
}
