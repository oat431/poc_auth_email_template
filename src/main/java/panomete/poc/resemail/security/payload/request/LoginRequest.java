package panomete.poc.resemail.security.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginRequest")
public record LoginRequest(
        String username,
        String password
) {
}
