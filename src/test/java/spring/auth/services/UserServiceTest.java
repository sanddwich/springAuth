package spring.auth.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.auth.entities.AccessRole;
import spring.auth.entities.User;
import spring.auth.repositories.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private String userName = "TestUserName";
    private String userUsername = "TestUserUsername";
    private String userSurname = "TestUserSurname";
    private String userEmail = "TestUserEmail@mail.ru";
    private String userAccessToken = "TestUserAccessToken";
    private String userPassword = "TestUserPassword";
    private boolean userActive = true;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @AfterEach
    void tearDown() {
        reset();
    }

    private User createTestUser() {
        return User.builder()
          .name(userName)
          .username(userUsername)
          .password(userPassword)
          .surname(userSurname)
          .email(userEmail)
          .active(userActive)
          .accessToken(userAccessToken)
          .accessRoles(Collections.emptyList())
          .build();
    }

    @Test
    void findAll() {
        //when
        userService.findAll();

        //then
        verify(userRepository).findAll();
    }

    @Test
    void search() {
        //given
        String searchTerm = Mockito.anyString();

        //when
        userService.search(searchTerm);

        //then
        verify(userRepository).search(searchTerm);
    }

    @Test
    void save() {
        //given
        User user = createTestUser();

        //when
        when(userRepository.findByUsername(Mockito.anyString()))
          .thenReturn(Collections.emptyList());
        when(userRepository.findByEmail(Mockito.anyString()))
          .thenReturn(Collections.emptyList());
        when(userRepository.save(user))
          .thenReturn(Optional.ofNullable(user).orElse(null));

        userService.save(user);

        //then
        verify(userRepository).save(user);
    }

    @Test
    void delete() {
        //given
        User user = createTestUser();

        //when
        userService.delete(user);
        when(userRepository.findById(user.getId()))
          .thenReturn(Optional.of(user));

        //then
        assertEquals(user, userService.delete(user));
        verify(userRepository).delete(user);
    }

    @Test
    void update() {
        //given
        User user = Mockito.any(User.class);

        //when
        userService.update(user);

        //then
        verify(userRepository).save(user);
    }

    @Test
    void findByUsernameOREmailSuccess() {
        //given
        User user = createTestUser();

        //when
        userService.findByUsernameOREmail(user);
        when(userRepository.findByUsername(user.getUsername()))
          .thenReturn(Collections.singletonList(user));

        //then
        assertTrue(userService.findByUsernameOREmail(user));
    }

    @Test
    void findByUsernameOREmailSuccess2() {
        //given
        User user = createTestUser();

        //when
        userService.findByUsernameOREmail(user);
        when(userRepository.findByEmail(user.getEmail()))
          .thenReturn(Collections.singletonList(user));

        //then
        assertTrue(userService.findByUsernameOREmail(user));
    }

    @Test
    void findByUsernameOREmailError() {
        //given
        User user = createTestUser();

        //when
        userService.findByUsernameOREmail(user);
        when(userRepository.findByUsername(user.getUsername()))
          .thenReturn(Collections.emptyList());
        when(userRepository.findByEmail(user.getEmail()))
          .thenReturn(Collections.emptyList());

        //then
        assertFalse(userService.findByUsernameOREmail(user));
    }

    @Test
    void findByAccessToken() {
        //when
        userService.findByAccessToken(Mockito.anyString());

        //then
        verify(userRepository).findByAccessToken(Mockito.anyString());
    }

    @Test
    void userAlreadyExistSuccess() {
        //given
        User user = createTestUser();

        //when
        userService.userAlreadyExist(user);
        when(userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()))
          .thenReturn(Collections.singletonList(user));

        //then
        assertTrue(userService.userAlreadyExist(user));
    }

    @Test
    void userAlreadyExistError() {
        //given
        User user = createTestUser();

        //when
        userService.userAlreadyExist(user);
        when(userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()))
          .thenReturn(Collections.emptyList());

        //then
        assertFalse(userService.userAlreadyExist(user));
    }

    @Test
    void clearAccessTokenSuccess() {
        //given
        User user = createTestUser();

        //when
        userService.clearAccessToken(user);
        when(userRepository.save(user))
          .thenReturn(user);

        //then
        assertTrue(userService.clearAccessToken(user));
    }

    @Test
    void findByUsername() {
        //when
        userService.findByUsername(Mockito.anyString());

        //then
        verify(userRepository).findByUsername(Mockito.anyString());
    }

    @Test
    void findByEmail() {
        //when
        userService.findByEmail(Mockito.anyString());

        //then
        verify(userRepository).findByEmail(Mockito.anyString());
    }

    @Test
    void findById() {
        //when
        userService.findById(Mockito.anyInt());

        //then
        verify(userRepository).findById(Mockito.anyInt());
    }

    @Test
    void findByName() {
        //when
        userService.findByName(Mockito.anyString());

        //then
        verify(userRepository).findByName(Mockito.anyString());
    }

    @Test
    void findBySurname() {
        //when
        userService.findBySurname(Mockito.anyString());

        //then
        verify(userRepository).findBySurname(Mockito.anyString());
    }
}