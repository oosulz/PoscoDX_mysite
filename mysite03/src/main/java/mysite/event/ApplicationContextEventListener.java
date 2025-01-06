package mysite.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import mysite.service.SiteService;

public class ApplicationContextEventListener {
	
	@Autowired
	private ApplicationContext applicationContext; // 왜 주입? 코드로 bean 등록 시키려고
	
	@EventListener({ContextRefreshedEvent.class})
	public void handlerContextRefreshedEvent() {
		
		SiteService siteService = applicationContext.getBean(SiteService.class);
		System.out.println("-- Context Refreshed Event Received --" + siteService.toString());
	}

}
