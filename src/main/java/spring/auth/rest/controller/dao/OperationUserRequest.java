package spring.auth.rest.controller.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationUserRequest {
	private Date createdDate;
	private Date modifiedDate;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String email;
	private boolean active;
	private List<UpdateAccessRoleRequest> accessRoles;
}
