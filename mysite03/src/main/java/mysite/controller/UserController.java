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
import mysite.security.Auth;
import mysite.security.AuthUser;
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
		try {
			System.out.println(userVo.toString());
			userService.join(userVo);
			System.out.println(userVo.toString());
		} catch (RuntimeException e) {
			System.out.println("error:" + e);
		}
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = "/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/loginform";
	}

	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(@AuthUser UserVo authUser,Model model) {
		//System.out.println("[auth]" + authUser.getId());
		UserVo userVo = userService.getUser(authUser.getId());
		model.addAttribute("vo", userVo);

		return "user/updateform";
	}
	
	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, UserVo userVo) {
		userVo.setId(authUser.getId());
		userService.update(userVo);

		authUser.setName(userVo.getName());
		return "redirect:/user/update";
	}

}
