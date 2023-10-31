package spring.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import spring.auth.entities.User;
import spring.auth.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements BaseDataService<User> {
	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	@Override
	public List<User> search(String searchTerm) {
		return this.userRepository.search(searchTerm);
	}

	@Override
	public User save(User user) {
		if (!this.findByUsernameOREmail(user)) {
			this.userRepository.save(user);
			return this.userRepository.findByUsername(user.getUsername())
			  .stream()
			  .findFirst()
			  .get();
		}

		return null;
	}

	@Override
	public User delete(User user) throws DataIntegrityViolationException {
		List<User> userList = this.userRepository.findById(user.getId()).stream().toList();
		if (!userList.isEmpty()) {
			this.userRepository.delete(user);
			return userList.stream().findFirst().get();
		}

		return null;
	}

	public User update(User user) {
		this.userRepository.save(user);
		return this.userRepository.findByUsername(user.getUsername()).stream().findFirst().get();
	}

	public boolean findByUsernameOREmail(User user) {
		if (
		  !this.findByUsername(user.getUsername()).isEmpty() || !this.findByEmail(user.getEmail()).isEmpty()
		) return true;

		return false;
	}

	public User findByAccessToken(String val) {
		List<User> userList = this.userRepository.findByAccessToken(val);
		if (!userList.isEmpty()) return userList.stream().findFirst().get();

		return null;
	}

	public boolean userAlreadyExist(User user) {
		if (
		  this.userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isEmpty()
			|| user.getId() == this.userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).stream().findFirst().get().getId()
		) {
			return false;
		}

		return true;
	}

	public boolean clearAccessToken(User user) {
		try {
			user.setAccessToken(null);
			this.update(user);
			return true;
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public List<User> findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}

	public List<User> findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	public Optional<User> findById(Integer id) {
		return this.userRepository.findById(id);
	}

	public List<User> findByName(String val) {
		return this.userRepository.findByName(val);
	}

	public List<User> findBySurname(String val) {
		return this.userRepository.findBySurname(val);
	}
}
