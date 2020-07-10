package smp.ytr.service;

import java.sql.SQLException;
import java.util.List;

import smp.ytr.dao.vo.HotVideoVO;
import smp.ytr.dao.vo.StreamerVO;

public interface MainService {

	/*
	public List<HotVideoVO> getHotVideo() throws SQLException;
	public int updateHotVideo();
	*/
	
	public List<StreamerVO> getStreamers(String sch_value);
	
}
