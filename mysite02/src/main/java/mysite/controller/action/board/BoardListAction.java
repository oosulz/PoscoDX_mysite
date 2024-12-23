package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.dao.GuestbookDao;
import mysite.vo.BoardVo;
import mysite.vo.GuestbookVo;

public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<BoardVo> boardList = new BoardDao().findAll();
		
		request.setAttribute("boardlist", boardList);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);

	}

}
