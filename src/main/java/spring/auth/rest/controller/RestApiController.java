package spring.auth.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

    @GetMapping("/getExample")
    public ResponseEntity getExample() {
        return ResponseEntity.ok("getExample");
    }

    @PostMapping("/postExample")
    public ResponseEntity postExample() {
        return ResponseEntity.ok("postExample");
    }
}
