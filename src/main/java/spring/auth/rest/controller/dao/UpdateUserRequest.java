package spring.auth.rest.controller.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.auth.entities.User;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
	private Integer id;
	private Date createdDate;
	private Date modifiedDate;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String email;
	private boolean active;
}
