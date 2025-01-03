package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;
import mysite.vo.UserVo;

@Repository
public class BoardRepository {

	private DataSource dataSource;

	private SqlSession sqlSession;

	public BoardRepository(DataSource dataSource, SqlSession sqlSession) {
		this.dataSource = dataSource;
		this.sqlSession = sqlSession;
	}

	public int plusHit(Long id) {
		return sqlSession.update("board.plusHit", id);
	}

	private int getMaxGroupNo() {
		return sqlSession.selectOne("board.getMaxGroupNo");
	}

	public int getTotalCount() {
		return sqlSession.selectOne("board.getTotalCount");
	}

	public BoardVo findById(Long boardId) {
		return sqlSession.selectOne("board.findById", boardId);
	}

	public int deleteById(Long id) {
		return sqlSession.delete("board.deleteById", id);
	}

	public int insert(BoardVo vo) {
		if (vo.getgNo() == 0) {
			vo.setgNo(getMaxGroupNo() + 1);
			vo.setoNo(1);
			vo.setDepth(0);

		} else {
			sqlSession.update("board.updateOrderNo", vo);
		}

		return sqlSession.insert("board.insertBoard", vo);
	}

	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);
	}
	
	public List<BoardVo> getList(int currentPage, int pageSize) {
	    Map<String, Integer> map = new HashMap<>();
	    map.put("currentpage", (currentPage - 1) * pageSize);
	    map.put("pagesize", pageSize);
	    
	    return sqlSession.selectList("board.findByCurrentPageAndPageSize", map);
	}
}
