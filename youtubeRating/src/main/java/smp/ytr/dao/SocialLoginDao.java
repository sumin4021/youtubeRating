package smp.ytr.dao;

import java.sql.SQLException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import smp.ytr.dao.vo.UserVO;

@Repository
public class SocialLoginDao {

	@Autowired
	private SqlSessionTemplate mybatis;

	public int refreshToken(UserVO userVO) throws SQLException{
		return mybatis.insert("socialLoginMapper.refreshToken", userVO);
	}

}
