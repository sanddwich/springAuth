package spring.auth.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/")
@RequiredArgsConstructor
public class RestApiAdminController {

    @GetMapping({"", "/"})
    public ResponseEntity index() {
        return ResponseEntity.ok("RestApiAdminController");
    }
}
