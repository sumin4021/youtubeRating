/**
 * MainController
 * 메인화면 접속.. 시 
 */
package smp.ytr.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import smp.ytr.service.MainService;

@Controller
public class MainController {

	@Autowired
	MainService svc;
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@RequestMapping(value = "/")
	public ModelAndView main(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("main/main");
		
		/*
		try {
			mv.addObject("hotVideo", svc.getHotVideo());
			
		} catch (SQLException e) {main_search
			e.printStackTrace();
		}
		*/
		
		return mv;
	}
	
	
	@RequestMapping(value="/main_search")
	public ModelAndView mainSearch(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("main/main_search");
		
		String schKeyword = request.getParameter("search_keyword");
		
		if(schKeyword != null && schKeyword != "") {
			mv.addObject("schKeyword", schKeyword);
			mv.addObject("streamers", svc.getStreamers(schKeyword));
		}else {
			mv.setViewName("redirect:/");
		}
		
		return mv;
	}
	
	//유투브 api를 이용한다. > DB에 저장한다. > 구글 로그인한 사용자에 한해 리프레시를 요청 할 수 있다.
	
	
}

