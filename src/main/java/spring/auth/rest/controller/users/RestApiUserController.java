package spring.auth.rest.controller.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.auth.entities.User;
import spring.auth.rest.controller.dao.OperationUserRequest;
import spring.auth.rest.controller.dao.RequestSearchData;
import spring.auth.rest.controller.dao.UpdateUserRequest;
import spring.auth.rest.controller.inserters.UserInserter;
import spring.auth.rest.controller.mappers.UserMapper;
import spring.auth.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class RestApiUserController {
	private final UserService userService;
	private final UserMapper userMapper;
	private final UserInserter userInserter;

	@GetMapping({"/", ""})
	public ResponseEntity index() {
		Map<String, Object> result = new HashMap<>();
		result.put("message", "/api/v1/users Controller");

		return ResponseEntity.ok(result);
	}

	@RequestMapping(method = {RequestMethod.POST}, value = {"/add"})
	public ResponseEntity addUser(
	  @RequestBody OperationUserRequest operationUserRequest
	) throws Exception {
		Map<String, User> response = new HashMap<>();
		User user = this.userInserter.insertUser(operationUserRequest);
		response.put("user", user);

		return ResponseEntity.ok(response);
	}

	@RequestMapping(method = {RequestMethod.POST}, value = {"/delete"})
	public ResponseEntity delete(
	  @RequestBody UpdateUserRequest updateUserRequest
	) throws Exception {
		List<User> userList = this.userService.findById(updateUserRequest.getId()).stream().toList();
		if (userList.isEmpty()) throw new Exception(
		  "User with ID'" + updateUserRequest.getId().toString() + "' is not founded"
		);

		User user = this.userService.delete(userList.stream().findFirst().get());
		if (user == null) throw new Exception(
		  "User with ID'" + updateUserRequest.getId().toString() + "' DELETE ERROR"
		);

		Map<String, User> response = new HashMap<>();
		response.put("user", user);

		return ResponseEntity.ok(response);
	}

	@RequestMapping(method = {RequestMethod.PATCH}, value = {"/patch"})
	public ResponseEntity patchUser(
	  @RequestBody UpdateUserRequest updateUserRequest
	) throws Exception {
		Map<String, User> response = new HashMap<>();
		User user = this.userMapper.mapUser(updateUserRequest);
		response.put("user", user);

		return ResponseEntity.ok(response);
	}

	@GetMapping({"/get_all", "/get_all/"})
	public ResponseEntity getAll() {
		Map<String, List<User>> response = new HashMap<>();
		response.put("users", this.userService.findAll());

		return ResponseEntity.ok(response);
	}

	@RequestMapping(method = {RequestMethod.POST}, value = {"/find"})
	public ResponseEntity find(
	  @RequestBody RequestSearchData requestSearchData
	) throws Exception {
		Map<String, List<User>> response = new HashMap<>();
		List<User> userList = requestSearchData.getSearchTerm().equals("")
		  ? this.userService.findAll()
		  : this.userService.search(requestSearchData.getSearchTerm());
		response.put("users", userList);

		return ResponseEntity.ok(response);
	}
}
