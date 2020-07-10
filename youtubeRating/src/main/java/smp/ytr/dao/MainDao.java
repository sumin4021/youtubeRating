package smp.ytr.dao;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import smp.ytr.dao.vo.HotVideoVO;
import smp.ytr.dao.vo.StreamerVO;

@Repository
public class MainDao {

	@Autowired
	private SqlSessionTemplate mybatis;

	public List<HotVideoVO> getHotVideo() throws SQLException {
		return mybatis.selectList("mainMapper.getHotVideo");
	}

	public int updateHotVideo(List<HotVideoVO> hotVideoList) throws SQLException {
		return mybatis.insert("mainMapper.updateHotVideo", hotVideoList);
	}

	public List<StreamerVO> getStreamers(String sch_value) throws SQLException {
		return mybatis.selectList("mainMapper.getStreamers", sch_value);
	}

	public int updateStreamers(StreamerVO vo) throws SQLException {
		return mybatis.insert("mainMapper.updateStreamers", vo);
	}
}
