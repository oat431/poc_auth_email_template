package panomete.poc.resemail.security.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import panomete.poc.resemail.common.entity.BaseEntity;
import panomete.poc.resemail.role.entity.Role;
import panomete.poc.resemail.token.entity.RefreshToken;
import panomete.poc.resemail.user.entity.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "tb_auth")
public class Auth extends BaseEntity implements UserDetails {
    public Auth() {}

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Builder.Default
    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @OneToOne(mappedBy = "auth")
    User user;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Role> roles;

    @OneToMany(mappedBy="auth")
    @ToString.Exclude
    List<RefreshToken> refreshTokens;

    @Override
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
        return isEnabled;
    }
}
