package spring.auth.rest.controller.privileges;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.auth.entities.AccessRole;
import spring.auth.entities.Privilege;
import spring.auth.rest.controller.dao.*;
import spring.auth.rest.controller.inserters.PrivilegeInserter;
import spring.auth.rest.controller.mappers.PrivilegeMapper;
import spring.auth.services.PrivilegeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/privileges")
@RequiredArgsConstructor
public class RestApiPrivilegeController {
	private final PrivilegeService privilegeService;
	private final PrivilegeMapper privilegeMapper;
	private final PrivilegeInserter privilegeInserter;

	@GetMapping({"/", ""})
	public ResponseEntity index() {
		Map<String, Object> result = new HashMap<>();
		result.put("message", "/api/v1/accessRoles Controller");

		return ResponseEntity.ok(result);
	}

	@RequestMapping(method = {RequestMethod.GET}, value = {"{id}", "/{id}"})
	public ResponseEntity get_privilege(
	  @PathVariable Integer id
	) throws Exception {
		List<Privilege> privilegeList = this.privilegeService.findById(id).stream().toList();
		if (privilegeList.isEmpty()) throw new Exception("Privilege is not found!");
		Map<String, Privilege> response = new HashMap<>();
		response.put("privilege", privilegeList.stream().findFirst().get());

		return ResponseEntity.ok(response);
	}

	@RequestMapping(method = {RequestMethod.PATCH}, value = {"/patch"})
	public ResponseEntity patch_access_role(
	  @RequestBody UpdatePrivilegeRequest updatePrivilegeRequest
	  ) throws Exception {
		Privilege privilege = this.privilegeMapper.mapPrivilege(updatePrivilegeRequest);
		Map<String, Privilege> response = new HashMap<>();
		response.put("privilege", privilege);

		return ResponseEntity.ok(response);
	}

	@RequestMapping(method = {RequestMethod.PATCH}, value = {"/add"})
	public ResponseEntity addAccessRole(
	  @RequestBody OperationPrivilegeRequest operationPrivilegeRequest
	  ) throws Exception {
		Privilege privilege = this.privilegeInserter.insertPrivilege(operationPrivilegeRequest);
		Map<String, Privilege> response = new HashMap<>();
		response.put("privilege", privilege);

		return ResponseEntity.ok(response);
	}

	@RequestMapping(method = {RequestMethod.POST}, value = {"/find"})
	public ResponseEntity find(
	  @RequestBody RequestSearchData requestSearchData
	) throws Exception {
		Map<String, List<Privilege>> response = new HashMap<>();
		List<Privilege> privilegeList = requestSearchData.getSearchTerm().equals("")
		  ? this.privilegeService.findAll()
		  : this.privilegeService.search(requestSearchData.getSearchTerm());
		response.put("privileges", privilegeList);

		return ResponseEntity.ok(response);
	}

	@GetMapping({"/get_all", "get_all"})
	public ResponseEntity getAll() throws Exception{
		Map<String, List<Privilege>> result = new HashMap<>();
		result.put("privileges", this.privilegeService.findAll());

		return ResponseEntity.ok(result);
	}
}
