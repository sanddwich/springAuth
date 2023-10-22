package spring.auth.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(name = "nameIndex", columnList = "name"),
        @Index(name = "codeIndex", columnList = "code"),
        @Index(name = "descriptionIndex", columnList = "description")
})
public class Privilege extends AbstractEntity {
    @NotEmpty
    @Column(unique = true)
    private String name;

    @NotEmpty
    @Column(unique = true)
    private String code;

    @NotEmpty
    private String description;

    //    @JsonIgnore
//    @ManyToMany(mappedBy = "privileges")
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "access_role_privilege_lnk",
            joinColumns = {@JoinColumn(name = "privilege_id")},
            inverseJoinColumns = {@JoinColumn(name = "access_role_id")}
    )
    private List<AccessRole> roles = new ArrayList<>();

    @Override
    public String toString() {
        return "\nPrivilege{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
