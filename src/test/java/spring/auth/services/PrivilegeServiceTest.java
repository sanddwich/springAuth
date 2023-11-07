package spring.auth.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.auth.entities.Privilege;
import spring.auth.repositories.PrivilegeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrivilegeServiceTest {
	private String privilegeName = "TestPrivilegeName";
	private String privilegeCode = "TestPrivilegeCode";
	private String privilegeDescription = "TestPrivilegeDescription";

	@Mock
	private PrivilegeRepository privilegeRepository;

	@InjectMocks
	private PrivilegeService privilegeService;

	@AfterEach
	void tearDown() {
		reset();
	}

	@Test
	void canFindAllPrivileges() {
		//given
		//when
		privilegeService.findAll();
		//then
		verify(privilegeRepository).findAll();
	}

	@Test
	void savePrivilegeSuccess() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeRepository.findByCode(Mockito.anyString()))
		  .thenReturn(Collections.emptyList());
		when(privilegeRepository.findByName(Mockito.anyString()))
		  .thenReturn(Collections.emptyList());
		when(privilegeRepository.save(Mockito.any(Privilege.class)))
		  .thenReturn(privilege);

		privilegeService.save(privilege);

		//then
		verify(privilegeRepository).save(privilege);
		assertThat(privilegeRepository.save(privilege)).isInstanceOf(Privilege.class);
	}

	@Test
	void savePrivilegeWithNoUniqueNameError() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeRepository.findByName(Mockito.anyString()))
		  .thenReturn(Collections.singletonList(privilege));

		Privilege savedPrivilege = privilegeService.save(privilege);

		//then
		verify(privilegeRepository, times(0)).save(privilege);
		assertThat(privilegeRepository.save(privilege)).isNull();
	}

	@Test
	void savePrivilegeWithNoUniqueCodeError() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeRepository.findByCode(Mockito.anyString()))
		  .thenReturn(Collections.singletonList(privilege));

		Privilege savedPrivilege = privilegeService.save(privilege);

		//then
		verify(privilegeRepository, times(0)).save(privilege);
		assertThat(privilegeRepository.save(privilege)).isNull();
	}

	@Test
	void search() {
		//given
		String searchTerm = Mockito.anyString();

		//when
		privilegeService.search(searchTerm);

		//then
		verify(privilegeRepository).search(searchTerm);
	}

	@Test
	void deleteSuccess() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		privilegeService.delete(privilege);
		when(privilegeRepository.findById(privilege.getId()))
		  .thenReturn(Optional.of(privilege));


		//then
		assertEquals(privilege, privilegeService.delete(privilege));
		verify(privilegeRepository).delete(privilege);
	}

	@Test
	void findPrivilegeByNameOrCodeSuccess() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeRepository.findByName(privilege.getName()))
		  .thenReturn(Collections.singletonList(privilege));

		privilegeService.findPrivilegeByNameOrCode(privilege);

		//then
		assertTrue(privilegeService.findPrivilegeByNameOrCode(privilege));
	}

	@Test
	void findPrivilegeByNameOrCodeError() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeRepository.findByName(privilege.getName()))
		  .thenReturn(Collections.emptyList());

		privilegeService.findPrivilegeByNameOrCode(privilege);

		//then
		assertFalse(privilegeService.findPrivilegeByNameOrCode(privilege));
	}

	@Test
	void saveAllSuccess() {
		//given
		List<Privilege> privilegeList = createTestPrivilegeList();

		//when
		when(privilegeRepository.findByName(Mockito.anyString()))
		  .thenReturn(Collections.emptyList());

		privilegeService.saveAll(privilegeList);

		//then
		verify(privilegeRepository).save(privilegeList.get(0));
		verify(privilegeRepository).save(privilegeList.get(1));
		verify(privilegeRepository).save(privilegeList.get(2));
		assertThat(privilegeService.saveAll(privilegeList)).isInstanceOf(List.class);
	}

	@Test
	void update() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		privilegeService.update(privilege);

		//then
		verify(privilegeRepository).save(privilege);
	}

	@Test
	void isPrivilegeByNameNotExistSuccess() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeRepository.findByName(privilege.getName()))
		  .thenReturn(Collections.singletonList(privilege));
		privilegeService.isPrivilegeByNameNotExist(privilege);

		//then
		verify(privilegeRepository).findByName(privilege.getName());
		assertFalse(privilegeService.isPrivilegeByNameNotExist(privilege));
	}

	@Test
	void isPrivilegeByNameNotExistError() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeRepository.findByName(privilege.getName()))
		  .thenReturn(Collections.emptyList());
		privilegeService.isPrivilegeByNameNotExist(privilege);

		//then
		verify(privilegeRepository).findByName(privilege.getName());
		assertTrue(privilegeService.isPrivilegeByNameNotExist(privilege));
	}

	@Test
	void findByNameSuccess() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeService.findByName(Mockito.anyString()))
		  .thenReturn(Collections.singletonList(privilege));

		privilegeService.findByName(privilege.getName());

		//then
		verify(privilegeRepository).findByName(privilege.getName());
		assertTrue(!privilegeService.findByName(privilege.getName()).isEmpty());
	}

	@Test
	void findByNameError() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeService.findByName(Mockito.anyString()))
		  .thenReturn(Collections.emptyList());

		privilegeService.findByName(privilege.getName());

		//then
		verify(privilegeRepository).findByName(privilege.getName());
		assertTrue(privilegeService.findByName(privilege.getName()).isEmpty());
	}

	@Test
	void findByCodeError() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeService.findByCode(Mockito.anyString()))
		  .thenReturn(Collections.emptyList());

		privilegeService.findByCode(privilege.getCode());

		//then
		verify(privilegeRepository).findByCode(privilege.getCode());
		assertTrue(privilegeService.findByCode(privilege.getCode()).isEmpty());
	}

	@Test
	void findByCodeSuccess() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeService.findByCode(Mockito.anyString()))
		  .thenReturn(Collections.singletonList(privilege));

		privilegeService.findByCode(privilege.getCode());

		//then
		verify(privilegeRepository).findByCode(privilege.getCode());
		assertTrue(!privilegeService.findByCode(privilege.getCode()).isEmpty());
	}

	@Test
	void findByDescriptionSuccess() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeService.findByDescription(Mockito.anyString()))
		  .thenReturn(Collections.singletonList(privilege));

		privilegeService.findByDescription(privilege.getDescription());

		//then
		verify(privilegeRepository).findByDescription(privilege.getDescription());
		assertTrue(!privilegeService.findByDescription(privilege.getDescription()).isEmpty());
	}

	@Test
	void findByDescriptionError() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeService.findByDescription(Mockito.anyString()))
		  .thenReturn(Collections.emptyList());

		privilegeService.findByDescription(privilege.getDescription());

		//then
		verify(privilegeRepository).findByDescription(privilege.getDescription());
		assertTrue(privilegeService.findByDescription(privilege.getDescription()).isEmpty());
	}

	@Test
	void findById() {
		//given
		Privilege privilege = createTestPrivilege(1);

		//when
		when(privilegeService.findById(privilege.getId()))
		  .thenReturn(Optional.ofNullable(privilege));

		privilegeService.findById(privilege.getId());

		//then
		verify(privilegeRepository).findById(privilege.getId());
		assertTrue(!privilegeService.findById(privilege.getId()).isEmpty());
	}

	private Privilege createTestPrivilege(Integer num) {
		return Privilege.builder()
		  .name(privilegeName.concat(num.toString()))
		  .code(privilegeCode.concat(num.toString()))
		  .description(privilegeDescription.concat(num.toString()))
		  .build();
	}

	private List<Privilege> createTestPrivilegeList() {
		return List.of(
		  createTestPrivilege(1),
		  createTestPrivilege(2),
		  createTestPrivilege(3)
		);
	}
}