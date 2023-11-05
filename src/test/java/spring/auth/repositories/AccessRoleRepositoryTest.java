package spring.auth.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.auth.entities.AccessRole;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class AccessRoleRepositoryTest {
	@Autowired
	private AccessRoleRepository accessRoleRepository;
	private String accessRoleName = "TestAccessRoleName";
	private String accessRoleCode = "TestAccessRoleCode";
	private String accessRoleDescription = "TestAccessRoleDescription";

	private void generateAccessRoleAtDB() {
		AccessRole accessRole = AccessRole.builder()
		  .name(accessRoleName)
		  .code(accessRoleCode)
		  .description(accessRoleDescription)
		  .privileges(Collections.emptyList())
		  .build();

		accessRoleRepository.save(accessRole);
	}

	private void generateAccessRoleAtDB2() {
		AccessRole accessRole = AccessRole.builder()
		  .name(accessRoleName)
		  .code(accessRoleCode)
		  .description(accessRoleDescription)
		  .privileges(Collections.emptyList())
		  .build();

		accessRoleRepository.save(accessRole);
	}



	@AfterEach
	void tearDown() {
		accessRoleRepository.deleteAll();
	}

	private void checkAssertions(List<AccessRole> accessRoleList) {
		assertTrue(!accessRoleList.isEmpty());
		assertTrue(accessRoleList.stream().findFirst()
		  .map(AccessRole::getName)
		  .map(el -> el.equals(accessRoleName))
		  .orElse(false)
		);
		assertTrue(accessRoleList.stream().findFirst()
		  .map(AccessRole::getCode)
		  .map(el -> el.equals(accessRoleCode))
		  .orElse(false)
		);
		assertTrue(accessRoleList.stream().findFirst()
		  .map(AccessRole::getDescription)
		  .map(el -> el.equals(accessRoleDescription))
		  .orElse(false)
		);
	}

	@Test
	void search() {
		//given
		generateAccessRoleAtDB();

		//when
		List<AccessRole> accessRoleList = accessRoleRepository.search("AccessRole");

		//then
		checkAssertions(accessRoleList);
	}

	@Test
	void findByName() {
		//given
		generateAccessRoleAtDB();

		//when
		List<AccessRole> accessRoleList =
		  accessRoleRepository.findByName(accessRoleName);

		//then
		checkAssertions(accessRoleList);
	}

	@Test
	void findByCode() {
		//given
		generateAccessRoleAtDB();

		//when
		List<AccessRole> accessRoleList =
		  accessRoleRepository.findByCode(accessRoleCode);

		//then
		checkAssertions(accessRoleList);
	}

	@Test
	void findByDescription() {
		//given
		generateAccessRoleAtDB();

		//when
		List<AccessRole> accessRoleList =
		  accessRoleRepository.findByDescription(accessRoleDescription);

		//then
		checkAssertions(accessRoleList);
	}
}