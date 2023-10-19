package spring.auth.rest.controller.inserters;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import spring.auth.entities.AccessRole;
import spring.auth.entities.User;
import spring.auth.rest.controller.dao.OperationUserRequest;
import spring.auth.rest.controller.dao.UpdateAccessRoleRequest;
import spring.auth.services.AccessRoleService;
import spring.auth.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserInserter {
	UserService userService;
	AccessRoleService accessRoleService;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	Integer passwordLength = 4;

	public User insertUser(OperationUserRequest operationUserRequest) throws Exception {
		userCheck(operationUserRequest);
		User user = User.builder()
		  .name(operationUserRequest.getName())
		  .surname(operationUserRequest.getSurname())
		  .username(operationUserRequest.getUsername())
		  .password(this.bCryptPasswordEncoder.encode(operationUserRequest.getPassword()))
		  .active(operationUserRequest.isActive())
		  .email(operationUserRequest.getEmail())
		  .accessRoles(this.getAccessRoles(operationUserRequest))
		  .build();

		return user;
	}

	public List<AccessRole> getAccessRoles(OperationUserRequest operationUserRequest) throws Exception {
		List<AccessRole> accessRoleList = new ArrayList<>();
		for(UpdateAccessRoleRequest updateAccessRoleRequest: operationUserRequest.getAccessRoles()) {
			List<AccessRole> accessRoleListTmp = this.accessRoleService.findById(
			  updateAccessRoleRequest.getId()
			).stream().toList();
			if (accessRoleListTmp.isEmpty()) throw new Exception(
			  "AccessRole: " + updateAccessRoleRequest.getCode() + " does not exist!"
			);
			accessRoleList.add(accessRoleListTmp.stream().findFirst().get());
		}

		return accessRoleList;
	}

	public void userCheck(OperationUserRequest operationUserRequest) throws Exception {
		checkUsername(operationUserRequest.getUsername());
		checkEmail(operationUserRequest.getEmail());
		checkPassword(operationUserRequest.getPassword());
		checkName(operationUserRequest.getName());
		checkSurname(operationUserRequest.getSurname());
	}

	public void checkUsername(String username) throws Exception {
		if (username == null || username == "") throw new Exception(
		  "Empty username!"
		);

		List<User> userList = this.userService.findByUsername(username);
		if (!userList.isEmpty()) throw new Exception(
		  "Other user with username '" + username + "' already exist!"
		);
	}

	public void checkEmail(String email) throws Exception {
		if (email == null || email == "") throw new Exception(
		  "Empty email!"
		);

		List<User> userList = this.userService.findByUsername(email);
		if (!userList.isEmpty()) throw new Exception(
		  "Other user with email '" + email + "' already exist!"
		);
	}

	public void checkPassword(String password) throws Exception {
		if (password == null || password == "") throw new Exception(
		  "Empty password!"
		);

		if (password.length() < this.passwordLength) throw new Exception(
		  "Password contains less than " + this.passwordLength + " characters"
		);
	}

	public void checkName(String name) throws Exception {
		if (name == null || name == "") throw new Exception(
		  "Empty name!"
		);
	}

	public void checkSurname(String surname) throws Exception {
		if (surname == null || surname == "") throw new Exception(
		  "Empty surname!"
		);
	}
}
