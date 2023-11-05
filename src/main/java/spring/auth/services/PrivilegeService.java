package spring.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import spring.auth.entities.Privilege;
import spring.auth.repositories.PrivilegeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrivilegeService implements BaseDataService<Privilege> {
	private final PrivilegeRepository privilegeRepository;

	@Autowired
	public PrivilegeService(PrivilegeRepository privilegeRepository) {
		this.privilegeRepository = privilegeRepository;
	}

	@Override
	public List<Privilege> findAll() {
		return this.privilegeRepository.findAll();
	}

	@Override
	public List<Privilege> search(String searchTerm) {
		return this.privilegeRepository.search(searchTerm);
	}

	@Override
	public Privilege save(Privilege privilege) {
		if (!this.findPrivilegeByNameOrCode(privilege)) {
			return this.privilegeRepository.save(privilege);
		}

		return null;
	}

	@Override
	public Privilege delete(Privilege privilege) throws DataIntegrityViolationException {
		Optional<Privilege> privilegeOptional = this.privilegeRepository.findById(privilege.getId());
		privilegeOptional
		  .ifPresent(privilegeRepository::delete);

		return privilegeOptional.orElse(null);
	}

	public boolean findPrivilegeByNameOrCode(Privilege privilege) {
		return !this.privilegeRepository.findByName(privilege.getName()).isEmpty() ||
		  !this.privilegeRepository.findByCode(privilege.getCode()).isEmpty();
	}

	public List<Privilege> saveAll(List<Privilege> privileges) {
		return privileges.stream()
		  .filter(this::isPrivilegeByNameNotExist)
		  .peek(privilegeRepository::save)
		  .collect(Collectors.toList());
	}

	public Privilege update(Privilege privilege) {
		return privilegeRepository.save(privilege);
	}

	public boolean isPrivilegeByNameNotExist(Privilege privilege) {
		return privilegeRepository.findByName(privilege.getName()).isEmpty();
	}

	public List<Privilege> findByName(String name) {
		return this.privilegeRepository.findByName(name);
	}

	public List<Privilege> findByCode(String code) {
		return this.privilegeRepository.findByCode(code);
	}

	public List<Privilege> findByDescription(String description) {
		return this.privilegeRepository.findByDescription(description);
	}

	public Optional<Privilege> findById(Integer id) {
		return this.privilegeRepository.findById(id);
	}
}
