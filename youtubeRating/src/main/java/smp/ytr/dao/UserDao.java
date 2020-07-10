package smp.ytr.dao;

import java.sql.SQLException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import smp.ytr.dao.vo.UserVO;

@Repository
public class UserDao {

	@Autowired
	private SqlSessionTemplate mybatis;

	public int joinUsFromHome(UserVO vo) throws SQLException {
		return mybatis.insert("userMapper.joinUsFromHome", vo);
	}

	public UserVO loginFromHome(UserVO vo) throws SQLException {
		return mybatis.selectOne("userMapper.loginFromHome", vo);
	}

	
	
	public String isEmailOverlap(UserVO vo) throws SQLException {
		return mybatis.selectOne("userMapper.isEmailOverlap", vo);
	}

	public String isNickOverlap(UserVO vo) throws SQLException {
		return mybatis.selectOne("userMapper.isNickOverlap", vo);
	}

	public UserVO getUserInfoFromDB(UserVO vo) {
		return mybatis.selectOne("userMapper.getUserInfoFromDB", vo);
	}
}
