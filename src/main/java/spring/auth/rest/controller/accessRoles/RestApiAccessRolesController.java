package spring.auth.rest.controller.accessRoles;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.auth.entities.AccessRole;
import spring.auth.entities.Privilege;
import spring.auth.services.AccessRoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/access_roles")
@RequiredArgsConstructor
public class RestApiAccessRolesController {
	private final AccessRoleService accessRoleService;

	@GetMapping({"/", ""})
	public ResponseEntity index() {
		Map<String, Object> result = new HashMap<>();
		result.put("message", "/api/v1/accessRoles Controller");

		return ResponseEntity.ok(result);
	}

	@GetMapping({"/get_all", "get_all"})
	public ResponseEntity getAll() {
		Map<String, List<AccessRole>> result = new HashMap<>();
		result.put("accessRoles", this.accessRoleService.findAll());

		return ResponseEntity.ok(result);
	}
}
