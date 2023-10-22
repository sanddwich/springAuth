package spring.auth.rest.controller.inserters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.auth.entities.AccessRole;
import spring.auth.entities.Privilege;
import spring.auth.rest.controller.dao.OperationAccessRoleRequest;
import spring.auth.rest.controller.dao.OperationPrivilegeRequest;
import spring.auth.rest.controller.dao.UpdatePrivilegeRequest;
import spring.auth.services.AccessRoleService;
import spring.auth.services.PrivilegeService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PrivilegeInserter {
	private final PrivilegeService privilegeService;
	private final Integer fieldLength = 3;

	public Privilege insertPrivilege(OperationPrivilegeRequest operationPrivilegeRequest) throws Exception {
		privilegeCheck(operationPrivilegeRequest);
		Privilege privilege = Privilege.builder()
		  .code(operationPrivilegeRequest.getCode())
		  .name(operationPrivilegeRequest.getName())
		  .description(operationPrivilegeRequest.getDescription())
		  .build();

		this.privilegeService.save(privilege);

		return privilege;
	}

	private void privilegeCheck(OperationPrivilegeRequest operationPrivilegeRequest) throws Exception {
		checkName(operationPrivilegeRequest.getName());
		checkCode(operationPrivilegeRequest.getCode());
		checkDescription(operationPrivilegeRequest.getDescription());
	}

	private void checkName(String name) throws Exception {
		if (name == null || name == "" || name.length() < this.fieldLength) throw new Exception(
		  "Empty name or few characters!"
		);

		List<Privilege> privilegeList = this.privilegeService.findByName(name);
		if (!privilegeList.isEmpty()) throw new Exception(
		  "Other privilege with name '" + name + "' already exist!"
		);
	}

	private void checkCode(String code) throws Exception {
		if (code == null || code == "" || code.length() < this.fieldLength) throw new Exception(
		  "Empty code or few characters!"
		);

		List<Privilege> privilegeList = this.privilegeService.findByCode(code);
		if (!privilegeList.isEmpty()) throw new Exception(
		  "Other privilege with code '" + code + "' already exist!"
		);
	}

	private void checkDescription(String description) throws Exception {
		if (description == null || description == "") throw new Exception(
		  "Empty description!"
		);
	}
}
