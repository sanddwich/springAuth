package spring.auth.rest.controller.privileges;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.auth.entities.AccessRole;
import spring.auth.entities.Privilege;
import spring.auth.services.PrivilegeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/privileges")
@RequiredArgsConstructor
public class RestApiPrivilegeController {
	private final PrivilegeService privilegeService;

	@GetMapping({"/", ""})
	public ResponseEntity index() {
		Map<String, Object> result = new HashMap<>();
		result.put("message", "/api/v1/privileges Controller");

		return ResponseEntity.ok(result);
	}

	@GetMapping({"/get_all", "get_all"})
	public ResponseEntity getAll() {
		Map<String, List<Privilege>> result = new HashMap<>();
		result.put("privileges", this.privilegeService.findAll());

		return ResponseEntity.ok(result);
	}
}
