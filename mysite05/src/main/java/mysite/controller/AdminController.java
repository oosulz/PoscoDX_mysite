package mysite.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import mysite.event.SiteContextListener;
import mysite.security.Auth;
import mysite.service.FileUploadService;
import mysite.service.SiteService;
import mysite.vo.BoardVo;
import mysite.vo.SiteVo;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final SiteService siteService;
	private final FileUploadService fileUploadService;
	private final ServletContext servletContext;
	private final ApplicationContext applicationContext;

	public AdminController(SiteService siteService, FileUploadService fileUploadService, ServletContext servletContext, ApplicationContext applicationContext) {
		this.siteService = siteService;
		this.fileUploadService = fileUploadService;
		this.servletContext = servletContext;
		this.applicationContext = applicationContext;
	}

	@RequestMapping({ "" })
	public String main(Model model) {
		SiteVo siteVo = siteService.getSite();
		model.addAttribute("siteVo", siteVo);
		return "admin/main";
	}

	@RequestMapping({ "/guestbook" })
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping({ "/board" })
	public String board() {
		return "admin/board";
	}

	@RequestMapping({ "/user" })
	public String user() {
		return "admin/user";
	}

	@RequestMapping("/update")
	public String update(
		@RequestParam("title") String title, 
		@RequestParam("welcome") String welcome,
		@RequestParam("description") String description,
		@RequestParam("file") MultipartFile file,
		Model model) {
		
		SiteVo siteVo = siteService.getSite();
		siteVo.setTitle(title);
		siteVo.setWelcome(welcome);
		siteVo.setDescription(description);
	    
	    if (file != null && !file.isEmpty()) {
	        String profilePath = fileUploadService.restore(file);
	        if (profilePath != null) {
	            siteVo.setProfile(profilePath);
	        }
	    }
	    
		siteService.updateSite(siteVo);
		
		// servlet context Bean
		servletContext.setAttribute("siteVo", siteVo);
		
		// update application context bean
		SiteVo site = applicationContext.getBean(SiteVo.class);
		BeanUtils.copyProperties(siteVo, site);
		
		return "redirect:/admin";
	}

}
