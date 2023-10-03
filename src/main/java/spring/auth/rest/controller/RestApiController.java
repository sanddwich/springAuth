package spring.auth.rest.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.auth.config.JwtService;
import spring.auth.rest.auth.dao.AuthenticationRequest;
import spring.auth.rest.auth.dao.TokenRequest;
import spring.auth.services.UserService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class RestApiController {
    private final JwtService jwtService;

    @GetMapping("/getExample")
    public ResponseEntity getExample() {
        return ResponseEntity.ok("getExample");
    }

    @PostMapping("/postExample")
    public ResponseEntity postExample() {
        return ResponseEntity.ok("postExample");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/getClaims")
    public ResponseEntity getClaims(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        Claims claims = jwtService.extractAllClaims(token);

        return ResponseEntity.ok(claims);
    }
}
