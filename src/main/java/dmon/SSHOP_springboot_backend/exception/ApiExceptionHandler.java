package dmon.SSHOP_springboot_backend.exception;

import dmon.SSHOP_springboot_backend.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    /**
     * handle app exception threw in the service tier
     */
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Object>> handleException(AppException exception){
        ApiResponse<Object> apiError = ApiResponse.builder()
                .code(exception.getCode())
                .error(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiError);
    }

    /**
     * handle app exception uncategorized
     */
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<Object>> handleException(RuntimeException exception) {
        ApiResponse<Object> apiError = ApiResponse.builder()
                .success(false)
                .code(ExceptionCode.UNCATEGORIZED_EXCEPTION.getCode())
                .error(ExceptionCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiError);
    }

    /**
     * handle exception threw in the validation tier
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object>> handleException(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();

        ExceptionCode errorCode = ExceptionCode.KEY_INVALID;
        try {
            errorCode = ExceptionCode.valueOf(enumKey);
        } catch (IllegalArgumentException ignored){ }

        ApiResponse<Object> apiError = ApiResponse.builder()
                .success(false)
                .code(errorCode.getCode())
                .error(errorCode.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiError);
    }
}
