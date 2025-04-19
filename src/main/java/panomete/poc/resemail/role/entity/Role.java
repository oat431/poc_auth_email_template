package panomete.poc.resemail.role.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import panomete.poc.resemail.common.entity.BaseEntity;
import panomete.poc.resemail.security.entity.Auth;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@SuperBuilder
@Table(name = "tb_role")
public class Role extends BaseEntity {
    public Role() {}

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Auth> auths = new ArrayList<>();
}
