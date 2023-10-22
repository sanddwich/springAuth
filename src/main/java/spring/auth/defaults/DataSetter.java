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

import java.util.ArrayList;
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
	private User user;

	@Bean
	private void createData() {
		String admin = "ADMIN";
		String user = "USER";
		createPrivilegeList(admin);
		createAccessRoleList(admin);
		createAdmin();

		createPrivilegeList(user);
		createAccessRoleList(user);
		createUser();
	}

	private void createAdmin() {
		User user = new User(
				"admin", "bck-dkiselev@yandex.ru", passwordEncoder.encode("admin"),
				true, "Denis", "Kiselev", null, accessRoleList
		);

		this.admin = getUser(user);
	}

	private void createUser() {
		User user = new User(
		  "user", "user@yandex.ru", passwordEncoder.encode("user"),
		  true, "Иван", "Иванов", null, accessRoleList
		);

		this.user = getUser(user);
	}

	private User getUser(User user) {
		List<User> dbUserList = userService.findByUsername(user.getUsername());
		if (dbUserList.isEmpty()) return userService.save(user);

		return dbUserList.stream().findFirst().get();
	}

	private void createPrivilegeList(String code) {
		List<Privilege> privilegeListTMP = Stream.of(
		  Privilege.builder()
		    .code(code)
		    .name(code)
		    .description(code)
		    .build()
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

	private void createAccessRoleList(String code) {
		List<AccessRole> accessRoleListTMP = Stream.of(
				AccessRole.builder()
				  .name(code)
				  .code(code)
				  .description(code)
				  .privileges(privilegeList)
				  .build()
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
