package panomete.poc.resemail.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import panomete.poc.resemail.common.entity.BaseEntity;
import panomete.poc.resemail.security.entity.Auth;

@Getter
@Setter
@ToString
@Entity
@SuperBuilder
@Table(name = "tb_user")
public class User extends BaseEntity {
    public User(Auth auth) {}

    @Column(nullable = false)
    private String firstname;

    @Column
    private String lastname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id", nullable = false)
    @ToString.Exclude
    private Auth auth;
}
