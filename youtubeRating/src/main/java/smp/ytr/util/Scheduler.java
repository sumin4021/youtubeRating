package smp.ytr.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import smp.ytr.service.MainService;

@Component
public class Scheduler {

	@Autowired
	MainService svc;
	
	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

	/**
	 * 
	 * 함수명	: HotVideoUpdate_Scheduler
	 * 설명	: 매 시간마다 hot video gavi
	 * 작성일	: Apr 24, 2020 
	 * 작성자	: smpark
	 * 이력	:
		초	 | 분	| 시		| 일		| 월		| 요일	| 연도
		0~59 | 0~59	| 0~23	| 1~31	| 1~12	| 0~6	| 생략가능
	 */
    /*
    @Scheduled(cron="0 0 0/1 * * ?")
    public void HotVideoUpdate_Scheduler(){
    	logger.debug("load hotVideo update Scheduler start. . .");
    	//svc.updateHotVideo();
    }
	*/
}
