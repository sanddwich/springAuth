package spring.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.auth.entities.User;
import spring.auth.services.UserService;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final UserService userService;

	@Override
	protected void doFilterInternal(
	  @NonNull HttpServletRequest request,
	  @NonNull HttpServletResponse response,
	  @NonNull FilterChain filterChain
	) throws ServletException, IOException {
		final String authenticationHeader = request.getHeader("Authorization");
		final String jwtToken;
		final String username;

		if (authenticationHeader == null || !authenticationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		jwtToken = authenticationHeader.substring(7);
		username = getUsernameFromJWT(jwtToken);
		User dbUser = getDBUser(username);

		if (username != null && dbUser != null) {
			if (!dbUser.getAccessToken().equals(jwtToken)) {
				filterChain.doFilter(request, response);
				return;
			}

			if (SecurityContextHolder.getContext().getAuthentication() == null) {
				securityAuthenticate(username, jwtToken, request);
			}
		}

		filterChain.doFilter(request, response);
	}

	private void securityAuthenticate(String username, String jwtToken, HttpServletRequest request) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

		if (jwtService.isTokenValid(jwtToken, userDetails)) {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			  userDetails.getUsername(),
			  null,
			  userDetails.getAuthorities()
			);
			authenticationToken.setDetails(
			  new WebAuthenticationDetailsSource().buildDetails(request)
			);

			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
	}

	private String getUsernameFromJWT(String token) {
		try {
			return jwtService.extractUsername(token);
		} catch(Exception e) {}

		return null;
	}

	private User getDBUser(String username) {
		if (username != null) {
			List<User> userList = userService.findByUsername(username);
			if (!userList.isEmpty()) return userList.stream().findFirst().get();
		}

		return null;
	}
}
