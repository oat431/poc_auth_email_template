package panomete.poc.resemail.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) {
        Throwable rootCause = (Throwable) request.getAttribute("middleware_error");

        response.setStatus(rootCause != null ? 500 : 401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        var errorBody = new HashMap<String, Object>();
        errorBody.put("status", response.getStatus());
        errorBody.put("error", rootCause != null ? "Internal authentication error" : "Unauthorized");
        errorBody.put("message", rootCause != null ? rootCause.getMessage() : authException.getMessage());
        errorBody.put("timestamp", Instant.now().toString());
        errorBody.put("path", request.getRequestURI());

        try {
            new ObjectMapper().writeValue(response.getWriter(), errorBody);
        } catch (IOException e) {
            log.error("Error writing error response: {}", e.getMessage());
        }
    }
}
