package mysite.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
	private Long id;
	
	@NotEmpty
	@Size(min=2, max=8)
	private String name;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	@Size(min=4, max=16)
	private String password;
	
	
	private String gender;
	private String joinDate;
	private String role;
	

}
