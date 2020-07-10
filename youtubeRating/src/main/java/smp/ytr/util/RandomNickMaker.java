package smp.ytr.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RandomNickMaker {

	public static void main(String[] args) throws Exception {
//		getNounFromWiki();
//		nounValidationCheck();
//		System.out.println(mergeNick());
	}
	
	@Deprecated
	public static void getNounFromWiki() throws IOException {
		final String URL = "https://ko.wiktionary.org/w/index.php?title=%EB%B6%84%EB%A5%98:%ED%95%9C%EA%B5%AD%EC%96%B4_%EB%AA%85%EC%82%AC&from=%EA%B0%80";
		Document doc = Jsoup.connect(URL).get(); 
		Element category = doc.getElementsByClass("mw-category").get(0);
		Elements liList = category.getElementsByTag("li");
		File file = new File("/Users/smpark/Desktop/text.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		
		for(int i = 0 ; i < liList.size() ; i++) {
			if(i == liList.size()-1) {
				getNounFromWiki(liList.get(i).text(), bw, file);
			}else {
				bw.write(",");
				bw.write(liList.get(i).text());
			}
		}
		bw.flush();
		bw.close();
	}
	
	@Deprecated
	public static void getNounFromWiki(String firstString, BufferedWriter bw, File file) throws IOException {
		String URL = "https://ko.wiktionary.org/w/index.php?title=%EB%B6%84%EB%A5%98:%ED%95%9C%EA%B5%AD%EC%96%B4_%EB%AA%85%EC%82%AC&pagefrom="+ URLEncoder.encode(firstString,"UTF-8") +"&subcatfrom=%EA%B0%80&filefrom=%EA%B0%80#mw-pages";
		Document doc = Jsoup.connect(URL).get(); 
		Element category = doc.getElementsByClass("mw-category").get(0);
		Elements liList = category.getElementsByTag("li");
		
		for(int i = 0 ; i < liList.size() ; i++) {
			if(i == liList.size()-1) {
				getNounFromWiki(liList.get(i).text(), bw, file);
			}else {
				bw.write(",");
				bw.write(liList.get(i).text());
			}
			if("흰죽".equals(liList.get(i).text())) {
				System.out.println("종료!");
				break;
			}
		}
		bw.flush();
	}
	
	
	
	public static void nounValidationCheck() throws IOException {
		//유효성 검증, 초성중성종성을 제거한다. 그리고다시 파일저장.
		File file = new File("/Users/smpark/Desktop/text.txt");
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String[] readLine = br.readLine().split(",");

		List<String> list = new CopyOnWriteArrayList<String>();
		Collections.addAll(list, readLine); 
		list.remove("apple");
	
		String[] hangul = {"ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ","ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ","ㅏ","ㅐ","ㅑ","ㅒ","ㅓ","ㅔ","ㅕ","ㅖ","ㅗ","ㅘ","ㅙ","ㅚ","ㅛ","ㅜ","ㅝ","ㅞ","ㅟ","ㅠ","ㅡ","ㅢ","ㅣ","ㄱ","ㄲ","ㄳ","ㄴ","ㄵ","ㄶ","ㄷ","ㄹ","ㄺ","ㄻ","ㄼ","ㄽ","ㄾ","ㄿ","ㅀ","ㅁ","ㅂ","ㅄ","ㅅ","ㅆ","ㅇ","ㅈ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"}; 
		
		int i = 0 ;
		for(String str : list) {
			i++;
			for(String han : hangul) {
				if(str.startsWith(han)) {
					list.remove(str);
				}
			}
			if(str.length()>3) {
				list.remove(str);
				i++;
				System.out.println(i+" 개 제거");
			}
		}
		br.close();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));

		int enter = 0;
		for(String str : list) {
			bw.write(str);
			bw.write(",");
			enter++;
			if(enter % 10 == 0) bw.write("\r\n");
		}
		bw.flush();
		bw.close();
		
	}
	

	public String doGenerateRandNick() {
		Random rand = new Random();
		InputStream in = null;
		BufferedReader br = null;
		List<String> readLine = null;
		List<String> resList = null;

		try {

			in = RandomNickMaker.class.getClassLoader().getResourceAsStream("dummyChar");
			br = new BufferedReader(new InputStreamReader(in));
			readLine = new ArrayList<String>();
			
			String rline = "";
			while((rline = br.readLine()) != null) {
				for(String str : rline.split(",")) {
					readLine.add(str);
				}
			}
			
	//		System.out.println(readLine);
			
			List<String> two = new CopyOnWriteArrayList<String>();
			List<String> three = new CopyOnWriteArrayList<String>();
			
			for(String str : readLine) {
				if(str.length() < 3) {
					two.add(str);
				}else {
					three.add(str);
				}
			}
			
	
			String first = two.get(rand.nextInt(two.size()));
			String third = " "+three.get(rand.nextInt(three.size()));
	
			String name1 = getComleteWordByJongsung(first, "을", "를")+third;
			String name2 = getComleteWordByJongsung(first, "이", "가")+third;
			String name3 = getComleteWordByJongsung(first, "은", "는")+third;
			String name4 = getComleteWordByJongsung(first, "으로", "로")+third;
			String name5 = getComleteWordByJongsung(first, "과", "와")+third;
			String name6 = first+"의"+third;
			
			String[] result = {name1,name2,name3,name4,name5,name6};
	
			resList = new CopyOnWriteArrayList<String>();
			Collections.addAll(resList, result); 
	
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return resList.get(rand.nextInt(resList.size()));
		
	}

	public String getComleteWordByJongsung(String name, String firstValue, String secondValue) {
		try {

			char lastName = name.charAt(name.length() - 1);
			if (lastName < 0xAC00 || lastName > 0xD7A3) {
				return name;
			}
			int lastCharIndex = (lastName - 0xAC00) % 28;
			if (lastCharIndex > 0) {
				if (firstValue.equals("으로") && lastCharIndex == 8) {
					name += secondValue;
				} else {
					name += firstValue;
				}
			} else {
				name += secondValue;
			}
		} catch (Exception e) {
//			 e.printStackTrace();
			return "";
		}
		return name;
	}

}
