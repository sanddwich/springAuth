package spring.auth.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import spring.auth.entities.Privilege;
import spring.auth.repositories.PrivilegeRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PrivilegeServiceTest {
	@Mock
	private PrivilegeRepository privilegeRepository;
	private PrivilegeService privilegeService;
	private String privilegeName = "TestPrivilegeName";
	private String privilegeCode = "TestPrivilegeCode";
	private String privilegeDescription = "TestPrivilegeDescription";

	@BeforeEach
	void setUp() {
		privilegeService = new PrivilegeService(privilegeRepository);
	}

	@Test
	void canFindAllPrivileges() {
		//when
		privilegeService.findAll();
		//then
		verify(privilegeRepository).findAll();
	}

	@Test
	void canSavePrivilege() {
		//given
		Privilege privilege = createTestPrivilege();

		//when
		privilegeService.save(privilege);

		//then
		ArgumentCaptor<Privilege> privilegeArgumentCaptor =
		  ArgumentCaptor.forClass(Privilege.class);

		verify(privilegeRepository)
		  .save(privilegeArgumentCaptor.capture());

		Privilege capturedPrivilege = privilegeArgumentCaptor.getValue();
		assertThat(capturedPrivilege).isEqualTo(privilege);
	}

	@Test
	@Disabled
	void search() {
	}

	@Test
	@Disabled
	void delete() {
	}

	@Test
	@Disabled
	void findPrivilegeByNameOrCode() {
	}

	@Test
	@Disabled
	void saveAll() {
	}

	@Test
	@Disabled
	void update() {
	}

	@Test
	@Disabled
	void isPrivilegeByNameNotExist() {
	}

	@Test
	@Disabled
	void findByName() {
	}

	@Test
	@Disabled
	void findByCode() {
	}

	@Test
	@Disabled
	void findByDescription() {
	}

	@Test
	@Disabled
	void findById() {
	}

	private Privilege createTestPrivilege() {
		return Privilege.builder()
		  .name(privilegeName)
		  .code(privilegeCode)
		  .description(privilegeDescription)
		  .build();
	}
}