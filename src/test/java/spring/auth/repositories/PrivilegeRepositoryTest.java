package spring.auth.repositories;

import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.auth.entities.Privilege;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
		assertThat(privilegeList.isEmpty()).isFalse();
		assertThat(privilegeList.stream().findFirst().orElse(null))
		  .isInstanceOf(Privilege.class);
		assertThat(privilegeList.stream().findFirst().orElse(null)
		  .getCode()).isEqualTo(privilegeCode);
		assertThat(privilegeList.stream().findFirst().orElse(null)
		  .getName()).isEqualTo(privilegeName);
		assertThat(privilegeList.stream().findFirst().orElse(null)
		  .getDescription()).isEqualTo(privilegeDescription);
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