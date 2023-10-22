package spring.auth.rest.controller.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.auth.entities.AccessRole;
import spring.auth.entities.Privilege;
import spring.auth.rest.controller.dao.UpdateAccessRoleRequest;
import spring.auth.rest.controller.dao.UpdatePrivilegeRequest;
import spring.auth.services.AccessRoleService;
import spring.auth.services.PrivilegeService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AccessRoleMapper {
    private final PrivilegeService privilegeService;
    private final AccessRoleService accessRoleService;

    public AccessRole mapAccessRole(UpdateAccessRoleRequest updateAccessRoleRequest) throws Exception {
        List<AccessRole> accessRoleList = this.accessRoleService.findById(
          updateAccessRoleRequest.getId()
        ).stream().toList();

        if (accessRoleList.isEmpty()) throw new Exception(
          "AccessRole with ID: " + updateAccessRoleRequest.getId() + " is not found!"
        );
        AccessRole accessRole = accessRoleList.stream().findFirst().get();

        this.mapAccessRoleName(updateAccessRoleRequest, accessRole);
        this.mapAccessRoleCode(updateAccessRoleRequest, accessRole);
        this.mapAccessRoleDescription(updateAccessRoleRequest, accessRole);
        this.mapAccessRolePrivileges(updateAccessRoleRequest, accessRole);
        this.accessRoleService.update(accessRole);

        return accessRole;
    }

    private void mapAccessRoleName(
      UpdateAccessRoleRequest updateAccessRoleRequest, AccessRole accessRole
    ) throws Exception {
        if (!updateAccessRoleRequest.getName().equals(accessRole.getName())) {
            List<AccessRole> accessRoleList = this.accessRoleService.findByName(
              updateAccessRoleRequest.getName()
            ).stream().toList();
            if (!accessRoleList.isEmpty()) throw new Exception(
                    "Other AccessRole with name '" + updateAccessRoleRequest.getName() + "' already exist!"
            );

            accessRole.setName(updateAccessRoleRequest.getName());
        }
    }

    private void mapAccessRoleCode(
      UpdateAccessRoleRequest updateAccessRoleRequest, AccessRole accessRole
    ) throws Exception {
        if (!updateAccessRoleRequest.getCode().equals(accessRole.getCode())) {
            List<AccessRole> accessRoleList = this.accessRoleService.findByCode(updateAccessRoleRequest.getCode());
            if (!accessRoleList.isEmpty()) throw new Exception(
                    "Other access role with code '" + updateAccessRoleRequest.getCode() + "' already exist!"
            );

            accessRole.setCode(updateAccessRoleRequest.getCode());
        }
    }

    private void mapAccessRoleDescription(
      UpdateAccessRoleRequest updateAccessRoleRequest, AccessRole accessRole
    ) throws Exception {
        if (!updateAccessRoleRequest.getDescription().equals(accessRole.getDescription())) {
            accessRole.setDescription(updateAccessRoleRequest.getDescription());
        }
    }

    private void mapAccessRolePrivileges(
      UpdateAccessRoleRequest updateAccessRoleRequest, AccessRole accessRole
    ) throws Exception {
        List<Privilege> privilegeList = new ArrayList<>();
        for(UpdatePrivilegeRequest updatePrivilegeRequest: updateAccessRoleRequest.getPrivileges()) {
            List<Privilege> privilegeListTmp = this.privilegeService.findById(
              updatePrivilegeRequest.getId()
            ).stream().toList();
            if (privilegeList.isEmpty()) throw new Exception(
                    "Privilege: " + updatePrivilegeRequest.getCode() + " does not exist!"
            );
            privilegeList.add(privilegeListTmp.stream().findFirst().get());
        }

        accessRole.setPrivileges(privilegeList);
    }
}
