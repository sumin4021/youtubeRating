package smp.ytr.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import smp.ytr.dao.UserDao;
import smp.ytr.dao.vo.UserVO;
import smp.ytr.service.UserService;
import smp.ytr.util.Cipher;
import smp.ytr.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao dao;

	@Override
	public Map<String, String> getCryptoData() {
		Cipher cipher = new Cipher();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("ivKey", cipher.getIv());
		map.put("saltKey", cipher.getSalt());
		map.put("key", cipher.getKEY());
		
		return map;
	}

	@Override
	public String joinUsFromHome(UserVO vo) throws SQLException {
		String res = "";
		if(dao.joinUsFromHome(vo) < 1) {
			res = "fail";	
		} else {
			res = "suc";
		}
		
		return res;
	}
	
	@Override
	public UserVO loginFromHome(UserVO vo) throws SQLException {
		return dao.loginFromHome(vo);
	}

	
	@Override
	public boolean isEmailOverlap(UserVO vo) throws SQLException {
		return CommonUtil.isEmpty(dao.isEmailOverlap(vo));
	}

	@Override
	public boolean isNickOverlap(UserVO vo) throws SQLException {
		return CommonUtil.isEmpty(dao.isNickOverlap(vo));
	}


	
	
	
	
	
	@Override
	public UserVO getUserInfoFromDB(UserVO vo) throws SQLException {
		return dao.getUserInfoFromDB(vo);
	}
	
}
