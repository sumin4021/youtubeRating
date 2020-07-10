package smp.ytr.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import smp.ytr.dao.MainDao;
import smp.ytr.dao.vo.HotVideoVO;
import smp.ytr.dao.vo.StreamerVO;
import smp.ytr.service.MainService;
import smp.ytr.service._YouTubeAPI;
import smp.ytr.util.selenium.SeleniumMain;

@Service
public class MainServiceImpl implements MainService {
	
	private static final Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);
	
	@Autowired
	MainDao dao;

	/*	

	@Override
	public List<HotVideoVO> getHotVideo() throws SQLException {
		return dao.getHotVideo();
	}

	@Override
	public int updateHotVideo() {
		
		//SeleniumMain _sel = new SeleniumMain();
		List<HotVideoVO> hotVideoList = null;
		int updateReuslt = 0;
		
		try {
			//hotVideoList	= _sel.getUpdateHotVideoList();
			System.out.println(hotVideoList);
		 	updateReuslt = dao.updateHotVideo(hotVideoList); //UPDATE ROWS
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return updateReuslt;
	}
	*/


	/**
		1. DB조회.
		2. DB에 없는 조회 결과인 경우. > 혹은 사용자의 리프레쉬 요청.?
		-
		3. api call
		4. 조회 된 셀니엄 결과는 DB에 저장한ㄷㅏ.
	 */
	@Override
	public List<StreamerVO> getStreamers(String query) {
		List<StreamerVO> streamers = null;
		_YouTubeAPI api = new _YouTubeAPI();
		
		try {
			streamers = dao.getStreamers(query); //DB조회.
			
			if(streamers.size() < 2) { //DB조회 결과 너무 적으면
				streamers = api.search(query); // api 호출
				if(streamers.size() == 0) return new ArrayList<StreamerVO>();//api call 결과 0 인 경우. 결과가 없습니다!.
				for(StreamerVO vo: streamers) {
					dao.updateStreamers(vo);//업데트, 검색어도 업데이트.
				}
			}
			
			streamers = dao.getStreamers(query); //DB 다시 조회. (order 및 콤마 작업 위함.)
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return streamers;
	}
	
}
