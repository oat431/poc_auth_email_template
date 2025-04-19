package panomete.poc.resemail.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Resemail API",
                version = "0.1-dev",
                description = "POC FULL AUTHENTICATION WITH EMAIL DOCUMENT API"
        ),
        servers = {
                @Server(
                        description = "Local ENV 1",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Local ENV 2",
                        url = "http://localhost:8443"
                )
        }
)
@SecurityScheme(
        name = "Bearer Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT"
)
public class OpenAPIConfig {
}
