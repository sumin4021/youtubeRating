package smp.ytr.service;

import java.sql.SQLException;
import java.util.Map;

import smp.ytr.dao.vo.UserVO;

public interface UserService {

	Map<String, String> getCryptoData();

	String joinUsFromHome(UserVO vo) throws SQLException;
	
	UserVO loginFromHome(UserVO vo) throws SQLException;

	
	boolean isEmailOverlap(UserVO vo) throws SQLException;
	boolean isNickOverlap(UserVO vo) throws SQLException;

	

	UserVO getUserInfoFromDB(UserVO vo) throws SQLException;;

}
