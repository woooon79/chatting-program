package network_teamproject;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
	import java.net.URL;
	import java.net.URLEncoder;
	import java.io.BufferedReader;
	import java.io.IOException;
	import org.json.simple.JSONObject;
	import org.json.simple.JSONArray; //JSON배열 사용
	import org.json.simple.parser.JSONParser;
	import org.json.simple.parser.ParseException;
	//공공데이터 처리 클래스 
public class Weather {
	
	

	    public String weather() throws Exception {
	    	
	    	//url
	    	String apiUrl = " http://apis.data.go.kr/1360000/VilageF"
	    			+ "cstInfoService/getVilageFcst";
	        // 홈페이지에서 받은 키
	    	String serviceKey = "YmklZ928yJic8b0u38tg%2BPZ3S3lebZheO3HZQGAjKe0go2k9T70zKOhyhpP%2BkWJmPQUA1LI%2BjsQjf6EeWY8eOQ%3D%3D";
	    	
	    	// 경기도 성남시 분당구 서현동 위치정보
	    	String nx = "62"; //위도
	    	String ny = "124"; //경도
	    	
	    	String baseDate = "20201215"; //조회하고싶은 날짜
	    	String baseTime = "1100"; //조회하고싶은 시간
	    	
	    	//데이터 타입
	    	String type = "JSON";
	    	
	    	
	    	
	    	
	    	
	        StringBuilder urlBuilder = new StringBuilder(apiUrl); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("9", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(type, "UTF-8"));/*요청자료형식(XML/JSON)Default: XML*/
	        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*20년 11월 23일 발표*/
	        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*23시 발표(정시단위)*/
	        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
	        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점 Y 좌표*/
	        URL url = new URL(urlBuilder.toString());
	        
	      //HttpURLconnection을 통해 URL에 네트워크 접속 후 데이터를 받는다
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        //System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        
	      //공공 데이터 값을 읽어온다.
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        
	      //읽어온 공공데이터를 문자열로 반환
	        String json=sb.toString();
	        
	        /*
	         * POP 강수확률 %
	         * PTY 강수형태 코드값
	         * R06 6시간 강수량 범주 (1 mm)
	         * REH 습도 %
	         * S06 6시간 신적설 범주(1 cm)
	         * SKY 하늘상태 코드값
	         * T3H 3시간 기온 ℃
	         * TMN 아침 최저기온 ℃
	         * TMX 낮 최고기온 ℃
	         * UUU 풍속(동서성분) m/s
	         * VEC 풍향  m/s
	         * VVV 풍속(남북성분) m/s
	         * WAV 파고 M
	         * WSD 풍속  m/s
	         */ 
	        
	        /*코드값 의미
	         - 하늘상태(SKY) 코드 : 맑음(1), 구름많음(3), 흐림(4) 

	         - 강수형태(PTY) 코드 : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4), 빗방울(5), 빗방울/눈날림(6), 눈날림(7)
	                                                              여기서 비/눈은 비와 눈이 섞여 오는 것을 의미 (진눈개비)

	         */
	     
	       
	        
	      //Parsing
	        JSONParser parser = new JSONParser();
	        JSONObject village_weather=null;
	        try {
	        	village_weather = (JSONObject)parser.parse(json);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        JSONObject response = (JSONObject)village_weather.get("response");
	        JSONObject body= (JSONObject)response.get("body");
	        JSONObject items = (JSONObject)body.get("items");
	        
	      //JSONArray를 사용하여 배열형식의 데이터를 읽어온다.
	        JSONArray arr = (JSONArray)items.get("item");
	        JSONObject t = (JSONObject)arr.get(0);
	        	        
	      //for문을 통해 배열안의 JSON 값들을 parsing하여 출력
	        
	        
	        StringBuilder str=new StringBuilder();
	        
	        for(int i=0;i<9;i++) {
	        	JSONObject tmp = (JSONObject)arr.get(i);

	        	 str.append(tmp.get("category")+":"+tmp.get("fcstValue")+"/");  	 
	        }
	        
	        
	     
	        return str.toString();
	        
	        
	      
	    }
	}

