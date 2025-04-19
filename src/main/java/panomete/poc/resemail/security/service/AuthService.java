package panomete.poc.resemail.security.service;

import panomete.poc.resemail.security.entity.Auth;

public interface AuthService {
    Auth getUser(String username);
}
