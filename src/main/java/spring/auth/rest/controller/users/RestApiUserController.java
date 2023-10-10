package spring.auth.rest.controller.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.auth.entities.User;
import spring.auth.services.UserService;

import java.util.Arrays;
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

    @GetMapping({"/get_all", "/get_all/"})
    public ResponseEntity getAll() {
        Map<String, List<User>> response = new HashMap<>();
        response.put("users", this.userService.findAll());

        return ResponseEntity.ok(response);
    }
}
