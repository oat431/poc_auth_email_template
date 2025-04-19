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
    public User() {}

    @Column(name = "firstname", nullable = false, columnDefinition = "TEXT")
    private String username;

    @Column(name = "lastname", columnDefinition = "TEXT")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    Auth auth;
}
