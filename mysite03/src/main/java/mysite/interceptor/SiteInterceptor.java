package mysite.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	private LocaleResolver localeResolver;
	private SiteService siteService;

	public SiteInterceptor(LocaleResolver localeResolver,SiteService siteService) {
		this.localeResolver = localeResolver;
		this.siteService = siteService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// locale
		String lang = localeResolver.resolveLocale(request).getLanguage();
		SiteVo siteVo = siteService.getSite();
		request.setAttribute("lang", lang);
		request.setAttribute("siteVo", siteVo);
		return true; 
	}

}