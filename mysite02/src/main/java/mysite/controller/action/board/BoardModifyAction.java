package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class BoardModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("a");
		
		if ("boardmodify".equals(action)) {
			Long id = Long.parseLong(request.getParameter("id"));
			String title = request.getParameter("title");
			String contents = request.getParameter("content");
			Long userId = Long.parseLong(request.getParameter("userid"));

			BoardVo vo = new BoardVo();
			vo.setId(id);
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserId(userId);

			new BoardDao().update(vo);

			response.sendRedirect("/mysite02/board?a=boarddetail&id=" + id);
		}
	}
}