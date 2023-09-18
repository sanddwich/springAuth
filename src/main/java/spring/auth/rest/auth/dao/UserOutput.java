package spring.auth.rest.auth.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import spring.auth.entities.Privilege;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOutput {
	private String username;
	private String name;
	private String surname;
	private String email;
	private List<String> privileges;
}
