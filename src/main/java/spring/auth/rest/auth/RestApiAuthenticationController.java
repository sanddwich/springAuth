package spring.auth.rest.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.auth.config.JwtService;
import spring.auth.entities.AccessRole;
import spring.auth.entities.User;
import spring.auth.rest.auth.dao.AuthenticationRequest;
import spring.auth.rest.auth.dao.AuthenticationResponse;
import spring.auth.rest.auth.dao.RegisterRequest;
import spring.auth.services.UserService;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class RestApiAuthenticationController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/")
    public ResponseEntity index() {
        return ResponseEntity.ok("Rest Api v1 Auth Hello!");
    }

    @PostMapping("/register")
    public ResponseEntity register(
            @RequestBody RegisterRequest registerRequest
    ) {
        //var user...
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .active(true)
                .accessRoles(Collections.EMPTY_LIST)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        userService.save(user);
        String jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(jwtToken);
    }

    @PostMapping("/authenticate")
    public ResponseEntity register(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        User user = userService.findByUsername(authenticationRequest.getUsername()).stream().findFirst().get();
        String jwtToken = jwtService.generateToken(user);

        return ResponseEntity.ok(jwtToken);
    }
}
