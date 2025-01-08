package mysite.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.repository.GuestbookRepository;
import mysite.service.GuestbookService;
import mysite.vo.GuestbookVo;
@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	
	
	private final GuestbookRepository guestbookRepository;
	
	@Autowired
	private GuestbookService guestbookService;

	public GuestbookController(GuestbookRepository guestbookRepository) {
		this.guestbookRepository = guestbookRepository;
	}

	@GetMapping
	public String guestbook(Model model) {
		model.addAttribute("list", guestbookService.getContentsList());
		return "guestbook/list";
	}

	@RequestMapping(value = "/delete/{id}", method=RequestMethod.GET)
	public String deleteForm(@PathVariable("id") Long id) {
		return "guestbook/deleteform";
	}

	@RequestMapping(value = "/delete/{id}", method=RequestMethod.POST)
	public String delete(@PathVariable("id") Long id, @RequestParam(value="password", required=true, defaultValue="") String password) {
		guestbookService.deleteContents(id, password);
		return "redirect:/guestbook";
	}

	@RequestMapping(value ="/add", method=RequestMethod.POST)
	public String add(GuestbookVo vo) {
		guestbookService.addContents(vo);
		return "redirect:/guestbook";
	}
}