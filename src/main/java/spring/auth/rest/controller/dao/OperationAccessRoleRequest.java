package spring.auth.rest.controller.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationAccessRoleRequest {
	private Date createdDate;
	private Date modifiedDate;
	private String name;
	private String code;
	private String description;
	private List<UpdatePrivilegeRequest> privileges;
}
