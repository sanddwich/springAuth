package spring.auth.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

	private static final String SECRET_KEY =
	  "69caf277b2bf041e70723d2bc4c414552bba8b3cb514933a7a7ca73c5084adce";

	public String extractUsername(String jwtToken) {
		return extractClaim(jwtToken, Claims::getSubject);
	}

	public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(jwtToken);
		return claimsResolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(
	  Map<String, Object> claimList,
	  UserDetails userDetails
	) {
		return Jwts
		  .builder()
		  .setClaims(claimList)
		  .setSubject(userDetails.getUsername())
		  .setIssuedAt(new Date(System.currentTimeMillis()))
		  .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
		  .signWith(getSignInKey(), SignatureAlgorithm.HS256)
		  .compact();
	}

	public Claims extractAllClaims(String jwtToken) {
		return Jwts.
		  parserBuilder()
		  .setSigningKey(getSignInKey())
		  .build()
		  .parseClaimsJws(jwtToken)
		  .getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}
