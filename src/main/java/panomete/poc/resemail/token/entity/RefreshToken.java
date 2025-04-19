package panomete.poc.resemail.token.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import panomete.poc.resemail.common.entity.BaseEntity;
import panomete.poc.resemail.security.entity.Auth;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@SuperBuilder
@Table(name = "tb_refresh_token")
public class RefreshToken extends BaseEntity {
    public RefreshToken() {}


    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "exp_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "is_revoke")
    private Boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id", nullable = false)
    @ToString.Exclude
    private Auth auth;
}
