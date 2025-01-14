package mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class GuestbookLogRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert() {
		return sqlSession.update("guestbook-log.insert");
	}
	
	public int update() {
		return sqlSession.update("guestbook-log.update");
	}
	
	public int update(String regDate) {
		return sqlSession.update("guestbook-log.updateByRegDate",regDate);
	}

}
