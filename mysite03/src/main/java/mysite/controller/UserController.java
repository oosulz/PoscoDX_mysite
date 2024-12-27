package mysite.controller;

import java.util.Formatter.BigDecimalLayoutForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import mysite.dao.UserDao;
import mysite.service.UserService;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;

	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "user/joinform";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVo userVo) {
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = "/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/loginform";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpSession session, UserVo userVo, Model model) {
		UserVo authUser = userService.getUser(userVo.getEmail(), userVo.getPassword());
		if (authUser == null) {
			model.addAttribute("email", userVo.getEmail());
			model.addAttribute("password", userVo.getPassword());
			model.addAttribute("result", "fail");
			return "user/loginform";
		}
		session.setAttribute("authUser", authUser);
		return "redirect:/";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(HttpSession session, Model model) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}
		UserVo userVo = userService.getUser(authUser.getId());
		model.addAttribute("vo", userVo);

		return "user/updateform";
	}

	/*
	 * @RequestMapping(value="/update",method = RequestMethod.GET) public String
	 * update(@AuthUser UserVo authUser, Model model) { UserVo authUser = (UserVo)
	 * session.getAttribute("authUser"); if(authUser == null) { return "redirect:/";
	 * } UserVo userVo = userService.getUser(authUser.getId());
	 * model.addAttribute("vo",userVo);
	 * 
	 * return "user/updateform"; }
	 */

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpSession session, UserVo userVo) {
		// Access Control
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			return "redirect:/";
		}

		userVo.setId(authUser.getId());
		userService.update(userVo);

		authUser.setName(userVo.getName());
		return "redirect:/user/update";
	}

}
