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
				ResultSet rs = pstmt.executeQuery();){
			

			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				Long userId = rs.getLong(9);

	            
	            try (PreparedStatement pstmt2 = conn.prepareStatement(
	                        "SELECT name from user where id = ?")) {
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
				vo.setgNo(gNo);
				vo.setoNo(oNo);
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

			String url = "jdbc:mariadb://192.168.0.13:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

	public int insert(BoardVo vo) {
		int count = 0;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("insert into board values (null, ?, ?, ?, now(), ?, ?, ?, ?)");) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, 0);
			pstmt.setInt(4, vo.getgNo());
			pstmt.setInt(5, vo.getoNo());
			pstmt.setInt(6, vo.getDepth());
			pstmt.setLong(7, vo.getUserId());

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return count;
	}

	public int deleteByIdAndPassword(Long id, String password) {
		int count = 0;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from guestbook where id=? and password=?");) {
			pstmt.setLong(1, id);
			pstmt.setString(2, password);

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return count;
	}

	public BoardVo findById(Long boardId) {
		BoardVo boardVo = null;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select user_id, title, contents from board where id = ?");) {

			pstmt.setLong(1, boardId);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Long userId = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);

				boardVo = new BoardVo();
				
				boardVo.setUserId(userId);
				boardVo.setTitle(title);
				boardVo.setContents(contents);
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return boardVo;
	}

	public int updateUser(UserVo vo) {
		int result = 0;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("UPDATE user SET name = ?, password = ?, gender = ? WHERE id = ?");
				PreparedStatement pstmt2 = conn
						.prepareStatement("UPDATE user SET name = ?, gender = ? WHERE id = ?");) {

			ResultSet rs = null;

			if ("".equals(vo.getPassword())) {
				pstmt2.setString(1, vo.getName());
				pstmt2.setString(2, vo.getGender());
				pstmt2.setLong(3, vo.getId());
				rs = pstmt2.executeQuery();
			}

			else {
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getPassword());
				pstmt.setString(3, vo.getGender());
				pstmt.setLong(4, vo.getId());
				rs = pstmt.executeQuery();
			}

			rs.close();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return result;
	}

}
