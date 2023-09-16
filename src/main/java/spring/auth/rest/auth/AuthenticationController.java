package spring.auth.rest.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.auth.rest.auth.dao.AuthenticationRequest;
import spring.auth.rest.auth.dao.AuthenticationResponse;
import spring.auth.rest.auth.dao.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
	  @RequestBody RegisterRequest registerRequest
	) {
		return null;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> register(
	  @RequestBody AuthenticationRequest registerRequest
	) {
		return null;
	}
}
