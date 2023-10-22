package spring.auth.rest.controller.inserters;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import spring.auth.entities.AccessRole;
import spring.auth.entities.Privilege;
import spring.auth.entities.User;
import spring.auth.rest.controller.dao.OperationAccessRoleRequest;
import spring.auth.rest.controller.dao.OperationUserRequest;
import spring.auth.rest.controller.dao.UpdateAccessRoleRequest;
import spring.auth.rest.controller.dao.UpdatePrivilegeRequest;
import spring.auth.services.AccessRoleService;
import spring.auth.services.PrivilegeService;
import spring.auth.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccessRoleInserter {
	private final PrivilegeService privilegeService;
	private final AccessRoleService accessRoleService;
	private final Integer fieldLength = 3;

	public AccessRole insertAccessRole(OperationAccessRoleRequest operationUserRequest) throws Exception {
		accessRoleCheck(operationUserRequest);
		AccessRole accessRole = AccessRole.builder()
		  .code(operationUserRequest.getCode())
		  .name(operationUserRequest.getName())
		  .description(operationUserRequest.getDescription())
		  .privileges(getPrivileges(operationUserRequest))
		  .build();

		this.accessRoleService.save(accessRole);

		return accessRole;
	}

	private List<Privilege> getPrivileges(OperationAccessRoleRequest operationAccessRoleRequest) throws Exception {
		List<Privilege> privilegeList = new ArrayList<>();
		for(UpdatePrivilegeRequest updatePrivilegeRequest: operationAccessRoleRequest.getPrivileges()) {
			List<Privilege> privilegeListTmp = this.privilegeService.findById(
			  updatePrivilegeRequest.getId()
			).stream().toList();
			if (privilegeListTmp.isEmpty()) throw new Exception(
			  "Privilege: " + updatePrivilegeRequest.getCode() + " does not exist!"
			);
			privilegeList.add(privilegeListTmp.stream().findFirst().get());
		}

		return privilegeList;
	}

	private void accessRoleCheck(OperationAccessRoleRequest operationAccessRoleRequest) throws Exception {
		checkName(operationAccessRoleRequest.getName());
		checkCode(operationAccessRoleRequest.getCode());
		checkDescription(operationAccessRoleRequest.getDescription());
	}

	private void checkName(String name) throws Exception {
		if (name == null || name == "" || name.length() < this.fieldLength) throw new Exception(
		  "Empty name or few characters!"
		);

		List<AccessRole> accessRoleList = this.accessRoleService.findByName(name);
		if (!accessRoleList.isEmpty()) throw new Exception(
		  "Other access role with name '" + name + "' already exist!"
		);
	}

	private void checkCode(String code) throws Exception {
		if (code == null || code == "" || code.length() < this.fieldLength) throw new Exception(
		  "Empty code or few characters!"
		);

		List<AccessRole> accessRoleList = this.accessRoleService.findByCode(code);
		if (!accessRoleList.isEmpty()) throw new Exception(
		  "Other access role with code '" + code + "' already exist!"
		);
	}

	private void checkDescription(String description) throws Exception {
		if (description == null || description == "") throw new Exception(
		  "Empty description!"
		);
	}
}
