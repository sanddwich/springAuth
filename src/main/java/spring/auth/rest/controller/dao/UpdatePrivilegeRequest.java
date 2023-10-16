package spring.auth.rest.controller.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePrivilegeRequest {
	private String name;
	private String code;
	private String description;
}
