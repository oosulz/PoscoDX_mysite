package mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Controller
public class MainController {
	
	private final SiteService siteService;
	
	public MainController(SiteService siteService) {
		this.siteService = siteService;
	}
	@Autowired
	private LocaleResolver localeResolver;
	
	@RequestMapping({"/","/main"})
	public String index(HttpServletRequest request,Model model) {
			String lang =  localeResolver.resolveLocale(request).getLanguage();
			System.out.println("Language Code: " + lang);
			model.addAttribute("lang", lang);
			SiteVo siteVo = siteService.getSite();
			model.addAttribute("siteVo", siteVo);
		return "main/index";
		
	}
	
	

}
