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

public class BoardDetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("a");
		if ("boarddetail".equals(action)) {
			
			Long boardId = Long.parseLong(request.getParameter("id"));
			BoardDao dao = new BoardDao();
			BoardVo boardDetail = dao.findById(boardId);
			
			dao.plusHit(boardDetail.getId());
			request.setAttribute("boardContent", boardDetail);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/view.jsp");
			rd.forward(request, response);
		}

	}

}
