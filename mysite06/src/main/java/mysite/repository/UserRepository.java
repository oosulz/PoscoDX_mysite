package mysite.repository;

import java.util.Map;

import javax.sql.DataSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.session.SqlSession;

import mysite.vo.UserVo;

@Repository
public class UserRepository {

	private DataSource dataSource;

	private SqlSession sqlSession;

	public UserRepository(DataSource dataSource, SqlSession sqlSession) {
		this.dataSource = dataSource;
		this.sqlSession = sqlSession;
	}

	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo);
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		return sqlSession.selectOne("user.findByEmailAndPassword", Map.of("email", email, "password", password));
	}

	public UserVo findById(Long userId) {
		return sqlSession.selectOne("user.findById", userId);
	}

	public int updateUser(UserVo vo) {
		return sqlSession.update("user.update");
	}

	public <R> R findByEmail(String email, Class<R> resultType) {
		Map<String, Object> map = sqlSession.selectOne("user.findByEmail", email);
		return new ObjectMapper().convertValue(map, resultType);
	}

}
