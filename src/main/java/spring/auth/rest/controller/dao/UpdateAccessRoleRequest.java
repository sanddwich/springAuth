package spring.auth.rest.controller.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccessRoleRequest extends OperationAccessRoleRequest{
	private Integer id;
}
