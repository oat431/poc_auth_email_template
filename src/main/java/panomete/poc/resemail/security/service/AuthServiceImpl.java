package panomete.poc.resemail.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import panomete.poc.resemail.security.entity.Auth;
import panomete.poc.resemail.security.repository.AuthRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;

    @Override
    public Auth getUser(String username) {
        return authRepository.findByUsernameWithRoles(username);
    }
}
