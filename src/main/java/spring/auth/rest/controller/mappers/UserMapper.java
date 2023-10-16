package spring.auth.rest.controller.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import spring.auth.entities.AccessRole;
import spring.auth.entities.User;
import spring.auth.rest.controller.dao.UpdateAccessRoleRequest;
import spring.auth.rest.controller.dao.UpdateUserRequest;
import spring.auth.services.AccessRoleService;
import spring.auth.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final UserService userService;
    private final AccessRoleService accessRoleService;
    private final PasswordEncoder passwordEncoder;

    public User mapUser(UpdateUserRequest updateUserRequest) throws Exception {
        User user = this.userService.findById(updateUserRequest.getId()).get();
        if (user == null) throw new Exception(
          "User with ID: " + updateUserRequest.getId() + " is not found!"
        );

        this.mapUsername(updateUserRequest, user);
        this.mapEmail(updateUserRequest, user);
        this.mapName(updateUserRequest, user);
        this.mapSurname(updateUserRequest, user);
        this.mapActive(updateUserRequest, user);
        this.mapAccessRoles(updateUserRequest, user);
        this.mapPassword(updateUserRequest, user);
        this.userService.update(user);

        return user;
    }

    private void mapUsername(UpdateUserRequest updateUserRequest, User user) throws Exception {
        if (!updateUserRequest.getUsername().equals(user.getUsername())) {
            User tmpUser = this.userService.findByUsername(updateUserRequest.getUsername()).stream().findFirst().get();
            if (tmpUser != null) throw new Exception(
                    "Other user with username '" + updateUserRequest.getUsername() + "' already exist!"
            );

            user.setName(updateUserRequest.getUsername());
        }
    }

    private void mapEmail(UpdateUserRequest updateUserRequest, User user) throws Exception {
        if (!updateUserRequest.getEmail().equals(user.getEmail())) {
            User tmpUser = this.userService.findByEmail(updateUserRequest.getEmail()).stream().findFirst().get();
            if (tmpUser != null) throw new Exception(
                    "Other user with email '" + updateUserRequest.getEmail() + "' already exist!"
            );

            user.setEmail(updateUserRequest.getEmail());
        }
    }

    private void mapName(UpdateUserRequest updateUserRequest, User user) throws Exception {
        if (!updateUserRequest.getName().equals(user.getName())) {
            user.setName(updateUserRequest.getName());
        }
    }

    private void mapSurname(UpdateUserRequest updateUserRequest, User user) throws Exception {
        if (!updateUserRequest.getSurname().equals(user.getSurname())) {
            user.setSurname(updateUserRequest.getSurname());
        }
    }

    private void mapActive(UpdateUserRequest updateUserRequest, User user) throws Exception {
        if (updateUserRequest.isActive() != user.isActive()) {
            user.setActive(updateUserRequest.isActive());
        }
    }

    private void mapPassword(UpdateUserRequest updateUserRequest, User user) throws Exception {
        if (
                updateUserRequest.getPassword() != null &&
                !this.passwordEncoder.encode(updateUserRequest.getPassword()).equals(user.getPassword())
        ) user.setPassword(this.passwordEncoder.encode(updateUserRequest.getPassword()));
    }

    private void mapAccessRoles(UpdateUserRequest updateUserRequest, User user) throws Exception {
        List<AccessRole> accessRoleList = new ArrayList<>();
        for(UpdateAccessRoleRequest updateAccessRoleRequest: updateUserRequest.getAccessRoles()) {
            AccessRole accessRole = this.accessRoleService.findById(updateAccessRoleRequest.getId()).get();
            if (accessRole == null) throw new Exception(
                    "AccessRole: " + updateAccessRoleRequest.getCode() + " does not exist!"
            );
            accessRoleList.add(accessRole);
        }

        user.setAccessRoles(accessRoleList);
    }
}
