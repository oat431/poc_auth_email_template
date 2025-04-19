package panomete.poc.resemail.security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import panomete.poc.resemail.common.entity.BaseEntity;
import panomete.poc.resemail.role.entity.Role;
import panomete.poc.resemail.token.entity.RefreshToken;
import panomete.poc.resemail.user.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@Entity
@Table(name = "tb_auth")
@AllArgsConstructor
public class Auth extends BaseEntity implements UserDetails {
    public Auth() {}

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean enabled = true;

    @Column(name = "is_verified", nullable = false)
    @Builder.Default
    private Boolean verified = false;

    @OneToOne(mappedBy = "auth")
    @ToString.Exclude
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_auth_roles",
            joinColumns        = @JoinColumn(name = "auth_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "auth", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<RefreshToken> refreshTokens = new ArrayList<>();;



    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(
                role -> new SimpleGrantedAuthority(
                        role.getName()
                )
        ).collect(Collectors.toList());
    }

    public List<String> getSimpleAuthorities() {
        return this.roles.stream().map(Role::getName).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
