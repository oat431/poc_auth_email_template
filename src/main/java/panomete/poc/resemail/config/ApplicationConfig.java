package panomete.poc.resemail.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import panomete.poc.resemail.role.entity.Role;
import panomete.poc.resemail.role.repository.RoleRepository;
import panomete.poc.resemail.security.entity.Auth;
import panomete.poc.resemail.security.repository.AuthRepository;
import panomete.poc.resemail.user.entity.User;
import panomete.poc.resemail.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationConfig implements ApplicationListener<ApplicationReadyEvent> {
    private final RoleRepository roleRepository;
    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        addRoleIfEmpty();
        addInitUserIfEmpty();
        log.info("Application is ready");
    }

    private void addRoleIfEmpty() {
        if(roleRepository.count() == 0) {
            log.info("Adding role");
            roleRepository.save(Role.builder()
                    .name("ROLE_ADMIN")
                    .description("Admin can do everything")
                    .build()
            );
            roleRepository.save(Role.builder()
                    .name("ROLE_USER")
                    .description("User can do many things")
                    .build()
            );
        }
    }

    protected void addInitUserIfEmpty() {
        if(userRepository.count() == 0) {
            Role admin = roleRepository.findByName("ROLE_ADMIN");
            Auth adminAuth = Auth.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .email("admin@example.com")
                    .roles(List.of(admin))
                    .build();
            authRepository.save(adminAuth);

            User adminAcc = User.builder()
                    .firstname("admin")
                    .lastname("admin")
                    .auth(adminAuth)
                    .build();
            userRepository.save(adminAcc);

            log.info("Adding User");
            Role user = roleRepository.findByName("ROLE_USER");

            Auth userAuth = Auth.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user"))
                    .email("user@example.com")
                    .roles(List.of(user))
                    .build();
            authRepository.save(userAuth);

            User userAcc = User.builder()
                    .firstname("user")
                    .lastname("user")
                    .auth(userAuth)
                    .build();
            userRepository.save(userAcc);

        }
    }

}
