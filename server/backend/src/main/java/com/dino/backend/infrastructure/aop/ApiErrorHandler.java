package com.dino.backend.infrastructure.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.dino.backend.shared.domain.exception.AppException;
import com.dino.backend.shared.domain.exception.ErrorCode;

import java.util.Optional;

@ControllerAdvice
@Slf4j
public class ApiErrorHandler {
    /**
     * Handle app exception threw in the service tier
     *
     * @param: AppException
     * @return: ApiResponse
     */
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<Object>> handleException(AppException exception) {
        return ResponseEntity
                .status(exception.getStatus())
                .body(ApiResponse.builder()
                        .success(false)
                        .status(exception.getStatus().value())
                        .code(exception.getCode())
                        .error(exception.getMessage())
                        .build());
    }

    /**
     * Handle app exception unhandled
     *
     * @param: RuntimeException
     * @return: ApiResponse
     */
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse<Object>> handleException(RuntimeException exception) {
        log.error(">>> INTERNAL: unhandled exception occurred", exception);
        ErrorCode error = ErrorCode.SYSTEM__UNHANDLED_EXCEPTION;
        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponse.builder()
                        .success(false)
                        .status(error.getStatus().value())
                        .code(error.getCode())
                        .error(error.getMessage())
                        .build());
    }

    /**
     * Handle exception threw in the validation tier
     *
     * @params: RuntimeException
     * @return: ApiResponse
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object>> handleException(MethodArgumentNotValidException exception) {
        String key = Optional.ofNullable(exception.getFieldError())
                .map(FieldError::getDefaultMessage)
                .orElse(ErrorCode.SYSTEM__KEY_UNSUPPORTED.name());

        ErrorCode error = ErrorCode.safeValueOf(key)
                .orElse(ErrorCode.SYSTEM__KEY_UNSUPPORTED);

        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponse.builder()
                        .success(false)
                        .status(error.getStatus().value())
                        .code(error.getCode())
                        .error(error.getMessage())
                        .build());
    }

    /**
     * Handle exception threw in the service tier by spring security
     *
     * @return ApiResponse
     * @param: AccessDeniedException
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<Object>> handleException(AccessDeniedException exception) {
        ErrorCode error = ErrorCode.SECURITY__UNAUTHORIZED;
        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponse.builder()
                        .success(false)
                        .status(error.getStatus().value())
                        .code(error.getCode())
                        .error(error.getMessage())
                        .build());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ApiResponse<Object>> handleException(HttpRequestMethodNotSupportedException exception) {
        ErrorCode error = ErrorCode.SYSTEM__METHOD_NOT_SUPPORTED;
        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponse.builder()
                        .success(false)
                        .status(error.getStatus().value())
                        .code(error.getCode())
                        .error(String.format(error.getMessage(), exception.getMethod()))
                        .build());
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    ResponseEntity<ApiResponse<Object>> handleException(NoResourceFoundException exception) {
        ErrorCode error = ErrorCode.SYSTEM__ROUTE_NOT_SUPPORTED;
        return ResponseEntity
                .status(error.getStatus())
                .body(ApiResponse.builder()
                        .success(false)
                        .status(error.getStatus().value())
                        .code(error.getCode())
                        .error(String.format(error.getMessage(), exception.getResourcePath()))
                        .build());
    }
}
