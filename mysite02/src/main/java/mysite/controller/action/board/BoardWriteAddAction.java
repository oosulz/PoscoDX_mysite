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
		
		String GNo = request.getParameter("GNo");
		String ONo = request.getParameter("ONo");
		String depth = request.getParameter("depth");
		
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		Long userId = Long.parseLong(request.getParameter("userid"));

		BoardVo vo = new BoardVo();
		
        if (GNo != null && ONo != null && depth != null) {
            vo.setGNo(Integer.parseInt(GNo));
            vo.setONo(Integer.parseInt(ONo) + 1);
            vo.setDepth(Integer.parseInt(depth) + 1);
        }
        
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserId(userId);
		
		new BoardDao().insert(vo);
		
		response.sendRedirect("/mysite02/board");

	}

}
