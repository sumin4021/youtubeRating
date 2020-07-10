package smp.ytr.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import smp.ytr.dao.vo.StreamerVO;
import smp.ytr.service.impl.MainServiceImpl;

public class _YouTubeAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(MainServiceImpl.class);
	
	/*
	public static void main(String[] args) {
		_YouTubeAPI api = new _YouTubeAPI();
		api.search("우왁굳");
	}
	*/
	
	private final String API_KEY = "AIzaSyB_BA5QhZMtIgpI4Tz5nHPNACPd3ZpnKDc";
	private final long NUMBER_OF_VIDEOS_RETURNED = 5;
	private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
  	private final JsonFactory JSON_FACTORY = new JacksonFactory();
  	
	private YouTube youtube;
	private String THUMB = "";
	
	public _YouTubeAPI() {
		youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
			public void initialize(HttpRequest request) throws IOException {}
		}).setApplicationName("youtube-rating").build();
	}
	/**
	 * 
	 * 함수명	: search
	 * 설명	: @param query
	 * 설명	: @param type
	 * 작성일	: Jun 18, 2020 
	 * 작성자	: smpark
	 * 이력	:
	 * type - search api를 이용해서 channel id 를 얻은 뒤 channel 검색.
	 */
	public List<StreamerVO> search(String query) {
		
		logger.debug(" youtube api call, ["+query+"]");
		
		List<StreamerVO> resVO = new ArrayList<StreamerVO>(); 
		StreamerVO streamerVO = null;
		
		try {
			YouTube.Search.List search = youtube.search().list("id");

			search.setKey(API_KEY);
			search.setQ(query);
			search.setType("channel");
			search.set("regionCode", "KR");
			search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
			search.setFields("items(id/channelId)");
			
			SearchListResponse searchResponse = search.execute();
	
			List<SearchResult> schResult = searchResponse.getItems();
			if (schResult != null) {
				
				StringBuffer sb = new StringBuffer();
				for( SearchResult sch_result : schResult ){
					sb.append(sch_result.getId().getChannelId()+",");
				}
				
				YouTube.Channels.List channels = youtube.channels().list("snippet,statistics");
				channels.setKey(API_KEY);
				channels.setId(sb.toString().substring(0, sb.toString().length()-1));
				channels.setFields("items(id, snippet/title, snippet/description, snippet/thumbnails/medium/url, statistics/viewCount, statistics/subscriberCount, statistics/videoCount, statistics/hiddenSubscriberCount)");
				channels.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
				
				ChannelListResponse channelListResponse = channels.execute();
				List<Channel> chanResult = channelListResponse.getItems();
				if(chanResult != null) {
					for(Channel channel :chanResult) {
						streamerVO = new StreamerVO();

						JsonObject jObject = new JsonParser().parse(channel.toString()).getAsJsonObject();
						JsonObject snippet = jObject.getAsJsonObject("snippet");
						JsonObject statistics = jObject.getAsJsonObject("statistics");
						
						if(snippet.getAsJsonObject("thumbnails") != null)
							if(snippet.getAsJsonObject("thumbnails").getAsJsonObject("medium") != null)
								if(snippet.getAsJsonObject("thumbnails").getAsJsonObject("medium").get("url") != null)
									THUMB = snippet.getAsJsonObject("thumbnails").getAsJsonObject("medium").get("url").getAsString();

						streamerVO.setSCH_KEYWORD(query);
						streamerVO.setCHANNEL_ID(jObject.get("id").getAsString()); 
						streamerVO.setCHANNEL_TITLE(snippet.get("title") == null ? "" : snippet.get("title").getAsString());
						streamerVO.setCHANNEL_DESC(snippet.get("description") == null ? "" : snippet.get("description").getAsString());
						streamerVO.setTHUMB(THUMB);
						streamerVO.setSUBSC_CNT(statistics.get("subscriberCount") == null ? "" : statistics.get("subscriberCount").getAsString());
						streamerVO.setSUBSC_HIST(statistics.get("subscriberCount") == null ? "" : statistics.get("subscriberCount").getAsString());
						streamerVO.setVIDEO_CNT(statistics.get("videoCount") == null ? "" : statistics.get("videoCount").getAsString());
						streamerVO.setVIDEO_HIST(statistics.get("videoCount") == null ? "" : statistics.get("videoCount").getAsString());
						streamerVO.setVIEW_CNT(statistics.get("viewCount") == null ? "" : statistics.get("viewCount").getAsString());
						streamerVO.setVIEW_HIST(statistics.get("viewCount") == null ? "" : statistics.get("viewCount").getAsString());
						streamerVO.setIS_HIDE(statistics.get("hiddenSubscriberCount") == null ? 0 : statistics.get("hiddenSubscriberCount").getAsBoolean() == true ? 1 : 0); //1 true 0 false

						resVO.add(streamerVO);
						logger.debug(" youtube api call, cahnnelId = ["+streamerVO.getCHANNEL_ID()+"]");
						logger.debug(" youtube api call, cahnnelTitle = ["+streamerVO.getCHANNEL_TITLE()+"]");
					}
				}
				
			}else { //schResult == null
				return null;
			}
			
			return resVO;

		} catch (GoogleJsonResponseException e) {
			logger.debug("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
			return null;
		} catch (IOException e) {
			logger.debug("There was an IO error: " + e.getCause() + " : " + e.getMessage());
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

