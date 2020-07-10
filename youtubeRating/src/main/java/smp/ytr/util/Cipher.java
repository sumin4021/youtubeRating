package smp.ytr.util;

import java.util.Random;

public class Cipher {

	private final int KEY_LENGTH = 32;
	
	private String KEY = "";
	private String iv = "";
	private String salt = "";	
	
	public String getKEY() { return KEY; }
	public void setKEY(String KEY) { this.KEY = KEY; }
	public String getIv() { return iv; }
	public void setIv(String iv) { this.iv = iv; }
	public String getSalt() { return salt; }
	public void setSalt(String salt) { this.salt = salt; }
	
	
	private String getRandomString(int length) {
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		String chars[] = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9".split(",");
		for (int i = 0; i < length; i++) {
			buffer.append(chars[random.nextInt(chars.length)]);
		}
		return buffer.toString();
	}
	
	public Cipher() {
		setKEY(getRandomString(KEY_LENGTH));
		setIv(getRandomString(KEY_LENGTH));
		setSalt(getRandomString(KEY_LENGTH));
	}

	
}
