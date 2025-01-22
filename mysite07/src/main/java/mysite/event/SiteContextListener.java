package mysite.event;

import org.springframework.context.ApplicationContext;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

public class SiteContextListener implements ServletContextListener {
	
    private SiteService siteService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        ApplicationContext springContext = 
            (ApplicationContext) context.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
        
        this.siteService = springContext.getBean(SiteService.class);
        SiteVo siteVo = siteService.getSite();
        context.setAttribute("siteVo", siteVo);
    }
}          