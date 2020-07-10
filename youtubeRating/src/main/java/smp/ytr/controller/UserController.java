/**
 * UserController
 */
package smp.ytr.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import smp.ytr.dao.vo.UserVO;
import smp.ytr.service.UserService;
import smp.ytr.util.CommonUtil;
import smp.ytr.util.RandomNickMaker;


@Controller	
public class UserController {

	@Autowired
	UserService svc;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * 
	 * 함수명	: join
	 * 설명	: 
	 * 설명	: @return
	 * 작성일	: May 3, 2020 
	 * 작성자	: smpark
	 * 이력	:
	 * 최초 로그인 시 iv, salt, key 랜덤 생성하여 join Page로 보내준다. > 매 로그인 화면 접속 시 마다 초기화 됨.
	 */
	@RequestMapping(value = "/join")
	public ModelAndView join(){

		ModelAndView mv = new ModelAndView("user/join");
		
		Map<String,String> cryptoData = svc.getCryptoData();
		mv.addObject("ivKey", cryptoData.get("ivKey"));
		mv.addObject("saltKey", cryptoData.get("saltKey"));
		mv.addObject("key", cryptoData.get("key"));
		
		return mv;
	}
	
	@RequestMapping(value = "/joinSuccess")
	public ModelAndView joinSuccess(HttpServletRequest request, HttpSession session){

		ModelAndView mv = new ModelAndView("redirection:/");
		
		return mv;
	}
	
	

	/**
	 * 함수명	: pwChkAndJoin
	 * 설명	: @param vo
	 * 설명	: @return
	 * 작성일	: May 3, 2020 
	 * 작성자	: smpark
	 * 이력	:
	 * join페이지에서 받은 iv,salt,key 를 기반으로 PBKDF2 방식으로 암호화된 패스워드를 전달받아 유효성 검증을 진행한다.
	 * 실질적인 로그인 시에는 sha256 으로 해싱된 패스워드를 사용한다.
	 * 실제 가입이 이루어지는 펑션.
	 */
	@ResponseBody
	@RequestMapping(value = "/pwChkAndJoin", method = RequestMethod.POST)
	public Map<String,String> pwChkAndJoin(@ModelAttribute UserVO vo, HttpSession session) {

		String id_validation	= CommonUtil.isEmailPattern(vo.getID());
		String pw_validation	= CommonUtil.isPassowrdPattern( CommonUtil.decrypt(vo.getSaltKey(), vo.getIvKey(), vo.getKEY(), vo.getPW_VALIDATION()) );
		String joinResult		= ""; 
		
		Map<String,String> resMap = new HashMap<String, String>();
		resMap.put("id_msg", id_validation);
		resMap.put("pw_msg", pw_validation);
		
		if("".equals(id_validation) && "".equals(pw_validation)) {
			logger.debug("user ["+vo.getID()+"] has valid");
			
			try {
				logger.debug("db insert");
				joinResult = svc.joinUsFromHome(vo);
				
				session.setAttribute("session_user_info", svc.loginFromHome(vo));			
				
			} catch (Exception e) {
				joinResult = "회원가입 실패";
			}

			resMap.put("join_result", joinResult);
		}
		return resMap;
	}
	
	
	/**
	 * 함수명	: overlapCheck
	 * 설명	: @param vo
	 * 설명	: @return
	 * 작성일	: May 3, 2020 
	 * 작성자	: smpark
	 * 이력	: 
	 * 이메일과 닉네임 중복확인
	 */
	@ResponseBody
	@RequestMapping(value = "/overlapCheck", method = RequestMethod.POST)
	public Map<String, String> overlapCheck(@ModelAttribute UserVO vo){
		Map<String,String> resMap = new HashMap<String, String>();
		
		try {
			
			if(CommonUtil.isEmpty(vo.getID())) {
				if(svc.isNickOverlap(vo)) 
					 resMap.put("nick", "ok"); //nick ok
				else resMap.put("nick", "overlap"); //nick overlap
				
			}else {
				if(svc.isEmailOverlap(vo))
					 resMap.put("email", "ok"); //email ok
				else resMap.put("email", "overlap"); //email overlap
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resMap;
	}
	
	
	/**
	 * 
	 * 함수명	: randNickMaker
	 * 설명	: @return
	 * 작성일	: May 15, 2020 
	 * 작성자	: smpark
	 * 이력	: 
	 * 랜덤 닉네임 생성 기능.
	 * 일반 로그인 사용자의 경우 닉네임 랜덤 생성은 선택.
	 * 소셜 로그인 사용자의 경우 닉네임 랜덤 생성으로 닉네임 사용. > 회원정보 수정.. 
	 */
	@ResponseBody
	@RequestMapping(value = "/randNickMaker", method = RequestMethod.POST)
	public Map<String, String> randNickMaker(){
		Map<String,String> resMap = new HashMap<String, String>();
		resMap.put("nick", new RandomNickMaker().doGenerateRandNick());
		
		return resMap;
	}
	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> login(@ModelAttribute UserVO vo, HttpServletRequest request, HttpSession session){
		Map<String,String> resMap = new HashMap<String, String>();
		
		try {
			UserVO resVO = svc.loginFromHome(vo);
			if(CommonUtil.isEmpty(resVO)) {
				resMap.put("isLogin","f");
			}else {
				session.setAttribute("session_user_info", resVO);
				resMap.put("isLogin","s");
			}
		} catch (Exception e) {
			resMap.put("isLogin","f");
			e.printStackTrace();
		}
		return resMap;
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request, HttpSession session){
		
		//TODO request.getParameter("nick") 으로 된 계쩡만 삭제.
		session.removeAttribute("session_user_info");
		
		return "redirect:"+request.getParameter("nowURL");
	}
	
	
	
}

