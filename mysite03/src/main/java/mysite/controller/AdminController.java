package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mysite.security.Auth;
import mysite.service.FileUploadService;
import mysite.service.SiteService;
import mysite.vo.BoardVo;
import mysite.vo.SiteVo;

@Controller
@RequestMapping("/admin")
@Auth(role="ADMIN")
public class AdminController {
	
	private final SiteService siteService;
	private final FileUploadService fileUploadService;
	
	public AdminController(SiteService siteService, FileUploadService fileUploadService) {
		this.siteService = siteService;
		this.fileUploadService = fileUploadService;
	}
	
	@RequestMapping({""})
	public String main(Model model) {
		SiteVo siteVo = siteService.getSite();
		model.addAttribute("siteVo", siteVo);
		return "admin/main";
	}
	
	@RequestMapping({"/guestbook"})
	public String guestbook() {
		return "admin/guestbook";
	}
	

	@RequestMapping({"/board"})
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping({"/user"})
	public String user() {
		return "admin/user";
	}
	
	@RequestMapping("/update")
	public String update(
		@RequestParam("title") String title, 
		@RequestParam("welcomeMessage") String welcomeMessage,
		@RequestParam("description") String description,
		@RequestParam("file1") MultipartFile file,
		Model model) {
		
		SiteVo siteVo = siteService.getSite();
		siteVo.setTitle(title);
		siteVo.setWelcome(welcomeMessage);
		siteVo.setDescription(description);
	    
	    if (file != null && !file.isEmpty()) {
	        String profilePath = fileUploadService.restore(file);
	        if (profilePath != null) {
	            siteVo.setProfile(profilePath);
	        }
	    }
	    
		System.out.println(siteVo.toString());
		siteService.updateSite(siteVo);
		
		return "redirect:/admin";
	}
	
}
