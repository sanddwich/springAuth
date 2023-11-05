package spring.auth.repositories;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.auth.entities.AccessRole;
import spring.auth.entities.User;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {
	@Autowired
	private UserRepository userRepository;
	private String userName = "TestUserName";
	private String userUsername = "TestUserUsername";
	private String userSurname = "TestUserSurname";
	private String userEmail = "TestUserEmail@mail.ru";
	private String userAccessToken = "TestUserAccessToken";
	private String userPassword = "TestUserPassword";
	private boolean userActive = true;

	private void createTestUserAtDB() {
		User user = User.builder()
		  .name(userName)
		  .username(userUsername)
		  .password(userPassword)
		  .surname(userSurname)
		  .email(userEmail)
		  .active(userActive)
		  .accessToken(userAccessToken)
		  .accessRoles(Collections.emptyList())
		  .build();

		userRepository.save(user);
	}

	private void checkAssertions(List<User> userList) {
		assertTrue(!userList.isEmpty());
		assertTrue(userList.stream().findFirst()
		  .map(User::getName)
		  .map(el -> el.equals(userName))
		  .orElse(false)
		);
		assertTrue(userList.stream().findFirst()
		  .map(User::getUsername)
		  .map(el -> el.equals(userUsername))
		  .orElse(false)
		);
		assertTrue(userList.stream().findFirst()
		  .map(User::getEmail)
		  .map(el -> el.equals(userEmail))
		  .orElse(false)
		);
		assertTrue(userList.stream().findFirst()
		  .map(User::getSurname)
		  .map(el -> el.equals(userSurname))
		  .orElse(false)
		);
		assertTrue(userList.stream().findFirst()
		  .map(User::getAccessToken)
		  .map(el -> el.equals(userAccessToken))
		  .orElse(false)
		);
		assertTrue(userList.stream().findFirst()
		  .map(User::isActive)
		  .map(el -> el.equals(userActive))
		  .orElse(false)
		);
	}

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void findByUsername() {
		//given
		createTestUserAtDB();

		//when
		List<User> userList = userRepository.findByUsername(userUsername);

		//then
		checkAssertions(userList);
	}

	@Test
	void findByEmail() {
		//given
		createTestUserAtDB();

		//when
		List<User> userList = userRepository.findByEmail(userEmail);

		//then
		checkAssertions(userList);
	}

	@Test
	void search() {
		//given
		createTestUserAtDB();

		//when
		List<User> userList = userRepository.search("User");

		//then
		checkAssertions(userList);
	}

	@Test
	void findByUsernameOrEmail() {
		//given
		createTestUserAtDB();

		//when
		List<User> userList = userRepository.findByUsernameOrEmail(userUsername, userEmail);

		//then
		checkAssertions(userList);
	}

	@Test
	void findByName() {
		//given
		createTestUserAtDB();

		//when
		List<User> userList = userRepository.findByName(userName);

		//then
		checkAssertions(userList);
	}

	@Test
	void findBySurname() {
		//given
		createTestUserAtDB();

		//when
		List<User> userList = userRepository.findBySurname(userSurname);

		//then
		checkAssertions(userList);
	}

	@Test
	void findByAccessToken() {
		//given
		createTestUserAtDB();

		//when
		List<User> userList = userRepository.findByAccessToken(userAccessToken);

		//then
		checkAssertions(userList);
	}
}