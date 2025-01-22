package mysite.vo;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getGNo() {
		return GNo;
	}

	public void setGNo(int GNo) {
		this.GNo = GNo;
	}

	public int getONo() {
		return ONo;
	}

	public void setONo(int ONo) {
		this.ONo = ONo;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "BoardVo [id=" + id + ", title=" + title + ", contents=" + contents + ", hit=" + hit + ", regDate="
				+ regDate + ", GNo=" + GNo + ", ONo=" + ONo + ", depth=" + depth + ", userId=" + userId + ", userName="
				+ userName + "]";
	}

}