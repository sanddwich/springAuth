package spring.auth.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = {"http://localhost:3000"})
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
