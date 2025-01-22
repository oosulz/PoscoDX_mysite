package mysite.vo;

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
public class BoardVo {
	private Long id;
	private String title;
	private String contents;
	private int hit;
	private String regDate;
	private int GNo;
	private int ONo;
	private int depth;
	private long userId;
	private String userName;
}