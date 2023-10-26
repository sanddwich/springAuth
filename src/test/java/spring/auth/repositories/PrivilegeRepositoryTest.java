package spring.auth.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.auth.entities.Privilege;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PrivilegeRepositoryTest {
	@Autowired
	private PrivilegeRepository privilegeRepository;
	private String privilegeName = "TestPrivilegeName";
	private String privilegeCode = "TestPrivilegeCode";
	private String privilegeDescription = "TestPrivilegeDescription";

	private void generatePrivilege() {
		Privilege privilege = Privilege.builder()
		  .name(privilegeName)
		  .code(privilegeCode)
		  .description(privilegeDescription)
		  .build();

		privilegeRepository.save(privilege);
	}

	private void isAssert(List<Privilege> privilegeList) {
		assertThat(privilegeList.isEmpty()).isFalse();
		assertThat(privilegeList.stream().findFirst().get()).isInstanceOf(Privilege.class);
		assertThat(privilegeList.stream().findFirst().get().getCode()).isEqualTo(privilegeCode);
		assertThat(privilegeList.stream().findFirst().get().getName()).isEqualTo(privilegeName);
		assertThat(privilegeList.stream().findFirst().get().getDescription()).isEqualTo(privilegeDescription);
	}

	@AfterEach
	void tearDown() {
		this.privilegeRepository.deleteAll();
	}

	@Test
	void search() {
		//given
		generatePrivilege();

		//when
		List<Privilege> privilegeList = privilegeRepository.search("Privilege");

		//then
		isAssert(privilegeList);
	}

	@Test
	void findByName() {
		//given
		generatePrivilege();

		//when
		List<Privilege> privilegeList = privilegeRepository.findByName(privilegeName);

		//then
		isAssert(privilegeList);
	}

	@Test
	void findByCode() {
		//given
		generatePrivilege();

		//when
		List<Privilege> privilegeList = privilegeRepository.findByCode(privilegeCode);

		//then
		isAssert(privilegeList);
	}

	@Test
	void findByDescription() {
		//given
		generatePrivilege();

		//when
		List<Privilege> privilegeList = privilegeRepository.findByDescription(privilegeDescription);

		//then
		isAssert(privilegeList);
	}
}