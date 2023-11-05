package spring.auth.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.auth.entities.Privilege;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PrivilegeRepositoryTest {
	@Autowired
	private PrivilegeRepository privilegeRepository;
	private String privilegeName = "TestPrivilegeName";
	private String privilegeCode = "TestPrivilegeCode";
	private String privilegeDescription = "TestPrivilegeDescription";

	private void generatePrivilegeAndSaveToDB() {
		Privilege privilege = Privilege.builder()
		  .name(privilegeName)
		  .code(privilegeCode)
		  .description(privilegeDescription)
		  .build();

		privilegeRepository.save(privilege);
	}

	private void assertPrivilegeList(List<Privilege> privilegeList) {
		assertFalse(privilegeList.isEmpty());
		assertThat(privilegeList.stream().findFirst().orElse(null))
		  .isInstanceOf(Privilege.class);
		assertTrue(privilegeList.stream().findFirst()
		  .map(Privilege::getCode)
		  .map(privilege -> privilege.equals(privilegeCode))
		  .orElse(false)
		);
		assertTrue(privilegeList.stream().findFirst()
		  .map(Privilege::getName)
		  .map(privilege -> privilege.equals(privilegeName))
		  .orElse(false)
		);
		assertTrue(privilegeList.stream().findFirst()
		  .map(Privilege::getDescription)
		  .map(desc -> desc.equals(privilegeDescription))
		  .orElse(false));

	}

	@AfterEach
	void tearDown() {
		this.privilegeRepository.deleteAll();
	}

	@Test
	void search() {
		//given
		generatePrivilegeAndSaveToDB();

		//when
		List<Privilege> privilegeList = privilegeRepository.search("Privilege");

		//then
		assertPrivilegeList(privilegeList);
	}

	@Test
	void findByName() {
		//given
		generatePrivilegeAndSaveToDB();

		//when
		List<Privilege> privilegeList = privilegeRepository.findByName(privilegeName);

		//then
		assertPrivilegeList(privilegeList);
	}

	@Test
	void findByCode() {
		//given
		generatePrivilegeAndSaveToDB();

		//when
		List<Privilege> privilegeList = privilegeRepository.findByCode(privilegeCode);

		//then
		assertPrivilegeList(privilegeList);
	}

	@Test
	void findByDescription() {
		//given
		generatePrivilegeAndSaveToDB();

		//when
		List<Privilege> privilegeList = privilegeRepository.findByDescription(privilegeDescription);

		//then
		assertPrivilegeList(privilegeList);
	}
}