package mysite.controller;


import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.board.BoardDeleteAction;
import mysite.controller.action.board.BoardDetailAction;
import mysite.controller.action.board.BoardDetailModifyAction;
import mysite.controller.action.board.BoardWriteListAction;
import mysite.controller.action.board.BoardWriteReplyAction;
import mysite.controller.action.board.BoardModifyAction;
import mysite.controller.action.board.BoardPageAction;
import mysite.controller.action.board.BoardWriteAddAction;
import java.util.Map;


@WebServlet("/board")
public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
		"boarddetail", new BoardDetailAction(),
		"boardwrite", new BoardWriteListAction(),
		"boardwriteadd", new BoardWriteAddAction(),
		"boarddetailmodify", new BoardDetailModifyAction(),
		"boardmodify", new BoardModifyAction(),
		"boardwritereply", new BoardWriteReplyAction(),
		"boarddelete", new BoardDeleteAction()
	);
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new BoardPageAction());
	}

}
