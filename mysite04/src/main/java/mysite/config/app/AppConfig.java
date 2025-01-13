package mysite.config.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

@Controller
//@Import()
@ComponentScan(basePackages= {"mysite.service","mysite.repository","mysite.aspect"})
public class AppConfig {

}
