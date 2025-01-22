package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;
import mysite.vo.UserVo;

public class BoardDao {

	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		String userName = null;
		try (Connection conn = getConnection();

				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT id, title, contents, hit, reg_date, g_no, o_no, depth, user_id from board order by g_no desc , o_no asc");
				ResultSet rs = pstmt.executeQuery();) {

			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int GNo = rs.getInt(6);
				int ONo = rs.getInt(7);
				int depth = rs.getInt(8);
				Long userId = rs.getLong(9);

				try (PreparedStatement pstmt2 = conn.prepareStatement("SELECT name from user where id = ?")) {
					pstmt2.setLong(1, userId);
					try (ResultSet rs2 = pstmt2.executeQuery()) {
						if (rs2.next()) {
							userName = rs2.getString(1);
						}
					}
				}

				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setGNo(GNo);
				vo.setONo(ONo);
				vo.setDepth(depth);
				vo.setUserId(userId);
				vo.setUserName(userName);
				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.15:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

	private int getMaxGroupNo() {

		int maxGNo = 0;
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select IFNULL(Max(g_no),0) from board;");
				ResultSet rs = pstmt.executeQuery();) {

			if (rs.next()) {
				maxGNo = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return maxGNo;
	}

	public void plusHit(Long id) {

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("update board SET hit = hit + 1 WHERE id = ?")) {
			pstmt.setLong(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public int insert(BoardVo vo) {

		int count = 0;
		try (Connection conn = getConnection();) {
			if (vo.getGNo() == 0) {
				vo.setGNo(getMaxGroupNo() + 1);
				vo.setONo(1);
				vo.setDepth(0);
			}

			// 답글
			else {
				String updateNoSql = "UPDATE board SET o_no = o_no + 1 " + "WHERE g_no = ? AND o_no >= ?";
				try (PreparedStatement updateStmt = conn.prepareStatement(updateNoSql)) {
					updateStmt.setInt(1, vo.getGNo());
					updateStmt.setInt(2, vo.getONo());
					updateStmt.executeUpdate();
				}
			}

			PreparedStatement pstmt = conn
					.prepareStatement("insert into board values (null, ?, ?, ?, now(), ?, ?, ?, ?)");

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, 0);
			pstmt.setInt(4, vo.getGNo());
			pstmt.setInt(5, vo.getONo());
			pstmt.setInt(6, vo.getDepth());
			pstmt.setLong(7, vo.getUserId());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return count;
	}

	public int deleteById(Long id) {
		int count = 0;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from board where id=?");) {
			pstmt.setLong(1, id);

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return count;
	}

	public BoardVo findById(Long boardId) {
		BoardVo boardVo = null;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select id, user_id, title, contents, g_no, o_no, depth from board where id = ?");) {

			pstmt.setLong(1, boardId);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Long id = rs.getLong(1);
				Long userId = rs.getLong(2);
				String title = rs.getString(3);
				String contents = rs.getString(4);
				int GNo = rs.getInt(5);
				int ONo = rs.getInt(6);
				int depth = rs.getInt(7);

				boardVo = new BoardVo();

				boardVo.setId(id);
				boardVo.setUserId(userId);
				boardVo.setTitle(title);
				boardVo.setContents(contents);
				boardVo.setGNo(GNo);
				boardVo.setONo(ONo);
				boardVo.setDepth(depth);
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return boardVo;
	}

	public int update(BoardVo vo) {
		int result = 0;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("UPDATE board SET title = ?, contents = ? WHERE id = ?");) {

			ResultSet rs = null;

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getId());
			rs = pstmt.executeQuery();

			rs.close();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return result;
	}

	public int getTotalCount() {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM board";

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;
	}

	public List<BoardVo> getList(int currentPage, int pageSize) {
		List<BoardVo> result = new ArrayList<>();
		String userName = null;

		String sql = "SELECT id, title, contents, hit, reg_date, g_no, o_no, depth, user_id FROM board ORDER BY g_no DESC, o_no ASC LIMIT ?, ?";

		try (Connection conn = getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, (currentPage - 1) * pageSize);
			pstmt.setInt(2, pageSize);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					
					Long id = rs.getLong(1);
					String title = rs.getString(2);
					String contents = rs.getString(3);
					int hit = rs.getInt(4);
					String regDate = rs.getString(5);
					int GNo = rs.getInt(6);
					int ONo = rs.getInt(7);
					int depth = rs.getInt(8);
					Long userId = rs.getLong(9);
					
					try (PreparedStatement pstmt2 = conn.prepareStatement("SELECT name from user where id = ?")) {
						pstmt2.setLong(1, userId);
						try (ResultSet rs2 = pstmt2.executeQuery()) {
							if (rs2.next()) {
								userName = rs2.getString(1);
							}
						}
					}
					
					BoardVo vo = new BoardVo();
					vo.setId(id);
					vo.setTitle(title);
					vo.setContents(contents);
					vo.setHit(hit);
					vo.setRegDate(regDate);
					vo.setGNo(GNo);
					vo.setONo(ONo);
					vo.setDepth(depth);
					vo.setUserId(userId);
					vo.setUserName(userName);
					result.add(vo);
				}
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return result;
	}

}
