package smp.ytr.dao.vo;

/**
 * @author smpark
 *
 */
public class UserVO {
	
	private String ID;
	private String PW;
	private String PW_VALIDATION;
	private String NICK_NAME;
	private String GENDER;
	private String AGE;
	private String PLATFRM;
	private String A_TOKEN;
	private String R_TOKEN;
	private int LVL;
	private int LVL_POINT;
	private String RGST_DT;
	private String UP_DT;
	
	private String KEY;
	private String ivKey;
	private String saltKey;
	
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getPW() {
		return PW;
	}
	public void setPW(String pW) {
		PW = pW;
	}
	public String getPW_VALIDATION() {
		return PW_VALIDATION;
	}
	public void setPW_VALIDATION(String pW_VALIDATION) {
		PW_VALIDATION = pW_VALIDATION;
	}
	public String getNICK_NAME() {
		return NICK_NAME;
	}
	public void setNICK_NAME(String nICK_NAME) {
		NICK_NAME = nICK_NAME;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	public String getPLATFRM() {
		return PLATFRM;
	}
	public void setPLATFRM(String pLATFRM) {
		PLATFRM = pLATFRM;
	}
	public String getA_TOKEN() {
		return A_TOKEN;
	}
	public void setA_TOKEN(String a_TOKEN) {
		A_TOKEN = a_TOKEN;
	}
	public String getR_TOKEN() {
		return R_TOKEN;
	}
	public void setR_TOKEN(String r_TOKEN) {
		R_TOKEN = r_TOKEN;
	}
	public String getAGE() {
		return AGE;
	}
	public void setAGE(String aGE) {
		AGE = aGE;
	}
	public int getLVL() {
		return LVL;
	}
	public void setLVL(int lVL) {
		LVL = lVL;
	}
	public int getLVL_POINT() {
		return LVL_POINT;
	}
	public void setLVL_POINT(int lVL_POINT) {
		LVL_POINT = lVL_POINT;
	}
	public String getRGST_DT() {
		return RGST_DT;
	}
	public void setRGST_DT(String rGST_DT) {
		RGST_DT = rGST_DT;
	}
	public String getUP_DT() {
		return UP_DT;
	}
	public void setUP_DT(String uP_DT) {
		UP_DT = uP_DT;
	}
	public String getKEY() {
		return KEY;
	}
	public void setKEY(String kEY) {
		KEY = kEY;
	}
	public String getIvKey() {
		return ivKey;
	}
	public void setIvKey(String ivKey) {
		this.ivKey = ivKey;
	}
	public String getSaltKey() {
		return saltKey;
	}
	public void setSaltKey(String saltKey) {
		this.saltKey = saltKey;
	}
	
	
}
