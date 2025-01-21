package mysite.config.web;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class LocaleConfig {

	@Bean
	public LocaleResolver cookielocaleResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver("lang");

		localeResolver.setCookieHttpOnly(false);
		return localeResolver;
	}
	
	@Bean
	//message Source
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("mysite/config/web/messages/message");
		messageSource.setDefaultEncoding("utf-8");
		return messageSource;
	}
}
