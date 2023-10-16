package spring.auth.rest.controller.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccessRoleRequest {
	private String name;
	private String code;
	private String description;
	private List<UpdatePrivilegeRequest> privileges;
}
