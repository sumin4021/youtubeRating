package smp.ytr.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import smp.ytr.dao.SocialLoginDao;
import smp.ytr.dao.UserDao;
import smp.ytr.dao.vo.UserVO;
import smp.ytr.service.SocialLoginService;
import smp.ytr.util.CommonUtil;
import smp.ytr.util.RandomNickMaker;


@Service
public class SocialLoginServiceImpl implements SocialLoginService {

	
	@Autowired
	SocialLoginDao dao;
	
	@Autowired
	UserDao userDao;
	
	private final String tokenURL_kakao 	= "https://kauth.kakao.com/oauth/token";
	private final String userInfoURL_kakao 	= "https://kapi.kakao.com/v2/user/me";
	
	private final String tokenURL_google	= "https://oauth2.googleapis.com/token";
	private final String userInfoURL_google	= "https://www.googleapis.com/oauth2/v3/userinfo";
	
	

	@Override
	public int refreshToken(UserVO userVO) throws SQLException {
		return dao.refreshToken(userVO);
	}

	
	@Override
	public Map<String, String> getToken_kakao(String code)  throws Exception{
		Map<String,String> tokenInfo = new HashMap<String, String>();
		
		HttpURLConnection conn 	= null;
		BufferedWriter bw 		= null;
		BufferedReader br 		= null;
			
		URL url = new URL(tokenURL_kakao);
		conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		
		bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		StringBuilder sb = new StringBuilder();
		sb.append("grant_type=authorization_code");
		sb.append("&client_id=ad7f2fd636fc8f915a672681570571af");
		sb.append("&redirect_uri=http://localhost/social/kakao");
		sb.append("&code=" + code);
		bw.write(sb.toString());
		bw.close();
		/*-- 여기까지 요청 --*/
		if(conn.getResponseCode() != 200) throw new Exception("err; not valid kakao server response");
		
		br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = "";
        String result = "";
        
        while ((line = br.readLine()) != null) { result += line; }
        
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);
        tokenInfo.put("access_token",element.getAsJsonObject().get("access_token").getAsString());
        tokenInfo.put("refresh_token",element.getAsJsonObject().get("refresh_token").getAsString());
		/*-- 여기까지 응답 --*/
        
        br.close();
		
		return tokenInfo;
	}

	
	@Override
	public UserVO getUserInfo_kakao(Map<String,String> tokens) throws Exception {
		UserVO userInfo = new UserVO();

		HttpURLConnection conn 	= null;
		BufferedReader br 		= null;
	
        URL url = new URL(userInfoURL_kakao);
        conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + tokens.get("access_token"));
        
        if(conn.getResponseCode() != 200) throw new Exception("err;");
        
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        String line = "";
        String result = "";
        while ((line = br.readLine()) != null) { result += line; }
        
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);
        
        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
        
        if(!kakao_account.getAsJsonObject().get("has_email").getAsBoolean()) //
        	throw new Exception("err; has not valid email user");
        
        String email 		= kakao_account.getAsJsonObject().get("email").getAsString();
        String age_range 	= kakao_account.getAsJsonObject().get("age_range").getAsString();
        String gender 		= kakao_account.getAsJsonObject().get("gender").getAsString();

        userInfo.setID(email);
        userInfo.setAGE(age_range);
        userInfo.setGENDER(gender);
        userInfo.setPLATFRM("K");
        userInfo.setA_TOKEN(tokens.get("access_token"));
        userInfo.setR_TOKEN(tokens.get("refresh_token"));
        //user nickname을 가져온뒤 없으면 랜덤 생성.
        userInfo.setNICK_NAME(setNick(userInfo));
        
	    return userInfo;
	}



	
	
	
	
	
	
	
	
	
	
	@Override
	public Map<String, String> getToken_google(String code) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> tokenInfo = new HashMap<String, String>();
		
		HttpURLConnection conn 	= null;
		BufferedWriter bw 		= null;
		BufferedReader br 		= null;
			
		URL url = new URL(tokenURL_google);
		conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		
		bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		StringBuilder sb = new StringBuilder();

		sb.append("code="+code);
		sb.append("&client_id=943953451988-s4dotjeqoclmas61kqrr3njdtl2tfo2c.apps.googleusercontent.com");
		sb.append("&client_secret=kTW_KuCE7aQNHCOdHbqMe5jH");
		sb.append("&redirect_uri=http://localhost/social/google");
		sb.append("&grant_type=authorization_code");
		bw.write(sb.toString());
		bw.close();
		/*-- 여기까지 요청 --*/
		if(conn.getResponseCode() != 200) throw new Exception("err; not valid google server response");
		
		br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = "";
        String result = "";
        
        while ((line = br.readLine()) != null) { result += line; }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);
        tokenInfo.put("access_token",element.getAsJsonObject().get("access_token").getAsString());
    	tokenInfo.put("refresh_token", element.getAsJsonObject().get("refresh_token") == null ? "" : element.getAsJsonObject().get("refresh_token").getAsString());
		/*-- 여기까지 응답 --*/
        
        br.close();
		
		return tokenInfo;
	}


	@Override
	public UserVO getUserInfo_google(Map<String, String> tokens) throws Exception {
		UserVO userInfo = new UserVO();

		HttpURLConnection conn 	= null;
		BufferedReader br 		= null;
	
        URL url = new URL(userInfoURL_google);
        conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + tokens.get("access_token"));
        
        if(conn.getResponseCode() != 200) throw new Exception("err;");
        
        br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        String line = "";
        String result = "";
        while ((line = br.readLine()) != null) { result += line; }
        
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);

        JsonObject google_account = element.getAsJsonObject();
        if(!google_account.get("email_verified").getAsBoolean())
        	throw new Exception("err; has not valid email user");
        
        String email 	= google_account.getAsJsonObject().get("email").getAsString();
        
        userInfo.setID(email);
        userInfo.setAGE("");
        userInfo.setGENDER("");
        userInfo.setPLATFRM("G");
        userInfo.setA_TOKEN(tokens.get("access_token"));
        userInfo.setR_TOKEN(tokens.get("refresh_token"));
        
        userInfo.setNICK_NAME(setNick(userInfo));
        
	    return userInfo;
	}



	/**
	 * 함수명	: setNick
	 * 설명	: @param userInfo
	 * 설명	: @return
	 * 설명	: @throws SQLException
	 * 작성일	: Jun 2, 2020 
	 * 작성자	: smpark
	 * 이력	: 닉네임 랜덤 생성. 중복제거. --> 나중에 좀 더 수정 필요 
	 */
	public String setNick(UserVO userInfo) throws SQLException {
		String nick 	= new RandomNickMaker().doGenerateRandNick();
		UserVO nickVO 	= userDao.getUserInfoFromDB(userInfo);
		
        if(nickVO != null) {
        	return nickVO.getNICK_NAME();
        } else {
        	if(CommonUtil.isEmpty(userDao.isNickOverlap(userInfo)))
        		return nick;
        	else 
        		return new RandomNickMaker().doGenerateRandNick();
        }
	}
	
}
