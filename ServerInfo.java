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
	//�ؽ�Ʈ ������ ��� ������ ip�ּҿ� ��Ʈ�ѹ��� �о�´�
	inputStream=new Scanner(new File(fileName));
	//ip �ּҰ� �о����
   	ip=inputStream.nextLine();
   	//��Ʈ�ѹ� �� �о����
	port=inputStream.nextLine();
	
}
catch(FileNotFoundException e) {
	System.out.println("File is not found.");
	System.out.println("default values are assigned. (IP:localhost, Port number:1234)");
	//����  �ؽ�Ʈ ������ �������� ���� ���, default ������ �����Ͽ� ó���Ѵ�(ip �ּ� :localhost,��Ʈ�ѹ�: 1234)
	ip="localhost";
	port="1234";

}
catch(Exception e) {
	System.out.println(e.toString());
	System.exit(0);
}

}

//IP �ּҰ� ��ȯ
public String returnIP() {
	return ip;
}

//��Ʈ �ѹ��� ��ȯ
public int returnPORT() {
	return Integer.parseInt(port);
}

}

