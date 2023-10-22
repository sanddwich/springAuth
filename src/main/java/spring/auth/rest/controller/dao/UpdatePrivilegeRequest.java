package spring.auth.rest.controller.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePrivilegeRequest extends OperationPrivilegeRequest {
	private Integer id;
}
