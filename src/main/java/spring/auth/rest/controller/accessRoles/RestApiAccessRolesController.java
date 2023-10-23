package spring.auth.rest.controller.accessRoles;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.auth.entities.AccessRole;
import spring.auth.entities.Privilege;
import spring.auth.entities.User;
import spring.auth.rest.controller.dao.OperationAccessRoleRequest;
import spring.auth.rest.controller.dao.RequestSearchData;
import spring.auth.rest.controller.dao.UpdateAccessRoleRequest;
import spring.auth.rest.controller.dao.UpdateUserRequest;
import spring.auth.rest.controller.inserters.AccessRoleInserter;
import spring.auth.rest.controller.mappers.AccessRoleMapper;
import spring.auth.services.AccessRoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/access_roles")
@RequiredArgsConstructor
public class RestApiAccessRolesController {
	private final AccessRoleService accessRoleService;
	private final AccessRoleMapper accessRoleMapper;
	private final AccessRoleInserter accessRoleInserter;

	@GetMapping({"/", ""})
	public ResponseEntity index() {
		Map<String, Object> result = new HashMap<>();
		result.put("message", "/api/v1/accessRoles Controller");

		return ResponseEntity.ok(result);
	}

	@RequestMapping(method = {RequestMethod.GET}, value={"/get/{id}"})
	public ResponseEntity getAccessRole(
	  @PathVariable Integer id
	) throws Exception {
		List<AccessRole> accessRoleList = this.accessRoleService.findById(id).stream().toList();
		if (accessRoleList.isEmpty()) throw new Exception(
		  "AccessRole with id: " + id.toString() + " is not found!"
		);

		Map<String, AccessRole> response = new HashMap<>();
		response.put("accessRole", accessRoleList.stream().findFirst().get());

		return ResponseEntity.ok(response);
	}

	@RequestMapping(method = {RequestMethod.PATCH}, value = {"/patch"})
	public ResponseEntity patchAccessRole(
	  @RequestBody UpdateAccessRoleRequest updateAccessRoleRequest
	) throws Exception {
		AccessRole accessRole = this.accessRoleMapper.mapAccessRole(updateAccessRoleRequest);
		Map<String, AccessRole> response = new HashMap<>();
		response.put("accessRole", accessRole);

		return ResponseEntity.ok(response);
	}

	@RequestMapping(method = {RequestMethod.POST}, value = {"/delete"})
	public ResponseEntity delete(
	  @RequestBody UpdateAccessRoleRequest updateAccessRoleRequest
	) throws Exception {
		List<AccessRole> accessRoleList = this.accessRoleService.findById(updateAccessRoleRequest.getId())
		  .stream().toList();
		if (accessRoleList.isEmpty()) throw new Exception(
		  "AccessRole with ID'" + updateAccessRoleRequest.getId().toString() + "' is not founded"
		);

		AccessRole accessRole = this.accessRoleService.delete(accessRoleList.stream().findFirst().get());
		if (accessRole == null) throw new Exception(
		  "AccessRole with ID'" + updateAccessRoleRequest.getId().toString() + "' DELETE ERROR"
		);

		Map<String, AccessRole> response = new HashMap<>();
		response.put("accessRole", accessRole);

		return ResponseEntity.ok(response);
	}

	@RequestMapping(method = {RequestMethod.POST}, value = {"/add"})
	public ResponseEntity addAccessRole(
	  @RequestBody OperationAccessRoleRequest operationAccessRoleRequest
	) throws Exception {
		AccessRole accessRole = this.accessRoleInserter.insertAccessRole(operationAccessRoleRequest);
		Map<String, AccessRole> response = new HashMap<>();
		response.put("accessRole", accessRole);

		return ResponseEntity.ok(response);
	}

	@RequestMapping(method = {RequestMethod.POST}, value = {"/find"})
	public ResponseEntity find(
	  @RequestBody RequestSearchData requestSearchData
	) throws Exception {
		Map<String, List<AccessRole>> response = new HashMap<>();
		List<AccessRole> accessRoleList = requestSearchData.getSearchTerm().equals("")
		  ? this.accessRoleService.findAll()
		  : this.accessRoleService.search(requestSearchData.getSearchTerm());
		response.put("accessRoles", accessRoleList);

		return ResponseEntity.ok(response);
	}

	@GetMapping({"/get_all", "get_all"})
	public ResponseEntity getAll() throws Exception{
		Map<String, List<AccessRole>> result = new HashMap<>();
		result.put("accessRoles", this.accessRoleService.findAll());

		return ResponseEntity.ok(result);
	}
}
