package smp.ytr.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.spec.KeySpec;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class CommonUtil {
	
	private static final int KEY_SIZE = 128;
	private static final int ITER_CNT = 1000;
	
	/**
	 * 
	 * 함수명	: passwordValidationCheck
	 * 설명	: @param id
	 * 설명	: @param pw
	 * 설명	: @return
	 * 작성일	: May 2, 2020 
	 * 작성자	: smpark
	 * 이력	: 
			1. 영문(대소문자 구분), 숫자, 특수문자 조합
			2. 8~20자리 사이 문자
			3. 같은 문자 4개 이상 사용 불가
			4. 공백문자 사용 불가
	 */
	public static String isPassowrdPattern(String pw) {
		String msg = "";
		
//		String pwPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-z])(?=.*[A-Z]).{8,20}$"; 특수문자까지.
		String pwPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
		Matcher matcher = Pattern.compile(pwPattern).matcher(pw);
		 
		pwPattern = "(.)\\1\\1\\1";
		Matcher matcher2 = Pattern.compile(pwPattern).matcher(pw);

		if(pw.length() < 8 || pw.length() > 20) { return "패스워드 길이는 8-20 자 내외로 정해주세요."; }
		if(!matcher.matches()){ return "패스워드는 대문자를 포함한 영문+숫자의 조합으로 정해주세요."; }
		if(matcher2.find()){ return "패스워드에 같은 문자가 4회이상 반복할 수 없습니다."; }
		if(pw.contains(" ")){ return "패스워드에 공백이 포함될 수 없습니다."; }
		
		return msg;
	}
	
	
	/**
	 * 
	 * 함수명	: isEmailPattern
	 * 설명	: @param email
	 * 설명	: @return
	 * 작성일	: May 3, 2020 
	 * 작성자	: smpark
	 * 이력	:
	 */
	public static String isEmailPattern(String email) {
		String msg = "이메일 형식이 아닙니다.";
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		if (m.matches()) {
			msg = "";
		}
		return msg;
	}
	

	/**
	 * 
	 * 함수명	: decrypt
	 * 설명	: @param salt
	 * 설명	: @param iv
	 * 설명	: @param key
	 * 설명	: @param ciphertext
	 * 설명	: @return
	 * 작성일	: May 3, 2020 
	 * 작성자	: smpark
	 * 이력	:
	 */
    public static String decrypt(String salt, String iv, String key, String ciphertext ) {
    	SecretKeyFactory factory = null;
    	KeySpec keySpec = null;
    	SecretKey secretKey = null;
    	Cipher cipher = null;
    	String decryptTxt = "";
    	try {
			
    		factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	        keySpec = new PBEKeySpec(key.toCharArray(), Hex.decodeHex(salt.toCharArray()), ITER_CNT, KEY_SIZE);
	        secretKey = new SecretKeySpec(factory.generateSecret(keySpec).getEncoded(), "AES");        
	        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(Hex.decodeHex(iv.toCharArray())));        
	        decryptTxt = new String(cipher.doFinal(Base64.decodeBase64(ciphertext)),"UTF-8");        
	        
    	} catch (Exception e) {
			e.printStackTrace();
			decryptTxt = "err_decrypt";
		}
    	
    	return decryptTxt;
    }
    
    
    public static boolean isEmpty(Object s) { 
    	if (s == null) { 
    		return true; 
    	} 
    	if ((s instanceof String) && (((String)s).trim().length() == 0)) { 
    		return true; 
		} 
    	if (s instanceof Map) { 
    		return ((Map<?, ?>)s).isEmpty(); 
		} 
    	if (s instanceof List) { 
    		return ((List<?>)s).isEmpty(); 
		} 
    	if (s instanceof Object[]) { 
    		return (((Object[])s).length == 0); 
		} 
    	return false; 
	}

    
    public static String URLencode(String value) {
    	try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
	    	return "";
		}
    }
    
    public static String URLdecode(String value) {
    	try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (Exception e) {
			return "";
		}
    }
    
    
}
