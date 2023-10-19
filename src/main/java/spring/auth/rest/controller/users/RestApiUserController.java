package spring.auth.rest.controller.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.auth.entities.User;
import spring.auth.rest.controller.dao.OperationUserRequest;
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
        System.out.println(operationUserRequest.toString());
//        Map<String, User> response = new HashMap<>();
//        User user = this.userInserter.insertUser(operationUserRequest);
//        response.put("user", user);

        return ResponseEntity.ok("addUser");
    }

    @RequestMapping(method = {RequestMethod.PATCH}, value = {"/{id}", "{id}"})
    public ResponseEntity patchUser(
            @PathVariable String id,
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
}
