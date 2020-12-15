package network_teamproject;


import java.io.*;
import java.util.Scanner;

public class ServerInfo {
		
	String ip="";
	String port="";
	
public ServerInfo() {

String fileName="serverinfo.txt";
Scanner inputStream=null;


try {
	//텍스트 파일을 열어서 서버의 ip주소와 포트넘버를 읽어온다
	inputStream=new Scanner(new File(fileName));
	//ip 주소값 읽어오기
   	ip=inputStream.nextLine();
   	//포트넘버 값 읽어오기
	port=inputStream.nextLine();
	
}
catch(FileNotFoundException e) {
	System.out.println("File is not found.");
	System.out.println("default values are assigned. (IP:localhost, Port number:1234)");
	//만약  텍스트 파일이 존재하지 않을 경우, default 정보를 지정하여 처리한다(ip 주소 :localhost,포트넘버: 1234)
	ip="localhost";
	port="1234";

}
catch(Exception e) {
	System.out.println(e.toString());
	System.exit(0);
}

}

//IP 주소값 반환
public String returnIP() {
	return ip;
}

//포트 넘버값 반환
public int returnPORT() {
	return Integer.parseInt(port);
}

}

