package spring.auth.rest.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import spring.auth.config.JwtService;
import spring.auth.entities.AccessRole;
import spring.auth.entities.User;
import spring.auth.rest.auth.dao.AuthenticationRequest;
import spring.auth.rest.auth.dao.AuthenticationResponse;
import spring.auth.rest.auth.dao.RegisterRequest;
import spring.auth.rest.auth.dao.UserOutput;
import spring.auth.services.UserService;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
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

	@GetMapping({"/", ""})
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

		AuthenticationResponse authenticationResponse = generateAuthenticationResponse(
		  jwtService.generateToken(user), user
		);

		return ResponseEntity.ok(authenticationResponse);
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

		AuthenticationResponse authenticationResponse = generateAuthenticationResponse(
		  jwtService.generateToken(user), user
		);

		return ResponseEntity.ok(authenticationResponse);
	}

	public void updateToken(String token) {
	}

	private AuthenticationResponse generateAuthenticationResponse(String token, User user) {
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
