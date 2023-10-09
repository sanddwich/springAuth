package spring.auth.rest.controller.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class RestApiUserController {
    @GetMapping({"/", ""})
    public ResponseEntity index() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "/api/v1/users Controller");

        return ResponseEntity.ok(result);
    }
}
