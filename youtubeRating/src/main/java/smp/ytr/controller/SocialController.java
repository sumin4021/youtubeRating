/**
 * 1. 소셜로그인 해서 인증만 성공하면 회원정보에(DB) 입력하고.
 * 2. 세션을 발급해줘서 회원과 동일한 권한을 준다.
 * 3. 다음에 다시 접속할 경우. 로그인 버튼을 눌러서 가지고 있던 토큰으로 사용자 인증을 한다 
 * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#refresh-token 참조.
 */

package smp.ytr.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import smp.ytr.dao.vo.UserVO;
import smp.ytr.service.SocialLoginService;
import smp.ytr.util.CommonUtil;


@RequestMapping("social")
@Controller
public class SocialController {

	@Autowired
	SocialLoginService svc;

	
	@RequestMapping("/kakao")
	public ModelAndView kakao(HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:/");
		
		String code = request.getParameter("code");
		
		try {
			Map<String, String> tokens	= svc.getToken_kakao(code);
			UserVO vo 					= svc.getUserInfo_kakao(tokens);

			if(vo.getID() != null || !"".equals(vo.getID())) {
				if(svc.refreshToken(vo) > 0) { //리프레쉬 카카오 유저!

					session.setAttribute("session_user_info", vo); //세션 생성
					mv.addObject("social_login", "success;");
					
				} else {
					mv.addObject("social_login", CommonUtil.URLencode("fail;카카오 계정 로그인에 실패 했습니다. 다시 시도해주세요."));
				}
			}else {
				mv.addObject("social_login", CommonUtil.URLencode("fail;카카오 계정에 email 값이 없어 사용 하실 수 없습니다. 다른 방법을 이용해주세요."));
			}
			
		} catch (Exception e) {
			mv.addObject("social_login", "err;"+e.getMessage());
		}
		
		return mv;
	}

	
	
	
	
	@RequestMapping(value = "/google")
	public ModelAndView google(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		ModelAndView mv = new ModelAndView("redirect:/");
		
		String code = request.getParameter("code");
		try {
			Map<String, String> tokens	= svc.getToken_google(code);
			UserVO vo 					= svc.getUserInfo_google(tokens);
			
			if(vo.getID() != null || !"".equals(vo.getID())) {
				if(svc.refreshToken(vo) > 0) { //리프레쉬 구글 유저!

					session.setAttribute("session_user_info", vo); //세션 생성
					mv.addObject("social_login", "success;");
					
				} else {
					mv.addObject("social_login", CommonUtil.URLencode("fail;구글 계정 로그인에 실패 했습니다. 다시 시도해주세요."));
				}
			}else {
				mv.addObject("social_login", CommonUtil.URLencode("fail;구글 계정에 email 값이 없어 사용 하실 수 없습니다. 다른 방법을 이용해주세요."));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("social_login", "err;"+e.getMessage());
		}
		
		return mv;
	}
	
}
