package mysite.event;

import java.lang.invoke.MutableCallSite;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.MutablePropertySources;

import mysite.service.SiteService;
import mysite.vo.SiteVo;

public class ApplicationContextEventListener {
	
	@Autowired
	private ApplicationContext applicationContext; // 왜 주입? 코드로 bean 등록 시키려고
	
	@EventListener({ContextRefreshedEvent.class})
	public void handlerContextRefreshedEvent() {
		System.out.println("-- Context Refreshed Event Received --");
		SiteService siteService = applicationContext.getBean(SiteService.class);
		SiteVo siteVo = siteService.getSite();
		
		MutablePropertyValues propertyValues = new MutablePropertyValues();
		propertyValues.add("title",siteVo.getTitle());
		propertyValues.add("welcome",siteVo.getWelcome());
		propertyValues.add("description",siteVo.getDescription());
		propertyValues.add("profile",siteVo.getProfile());
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(SiteVo.class);
		beanDefinition.setPropertyValues(propertyValues);
		
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
		registry.registerBeanDefinition("site", beanDefinition);
		
	}

}
