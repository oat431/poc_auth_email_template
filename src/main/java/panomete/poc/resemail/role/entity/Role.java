package panomete.poc.resemail.role.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import panomete.poc.resemail.common.entity.BaseEntity;
import panomete.poc.resemail.security.entity.Auth;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
@SuperBuilder
@Table(name = "tb_role")
public class Role extends BaseEntity {
    public Role() {}

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    List<Auth> auths;
}
