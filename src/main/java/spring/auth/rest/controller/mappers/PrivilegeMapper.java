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
public class PrivilegeMapper {
    private final PrivilegeService privilegeService;

    public Privilege mapPrivilege(UpdatePrivilegeRequest updatePrivilegeRequest) throws Exception {
        List<Privilege> privilegeList = this.privilegeService.findById(
          updatePrivilegeRequest.getId()
        ).stream().toList();

        if (privilegeList.isEmpty()) throw new Exception(
          "Privilege with ID: " + updatePrivilegeRequest.getId() + " is not found!"
        );
        Privilege privilege = privilegeList.stream().findFirst().get();

        this.mapPrivilegeName(updatePrivilegeRequest, privilege);
        this.mapPrivilegeCode(updatePrivilegeRequest, privilege);
        this.mapPrivilegeDescription(updatePrivilegeRequest, privilege);
        this.privilegeService.update(privilege);

        return privilege;
    }

    private void mapPrivilegeName(
      UpdatePrivilegeRequest updatePrivilegeRequest, Privilege privilege
    ) throws Exception {
        if (!updatePrivilegeRequest.getName().equals(privilege.getName())) {
            List<Privilege> privilegeList = this.privilegeService.findByName(
              updatePrivilegeRequest.getName()
            ).stream().toList();
            if (!privilegeList.isEmpty()) throw new Exception(
                    "Other Privilege with name '" + updatePrivilegeRequest.getName() + "' already exist!"
            );

            privilege.setName(updatePrivilegeRequest.getName());
        }
    }

    private void mapPrivilegeCode(
      UpdatePrivilegeRequest updatePrivilegeRequest, Privilege privilege
    ) throws Exception {
        if (!updatePrivilegeRequest.getCode().equals(privilege.getCode())) {
            List<Privilege> privilegeList = this.privilegeService.findByCode(updatePrivilegeRequest.getCode());
            if (!privilegeList.isEmpty()) throw new Exception(
                    "Other Privilege with code '" + updatePrivilegeRequest.getCode() + "' already exist!"
            );

            privilege.setCode(updatePrivilegeRequest.getCode());
        }
    }

    private void mapPrivilegeDescription(
      UpdatePrivilegeRequest updatePrivilegeRequest, Privilege privilege
    ) throws Exception {
        if (!updatePrivilegeRequest.getDescription().equals(privilege.getDescription())) {
            privilege.setDescription(updatePrivilegeRequest.getDescription());
        }
    }
}
