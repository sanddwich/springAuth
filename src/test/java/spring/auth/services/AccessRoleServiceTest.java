package spring.auth.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.auth.entities.AccessRole;
import spring.auth.repositories.AccessRoleRepository;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccessRoleServiceTest {
    private String accessRoleName = "TestAccessRoleName";
    private String accessRoleCode = "TestAccessRoleCode";
    private String accessRoleDescription = "TestAccessRoleDescription";

    @Mock
    private AccessRoleRepository accessRoleRepository;

    @InjectMocks
    private AccessRoleService accessRoleService;

    @AfterEach
    void tearDown() {
        reset();
    }

    private AccessRole createAccessRole() {
        return AccessRole.builder()
                .name(accessRoleName)
                .code(accessRoleCode)
                .description(accessRoleDescription)
                .privileges(Collections.emptyList())
                .build();
    }

    @Test
    void findAll() {
        //when
        accessRoleService.findAll();

        //then
        verify(accessRoleRepository).findAll();
    }

    @Test
    void search() {
        //given
        String searchTerm = Mockito.anyString();

        //when
        accessRoleService.search(searchTerm);

        //then
        verify(accessRoleRepository).search(searchTerm);
    }

    @Test
    void saveAccessRole() {
        //given
        AccessRole accessRole = createAccessRole();

        //when
        when(accessRoleRepository.findByName(Mockito.anyString()))
                .thenReturn(Collections.emptyList());
        when(accessRoleRepository.findByCode(Mockito.anyString()))
                .thenReturn(Collections.emptyList());
        when(accessRoleRepository.save(accessRole))
                .thenReturn(Optional.ofNullable(accessRole).orElse(null));

        accessRoleService.save(accessRole);

        //then
        verify(accessRoleRepository).save(accessRole);
    }

    @Test
    void update() {
        //given
        AccessRole accessRole = Mockito.any(AccessRole.class);

        //when
        accessRoleService.update(accessRole);

        //then
        verify(accessRoleRepository).save(accessRole);
    }

    @Test
    void delete() {
        //given
        AccessRole accessRole = createAccessRole();

        //when
        accessRoleService.delete(accessRole);
        when(accessRoleRepository.findById(accessRole.getId()))
                .thenReturn(Optional.of(accessRole));

        //then
        assertEquals(accessRole, accessRoleService.delete(accessRole));
        verify(accessRoleRepository).delete(accessRole);
    }

    @Test
    @Disabled
    void findAccessRoleByNameOrCode() {
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
}