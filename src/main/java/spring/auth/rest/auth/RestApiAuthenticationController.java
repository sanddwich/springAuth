package spring.auth.rest.auth;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.auth.config.JwtService;
import spring.auth.entities.User;
import spring.auth.rest.auth.dao.AuthenticationRequest;
import spring.auth.rest.auth.dao.AuthenticationResponse;
import spring.auth.rest.auth.dao.RegisterRequest;
import spring.auth.rest.auth.dao.UserOutput;
import spring.auth.services.UserService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RestApiAuthenticationController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping({"/", ""})
    public ResponseEntity index() throws Exception {
        return ResponseEntity.ok("Rest Api v1 Auth Hello!");
    }

    @PostMapping("/register")
    public ResponseEntity register(
            @RequestBody RegisterRequest registerRequest
    ) throws Exception {
        //var user...
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .active(true)
                .accessRoles(Collections.EMPTY_LIST)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        user = this.userService.save(user);

        if (user == null) throw new Exception("Save Error user");

        AuthenticationResponse authenticationResponse = generateAuthenticationResponse(
                jwtService.generateToken(user), user
        );

        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        User user = userService.findByUsername(authenticationRequest.getUsername()).stream().findFirst().get();
        AuthenticationResponse authenticationResponse = generateAuthenticationResponse(
          jwtService.generateToken(user), user
        );

        user.setAccessToken(authenticationResponse.getToken());
        userService.update(user);

        return ResponseEntity.ok(authenticationResponse);
    }

    private AuthenticationResponse generateAuthenticationResponse(String token, User user) throws Exception {
        UserOutput userOutput = UserOutput.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .privileges(
                        user
                                .getAuthorities()
                                .stream()
                                .map(grantedAuthority -> grantedAuthority.getAuthority())
                                .collect(Collectors.toList())
                )
                .build();

        return new AuthenticationResponse(
                token, userOutput
        );
    }
}
