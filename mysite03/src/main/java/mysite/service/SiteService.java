package mysite.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.ServletContext;
import mysite.repository.SiteRepository;
import mysite.vo.SiteVo;

@Service
public class SiteService {
	private final SiteRepository siteRepository;
	private final ServletContext context;

	public SiteService(SiteRepository siteRepository, ServletContext context) {
		this.siteRepository = siteRepository;
		this.context =context;
	}

	public SiteVo getSite() {
		return siteRepository.findLastOne();
	}

	public void updateSite(SiteVo siteVo) {
		siteRepository.update(siteVo);
		context.setAttribute("siteVo", siteVo);
	}
}