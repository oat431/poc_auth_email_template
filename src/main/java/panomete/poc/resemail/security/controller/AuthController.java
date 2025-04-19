package panomete.poc.resemail.security.controller;


import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import panomete.poc.resemail.common.entity.Status;
import panomete.poc.resemail.common.payload.response.ResponseDto;
import panomete.poc.resemail.security.entity.Auth;
import panomete.poc.resemail.security.payload.request.LoginRequest;
import panomete.poc.resemail.security.payload.response.JWTResponse;
import panomete.poc.resemail.security.service.AuthService;
import panomete.poc.resemail.util.JWTUtil;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication Feature")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @PostMapping("sign-in")
    @ApiResponse(
            responseCode = "200",
            description = "Login successful"
    )
    @Operation(summary = "Login", description = "Login")
    public ResponseEntity<ResponseDto<JWTResponse>> authenticate(
            @RequestBody LoginRequest request
    ) {
        String username = request.username();
        String password = request.password();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(
                    new ResponseDto<>(
                            Status.ERROR,
                            null,
                            "Incorrect Username or password"
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
        Auth user = authService.getUser(username);
        String jwtToken = jwtUtil.generateAccessToken(user);
        String refreshToken = UUID.randomUUID().toString();
        JWTResponse response = new JWTResponse(jwtToken, refreshToken);
//        service.login(user, jwtToken, refreshToken);
        return ResponseEntity.ok(new ResponseDto<>(
                Status.SUCCESS,
                response,
                "Login successful"
            )
        );
    }

    @GetMapping("/credentials")
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get user credentials", description = "Get user credentials")
    public ResponseEntity<Claims> getUserCredentials(HttpServletRequest request) {
        try{
            String token = request.getHeader("Authorization").substring(7);
            return ResponseEntity.ok(
                    jwtUtil.getClaimsFromToken(token)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
