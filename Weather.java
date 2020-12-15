package network_teamproject;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
	import java.net.URL;
	import java.net.URLEncoder;
	import java.io.BufferedReader;
	import java.io.IOException;
	import org.json.simple.JSONObject;
	import org.json.simple.JSONArray; //JSON�迭 ���
	import org.json.simple.parser.JSONParser;
	import org.json.simple.parser.ParseException;
	//���������� ó�� Ŭ���� 
public class Weather {
	
	

	    public String weather() throws Exception {
	    	
	    	//url
	    	String apiUrl = " http://apis.data.go.kr/1360000/VilageF"
	    			+ "cstInfoService/getVilageFcst";
	        // Ȩ���������� ���� Ű
	    	String serviceKey = "YmklZ928yJic8b0u38tg%2BPZ3S3lebZheO3HZQGAjKe0go2k9T70zKOhyhpP%2BkWJmPQUA1LI%2BjsQjf6EeWY8eOQ%3D%3D";
	    	
	    	// ��⵵ ������ �д籸 ������ ��ġ����
	    	String nx = "62"; //����
	    	String ny = "124"; //�浵
	    	
	    	String baseDate = "20201215"; //��ȸ�ϰ���� ��¥
	    	String baseTime = "1100"; //��ȸ�ϰ���� �ð�
	    	
	    	//������ Ÿ��
	    	String type = "JSON";
	    	
	    	
	    	
	    	
	    	
	        StringBuilder urlBuilder = new StringBuilder(apiUrl); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "="+serviceKey); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*��������ȣ*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("9", "UTF-8")); /*�� ������ ��� ��*/
	        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode(type, "UTF-8"));/*��û�ڷ�����(XML/JSON)Default: XML*/
	        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*20�� 11�� 23�� ��ǥ*/
	        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*23�� ��ǥ(���ô���)*/
	        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*���������� X ��ǥ��*/
	        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*�������� Y ��ǥ*/
	        URL url = new URL(urlBuilder.toString());
	        
	      //HttpURLconnection�� ���� URL�� ��Ʈ��ũ ���� �� �����͸� �޴´�
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        //System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        
	      //���� ������ ���� �о�´�.
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
	        
	      //�о�� ���������͸� ���ڿ��� ��ȯ
	        String json=sb.toString();
	        
	        /*
	         * POP ����Ȯ�� %
	         * PTY �������� �ڵ尪
	         * R06 6�ð� ������ ���� (1 mm)
	         * REH ���� %
	         * S06 6�ð� ������ ����(1 cm)
	         * SKY �ϴû��� �ڵ尪
	         * T3H 3�ð� ��� ��
	         * TMN ��ħ ������� ��
	         * TMX �� �ְ��� ��
	         * UUU ǳ��(��������) m/s
	         * VEC ǳ��  m/s
	         * VVV ǳ��(���ϼ���) m/s
	         * WAV �İ� M
	         * WSD ǳ��  m/s
	         */ 
	        
	        /*�ڵ尪 �ǹ�
	         - �ϴû���(SKY) �ڵ� : ����(1), ��������(3), �帲(4) 

	         - ��������(PTY) �ڵ� : ����(0), ��(1), ��/��(2), ��(3), �ҳ���(4), �����(5), �����/������(6), ������(7)
	                                                              ���⼭ ��/���� ��� ���� ���� ���� ���� �ǹ� (��������)

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
	        
	      //JSONArray�� ����Ͽ� �迭������ �����͸� �о�´�.
	        JSONArray arr = (JSONArray)items.get("item");
	        JSONObject t = (JSONObject)arr.get(0);
	        	        
	      //for���� ���� �迭���� JSON ������ parsing�Ͽ� ���
	        
	        
	        StringBuilder str=new StringBuilder();
	        
	        for(int i=0;i<9;i++) {
	        	JSONObject tmp = (JSONObject)arr.get(i);

	        	 str.append(tmp.get("category")+":"+tmp.get("fcstValue")+"/");  	 
	        }
	        
	        
	     
	        return str.toString();
	        
	        
	      
	    }
	}

