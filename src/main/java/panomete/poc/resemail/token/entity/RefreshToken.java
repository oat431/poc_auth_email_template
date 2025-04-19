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

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "is_revoked")
    private Boolean isRevoked;

    @Column(name = "expired_date")
    private LocalDateTime expDate;

    @ManyToOne(fetch = FetchType.EAGER)
    Auth auth;
}
