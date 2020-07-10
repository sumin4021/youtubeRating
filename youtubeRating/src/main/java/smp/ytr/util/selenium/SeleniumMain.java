package smp.ytr.util.selenium;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smp.ytr.dao.vo.HotVideoVO;
import smp.ytr.dao.vo.StreamerVO;

public class SeleniumMain {
/*
	private static final Logger logger = LoggerFactory.getLogger(SeleniumMain.class);
	
	public List<HotVideoVO> getUpdateHotVideoList(){
		List<HotVideoVO> voList = new ArrayList<HotVideoVO>();
		HotVideoVO vo = null;
		
		SeleniumUtil util = new SeleniumUtil();
		WebDriver driver = util.getWebDriver();
		driver.get("https://www.youtube.com");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		WebElement hotSection = driver.findElements(By.tagName("ytd-rich-section-renderer")).get(0);
		js.executeScript("arguments[0].scrollIntoView(true);", hotSection);
		
		WebElement showMoreBtn = hotSection.findElement(By.id("button-container"));
		util.wait("id", "button-container", 10);
		showMoreBtn.click();
		
		WebElement contentsWrap = hotSection.findElement(By.id("contents"));
		util.wait("id", "contents", 10);
		
		List<WebElement> contentsList = contentsWrap.findElements(By.tagName("ytd-rich-item-renderer"));
		for(WebElement content : contentsList) {
			vo = new HotVideoVO();
			vo.setIMG_SRC(content.findElement(By.id("img")).getAttribute("src"));
			voList.add(vo);
		}

		driver.close();
		
		return voList;
	}
	
	
	
	
	public List<StreamerVO> getStreamers(String sch_value) throws Exception{
		List<StreamerVO> streamers = new ArrayList<StreamerVO>();
		StreamerVO streamer = null;
		SeleniumUtil util = new SeleniumUtil();
		WebDriver driver = util.getWebDriver();
		
		String URL = "https://www.youtube.com/results?sp=EgIQAg%253D%253D&search_query="+URLEncoder.encode(sch_value,"UTF-8");
		
		driver.get(URL);
		
		try {
			Document doc = Jsoup.parse(driver.getPageSource());
	
			int loopCnt = 0;
			Elements contents = doc.getElementById("content-section").parent().parent().children();
			if(contents.size() <= 1) { //검색결과가 없거나 너무 적을 경우.
				return streamers;
			}
			for(Element content : contents) {
				if(loopCnt >= 5) break;
				
				streamer = new StreamerVO();
				Element content_section = content.getElementById("content-section");
					
				streamer.setCHANNEL_ID(content_section.select("#avatar-section > a").attr("href")); //채널아이디
				streamer.setTHUMBNAIL_SM(content_section.select("#avatar #img").attr("src")); //채널 섬네일 
				streamer.setCHANNEL_NAME(content_section.select("#text-container #text").text()); //채널 이름 
				streamer.setSUBSCRIBERS(content_section.select("#metadata > #subscribers").text() == "" ? "-명" : content_section.select("#metadata > #subscribers").text()); //채널 구독자 수
				streamer.setVIDEO_CNT(content_section.select("#metadata > #video-count").text() == "" ? "-개" : content_section.select("#metadata > #video-count").text()); //채널 영상 수 
				streamer.setSCH_KEYWORD(sch_value);
				
				streamers.add(streamer);
				loopCnt++;
			}
		} catch (Exception e) {
			return new ArrayList<StreamerVO>();
		}
		
		return streamers;
	}
	*/
	
}
