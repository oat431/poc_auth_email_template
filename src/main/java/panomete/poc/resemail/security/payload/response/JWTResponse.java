package panomete.poc.resemail.security.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "JWTResponse")
public record JWTResponse(
        String accessToken,
        String refreshToken
) {
}
