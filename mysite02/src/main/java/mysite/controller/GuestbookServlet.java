package mysite.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import mysite.controller.ActionServlet.Action;
import mysite.controller.action.main.MainAction;
import mysite.controller.action.request.AddAction;
import mysite.controller.action.request.DeleteAction;
import mysite.controller.action.request.DeleteFormAction;
import mysite.controller.action.request.ListAction;
import mysite.controller.action.user.JoinAction;
import mysite.controller.action.user.JoinFormAction;
import mysite.controller.action.user.JoinSuccessAction;
import mysite.dao.GuestbookDao;
import mysite.vo.GuestbookVo;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/guestbook")
public class GuestbookServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
			"add", new AddAction(), 
			"deleteform", new DeleteFormAction(),
			"delete", new DeleteAction());

	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}

}
