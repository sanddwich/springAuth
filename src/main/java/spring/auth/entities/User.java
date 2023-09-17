package spring.auth.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "usernameIndex", columnList = "username"),
        @Index(name = "emailIndex", columnList = "email")
})
public class User extends AbstractEntity implements UserDetails {

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @NotEmpty
    private String password;

    @NotNull
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER
//	  cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinTable(
            name = "user_access_role_lnk",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<AccessRole> accessRoles;

    @Override
    public String toString() {
        return "\nUser{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", accessRoles=" + accessRoles +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<String> privilegesStringList = accessRoles
//          .stream()
//          .map(AccessRole::getPrivileges)
//          .flatMap(Collection::stream)
//          .map(privilege -> privilege.getCode())
//          .collect(Collectors.toList());

        List<SimpleGrantedAuthority> privilegeList = accessRoles
          .stream()
          .map(AccessRole::getPrivileges)
          .flatMap(Collection::stream)
          .map(privilege -> new SimpleGrantedAuthority(privilege.getCode()))
          .collect(Collectors.toList());

        return privilegeList;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
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
        return true;
    }
}
