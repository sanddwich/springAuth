package spring.auth.rest.controller.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.auth.entities.User;
import spring.auth.rest.controller.dao.UpdateUserRequest;
import spring.auth.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class RestApiUserController {
    private final UserService userService;

    @GetMapping({"/", ""})
    public ResponseEntity index() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "/api/v1/users Controller");

        return ResponseEntity.ok(result);
    }

    @RequestMapping(method = {RequestMethod.PATCH}, value = {"/{id}", "{id}"})
    public ResponseEntity patchUser(
      @PathVariable String id,
      @RequestBody UpdateUserRequest updateUserRequest
    ) {
        System.out.println("updateUserRequest: " + updateUserRequest.toString());

        return ResponseEntity.ok("User is patched!");
    }

    @GetMapping({"/get_all", "/get_all/"})
    public ResponseEntity getAll() {
        Map<String, List<User>> response = new HashMap<>();
        response.put("users", this.userService.findAll());

        return ResponseEntity.ok(response);
    }
}
