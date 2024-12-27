package mysite.controller.action.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class BoardPageAction implements Action {

	private static final int PAGE_SIZE = 5;
	private static final int PAGE_BLOCK = 5;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int currentPage = 1;

		String page = request.getParameter("boardpage");
		
		if (page != null && !page.isEmpty()) {
			currentPage = Integer.parseInt(page);
		}

		BoardDao dao = new BoardDao();

		int totalCount = dao.getTotalCount();

		int totalPage = (int) Math.ceil((double) totalCount / PAGE_SIZE);

		int startPage = ((currentPage - 1) / PAGE_BLOCK) * PAGE_BLOCK + 1;
		int endPage = startPage + PAGE_BLOCK - 1;
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		boolean hasPrev = currentPage > 1;
		boolean hasNext = currentPage < totalPage;
		
	    List<Integer> disabledPages = new ArrayList<>();
	    for (int i = endPage + 1; i <= startPage + PAGE_BLOCK - 1; i++) {
	        disabledPages.add(i);
	    }


		List<BoardVo> list = dao.getList(currentPage, PAGE_SIZE);

		request.setAttribute("boardlist", list);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("hasPrev", hasPrev);
		request.setAttribute("hasNext", hasNext);
		request.setAttribute("pageBlock", PAGE_BLOCK);
		request.setAttribute("pageSize", PAGE_SIZE);
		request.setAttribute("disabledPages", disabledPages);
		request.setAttribute("totalCount", totalCount);
		
		System.out.println(currentPage+" "+totalPage+" "+startPage+" "+endPage+" "+hasPrev+" "+hasNext);

		System.out.println("Disabled Pages: " + disabledPages);

		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}
}
