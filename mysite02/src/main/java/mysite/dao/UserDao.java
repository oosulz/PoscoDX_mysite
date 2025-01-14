package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.UserVo;

public class UserDao {

	public List<UserVo> findAll() {
		List<UserVo> result = new ArrayList<>();

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select id, name, email, password, gender, date_format(join_date, '%Y-%m-%d %h:%i:%s') from user order by join_date desc");
				ResultSet rs = pstmt.executeQuery();) {
			while (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String password = rs.getString(4);
				String gender = rs.getString(5);
				String joinDate = rs.getString(6);

				UserVo vo = new UserVo();
				vo.setId(id);
				vo.setName(name);
				vo.setPassword(password);
				vo.setEmail(email);
				vo.setGender(gender);
				vo.setJoinDate(joinDate);

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

			String url = "jdbc:mariadb://192.168.0.21:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

	public int insert(UserVo vo) {
		int count = 0;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("insert into user values (null, ?, ?, ?, ?, now())");) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

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

	public UserVo findByEmailAndPassword(String email, String password) {

		UserVo userVo = null;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select id, name, email from user where email= ? and password = ?");) {

			pstmt.setString(1, email);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);

				userVo = new UserVo();

				userVo.setId(id);
				userVo.setName(name);
				userVo.setEmail(email);
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return userVo;

	}

	public UserVo findById(Long userId) {
		UserVo userVo = null;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select id, name, email, gender from user where id = ?");) {

			pstmt.setLong(1, userId);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);

				userVo = new UserVo();

				userVo.setId(id);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setGender(gender);
			}
			rs.close();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return userVo;
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
