package mysite.controller;


import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.board.BoardDetailAction;
import mysite.controller.action.board.BoardListAction;
import mysite.controller.action.board.BoardWriteListAction;
import mysite.controller.action.main.MainAction;
import java.util.Map;


@WebServlet("/board")
public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
		"boarddetail", new BoardDetailAction(),
		"boardwrite", new BoardWriteListAction()
	);
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new BoardListAction());
	}

}
