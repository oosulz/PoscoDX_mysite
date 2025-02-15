package mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import mysite.config.web.FileUploadConfig;
import mysite.config.web.LocaleConfig;
import mysite.config.web.MvcConfig;


@Controller
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Import({MvcConfig.class, LocaleConfig.class, FileUploadConfig.class})
@ComponentScan(basePackages= {"mysite.controller","mysite.exception"})
public class WebConfig {
	
}
