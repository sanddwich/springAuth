package spring.auth.defaults;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import spring.auth.entities.AccessRole;
import spring.auth.entities.Privilege;
import spring.auth.entities.User;
import spring.auth.services.AccessRoleService;
import spring.auth.services.PrivilegeService;
import spring.auth.services.UserService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DataSetter {
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final AccessRoleService accessRoleService;
	private final PrivilegeService privilegeService;
	private List<AccessRole> accessRoleList;
	private List<Privilege> privilegeList;
	private User admin;

	@Bean
	private void createData() {
		createPrivilegeList();
		createAccessRoleList();
		createUser();
	}

	private void createUser() {
		User user = new User(
				"admin", "bck-dkiselev@yandex.ru", passwordEncoder.encode("admin"),
				true, "Denis", "Kiselev", accessRoleList
		);

		admin = getUser(user);
	}

	private User getUser(User user) {
		List<User> dbUserList = userService.findByUsername(user.getUsername());
		if (dbUserList.isEmpty()) return userService.save(user);

		return dbUserList.stream().findFirst().get();
	}

	private void createPrivilegeList() {
		List<Privilege> privilegeListTMP = Stream.of(
				new Privilege("ADMIN", "ADMIN", "ADMIN ACCESS")
		).collect(Collectors.toList());

		privilegeList = privilegeListTMP
				.stream()
				.map(this::getPrivilege)
				.toList();
	}

	private Privilege getPrivilege(Privilege privilege) {
		List<Privilege> dbPrivilegeList = privilegeService.findByCode(privilege.getCode());
		if(dbPrivilegeList.isEmpty()) return privilegeService.save(privilege);

		return dbPrivilegeList.stream().findFirst().get();
	}

	private void createAccessRoleList() {
		List<AccessRole> accessRoleListTMP = Stream.of(
				new AccessRole("ADMIN", "ADMIN", "ADMIN ROLE", privilegeList)
		).collect(Collectors.toList());

		accessRoleList = accessRoleListTMP
				.stream()
				.map(this::getAccessRole)
				.toList();
	}

	private AccessRole getAccessRole(AccessRole accessRole) {
		List<AccessRole> dbAccessRoleList = accessRoleService.findByCode(accessRole.getCode());
		if (dbAccessRoleList.isEmpty()) return accessRoleService.save(accessRole);

		return dbAccessRoleList.stream().findFirst().get();
	}
}
