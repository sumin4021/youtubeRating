package smp.ytr.service;

import java.sql.SQLException;
import java.util.Map;

import smp.ytr.dao.vo.UserVO;

public interface SocialLoginService {

	Map<String, String> getToken_kakao(String authResponse) throws Exception;

	UserVO getUserInfo_kakao(Map<String,String> tokens) throws Exception;


	
	
	Map<String, String> getToken_google(String code) throws Exception;

	UserVO getUserInfo_google(Map<String, String> tokens) throws Exception;

	
	
	int refreshToken(UserVO userVO) throws SQLException;

}
