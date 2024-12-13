package dmon.SSHOP_springboot_backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dmon.SSHOP_springboot_backend.exception.ExceptionCode;
import dmon.SSHOP_springboot_backend.util.api.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * handle exception threw at Spring Security Filter Chain, specifically unauthenticated
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse httpResponse, AuthenticationException authException)
            throws IOException, ServletException {
        ExceptionCode exceptionCode = ExceptionCode.UNAUTHENTICATED;
        //define an httpResponse
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpResponse.setStatus(exceptionCode.getStatus().value());
        //define an apiResponse
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .success(false)
                .code(exceptionCode.getCode())
                .error(exceptionCode.getMessage())
                .build();
        //write httpResponse and apiResponse to buffet that will be sent to client
        ObjectMapper objectMapper = new ObjectMapper();
        httpResponse.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        httpResponse.flushBuffer();

    }
}
