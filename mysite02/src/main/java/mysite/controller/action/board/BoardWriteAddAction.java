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

public class BoardWriteAddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String gNo = request.getParameter("gno");
		String oNo = request.getParameter("ono");
		String depth = request.getParameter("depth");
		
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		Long userId = Long.parseLong(request.getParameter("userid"));

		BoardVo vo = new BoardVo();
		
        if (gNo != null && oNo != null && depth != null) {
            vo.setgNo(Integer.parseInt(gNo));
            vo.setoNo(Integer.parseInt(oNo) + 1);
            vo.setDepth(Integer.parseInt(depth) + 1);
        }
        
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserId(userId);
		
		new BoardDao().insert(vo);
		
		response.sendRedirect("/mysite02/board");

	}

}
