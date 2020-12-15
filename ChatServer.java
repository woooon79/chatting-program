package network_teamproject;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ChatServer {
    
	//사용자 아이디 리스트
	private static ArrayList<String> IDs = new ArrayList<>();

	//사용자 정보 리스트
	private static ArrayList<User> members = new ArrayList<>();
	//온라인 사용자 리스트
	private static ArrayList<Integer> onlines=new ArrayList<>(); 
	// 모든 클라이언트들의 PrintWriter 리스트
	// Broadcast 시 사용
	// 특정 클라이언트 선택위해 Set이 아닌 ArrayList로 클라이언트마다  번호 부여
	private static ArrayList <PrintWriter> writers = new ArrayList<>();

    static File file;
    static int socketnum=-1;
    
    //로그인,로그아웃 출력 형식 지정
	static SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분ss초");

	public static void main(String[] args) throws Exception {
		//클라이언트와 새로운 연결 형성 준비
		System.out.println("The chat server is running...");
		ExecutorService pool = Executors.newFixedThreadPool(500);
		 ServerInfo server=new ServerInfo();
         int portnum=server.returnPORT(); //ServerInfo 클래스의 returnPORT() 메소드를 사용하여 포트넘버 값을 가져온다
         FileInputStream file=null;
         ObjectInputStream in=null;
        
         
         try{

  	
             file= new FileInputStream("users.txt");
             in = new ObjectInputStream(file);
             
         }catch (FileNotFoundException e) {
             // TODO: handle exception
        	 System.out.println("파일을 찾을 수 없습니다");
         }catch(IOException e){
             
         }


            	 User u = null;
            	 User m=null;

         		try {
         			  while(true){
         			// 파일에서 user들 u로 역직렬화 수행
         			u = (User)in.readObject();
         			System.out.println(u.getID()+" "+u.number);
         			members.add(u);
         			IDs.add(u.getID());
         			socketnum++;
         			//읽어오다가 더이상 읽을게 없다면 EOFException 예외 발생 
         			
         			  }
         			
         		} catch(Exception e) {
         			//예외발생하면 while문 탈출
         			System.out.println("파일끝");
         			
         		}

            
         
         try (ServerSocket listener = new ServerSocket(portnum)) {
			//클라이언트와 새로운 연결이 형성될 때까지 블로킹
			while (true) {
				pool.execute(new Handler(listener.accept()));
			}
		}
	}

	/**
	 * The client handler task.
	 */
	private static class Handler implements Runnable {
		private String ID;
		private int user_num=-1;
		private String PW;
		private Socket socket;
		private Scanner in;
		private PrintWriter out;

		/**
		 * Constructs a handler thread, squirreling away the socket. All the interesting
		 * work is done in the run method. Remember the constructor is called from the
		 * server's main method, so this has to be as short as possible.
		 */
		public Handler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * Services this thread's client by repeatedly requesting a screen name until a
		 * unique one has been submitted, then acknowledges the name and registers the
		 * output stream for the client in a global set, then repeatedly gets inputs and
		 * broadcasts them.
		 */
		public void run() {
			try {
				in = new Scanner(socket.getInputStream());
				out = new PrintWriter(socket.getOutputStream(), true);
				
				//날씨 공공데이터 읽어오기
				Weather w=new Weather();
				String w_info=w.weather();
				
				
				 String[] whr=w_info.split("/");
	
				 StringBuilder tdyweather=new StringBuilder();
				
				 for(int i=0;i<whr.length;i++) {
					 String value=whr[i].substring(whr[i].indexOf(":")+1);
					  
			     //날씨 정보 추출
                 switch(whr[i].substring(0,whr[i].indexOf(":"))) {
	        	 case"POP":
	        		 tdyweather.append("강수확률: "+value+"%"+",");
	        		 break;
	        		 
	        	 case "T3H":
	        		 tdyweather.append("3시간 기온: "+value+"℃"+",");
	        		 break;
	        		 
	        	 case "SKY":
	        		 if(value.equals("1"))
	        			 tdyweather.append("하늘상태: 맑음"+",");
	        		 else if (value.equals("3"))
	        			 tdyweather.append("하늘상태: 구름많음"+",");
	        		 else if (value.equals("4"))
	        			 tdyweather.append("하늘상태: 흐림"+",");
	        		 break;
	        		 
	        	 case "PTY":
	        		 if(value.equals("0"))
	        			 tdyweather.append("강수형태: 없음"+",");
	        		 else if (value.equals("1"))
	        			 tdyweather.append("강수형태: 비"+",");
	        		 else if (value.equals("2"))
	        			 tdyweather.append("강수형태: 비/눈"+",");
	        		 else if (value.equals("3"))
	        			 tdyweather.append("강수형태: 눈"+",");
	        		 else if (value.equals("4"))
	        			 tdyweather.append("강수형태: 소나기"+",");
	        		 else if (value.equals("5"))
	        			 tdyweather.append("강수형태: 빗방울"+",");
	        		 else if (value.equals("6"))
	        			 tdyweather.append("강수형태: 빗방울/눈날림"+",");
	        		 else if (value.equals("7"))
	        			 tdyweather.append("강수형태: 눈날림"+",");
	        		 break;
	        		 

	        		 
	        	 }
				 }
				
				
				

				//이름 중복되지 않을 때까지 계속해서 이름 요청	
				
				while (true) {
					
					out.println("SUBMITNAME");
					//클라이언트에게 이름요청 메세지보낸다
				
					//클라이언트에게 이름 전달받는다.
					String input = in.nextLine();	

					
					//회원가입시 아이디 중복확인
					if (input.startsWith("[CHK]")) {
						String tid=input.substring(6);
						if(IDs.contains(tid))
							out.println("EXIST");
						else
							out.println("NONEXIST");
						continue;
						
					}
					
					//회원가입
					else if (input.startsWith("[NEW]")) {
						String info_str=input.substring(6);
						 String[] new_info=info_str.split(" "); 
		                 User new_mem=new User(new_info[0],new_info[1],new_info[2],new_info[3],new_info[4],new_info[5]);
		                 socketnum++;
		                 System.out.println(socketnum);
		                 new_mem.number=socketnum;
		                 members.add(new_mem);
		                 IDs.add(new_info[0]);
	
						continue;
						
					}
					
					//아이디 비밀번호확인
					else if (input.startsWith("[IDPW]")) {
					String login=input.substring(7);	
					String[] idandpw=login.split(" ");
					ID=idandpw[0];
					PW=idandpw[1];
					

					synchronized (IDs) {
						if(IDs.contains(ID)) {
							user_num=IDs.indexOf(ID);
							if((members.get(user_num).getPW()).equals(PW)) {
								members.get(user_num).setOnline("true");
								Date time=new Date();
								String time1=format1.format(time);
								members.get(user_num).setLogin_time(time1);
						         //out.println("OTHERS "+names);
							     break;
							}
						}

					}
					
				
					out.println("DISALLOW");
					
					}
					

				}

				// Now that a successful name has been chosen, add the socket's print writer
				// to the set of all writers so this client can receive broadcast messages.
				// But BEFORE THAT, let everyone else know that the new person has joined!
				
				
				
	             StringBuilder f=new StringBuilder();
				
				for (User user : members.get(user_num).friends) {
	
						String temp=user.getNumber()+"/"+user.getID()+"/"+user.getToday()+"/"+user.getLogin_time()+"/"+user.getLogout_time()+"/"+user.getOnline()+"/"+user.getNickname()+",";
						f.append(temp);
						System.out.println("info is "+f);
						
					}
				
				//사용자에게  날씨정보 전달	
				out.println("[WEATHER] "+tdyweather.toString());
				//사용자에게 친구정보 전달
				out.println("FRIENDS "+f.toString());
               
				//사용자에게 사용자 정보 전달
				out.println("NAMEACCEPTED " + ID+","+user_num+","+members.get(user_num).getToday()+","+members.get(user_num).getNickname());
	
				//[Broadcast]
				//모든 클라이언트들에게 새로운 접속자가 접속했다는 메세지 전달 
				for (PrintWriter writer : writers) {
					writer.println("[ON] " + ID+"/"+members.get(user_num).getLogin_time());
				}
				
				//writers,onlines 리스트에 새로운 접속자 추가(클라이언트 본인)
				writers.add(out);
				onlines.add(user_num);
				
	
				//만약  offline인 사용자에게 메세지 요청 왔었다면, 접속시 그 사실을 알림
					if(!members.get(user_num).requires.isEmpty()) {
						int num=members.get(user_num).requires.size();
						int[] arr=new int[num];
						
						for(int i=0;i<num;i++) {
							arr[i]=members.get(user_num).requires.get(i);
							
							out.println("[REQUIRE2] "+members.get(arr[i]).getID()+"/"+members.get(arr[i]).getNickname());
						}
					
						
					}

		
				while (true) {
					//클라이언트에게 메세지 전달받음
					String input = in.nextLine();
					
					
				
					
					//메세지가 "/quit" 문자열로 시작시 return => 클라이언트 퇴장
					if (input.toLowerCase().startsWith("/quit")) {
						return;
					}
					
					//상대방이 메세지 요청 거절했을때 작업처리
					if (input.startsWith("[DENY]")) {
						
						String str=input.substring(7);
						
						int num=IDs.indexOf(str);
						int sender=onlines.indexOf(num);
						
						writers.get(sender).println("[SORRY] "+ID);
						
						
						 continue;
					}			
					
					//상대방이 채팅연결을 끊었을때 작업처리(내 채팅방도 없어진다)
	                 if (input.startsWith("[BYE]")) {
						
						String str=input.substring(6);
						
						String id1=str.substring(0,str.indexOf("/"));
						String id2=str.substring(str.indexOf("/")+1);
						
						int num=IDs.indexOf(id2);
						int sender=onlines.indexOf(num);
						
						writers.get(sender).println("[BYE] "+id1);
						
						
						
						 continue;
					}	
					
	                 //상대방의 메세지 연결요청 승인 처리
					if (input.startsWith("[ACCEPT]")) {
						
						String str=input.substring(9);
						int num=IDs.indexOf(str);
						int sender=onlines.indexOf(num);
						writers.get(sender).println("[APPROVE] "+ID);
						 continue;
					}
					
					//클라이언트들간의 채팅방 대화작업 처리
		           if (input.startsWith("[MSG]")) {
						

						String str=input.substring(6);
						String[] temp=str.split("/");
						

					    String id1=temp[0];
						String id2=temp[1];
						String msg=temp[2];
						
						int num1=IDs.indexOf(id1);
						int num2=IDs.indexOf(id2);
						

						
						int sender=onlines.indexOf(num1);
						int receiver=onlines.indexOf(num2);
						
						out.println("[MSG] "+id1+":"+msg+","+id2);
						writers.get(receiver).println("[MSG] "+id1+":"+msg+","+id1);
						
						
						 continue;
					}
					
			       
		           //채팅연결 요청 처리
		           if (input.startsWith("[REQUIRE]")) {
						
						int num=Integer.parseInt(input.substring(10));
						int receiver=-1;
						
						if(onlines.contains(num)) {
					    receiver=onlines.indexOf(num);
					    
					    writers.get(receiver).println("[REQUIRE2] "+ID+"/"+members.get(user_num).getNickname());
						out.println("[CHATTER] "+members.get(num).getID());
						
						}
						else {
							members.get(num).requires.add(user_num);
							out.println("[CHATTER] "+members.get(num).getID());
						}
						
	
					}
		           
					//내 정보 수정 작업 처리
				     if (input.startsWith("[MYINFO]")) {
						
						String tdy=input.substring(9,input.indexOf("/"));
						String nm=input.substring(input.indexOf("/")+1);
						
						members.get(user_num).setNickname(nm);
						members.get(user_num).setToday(tdy);
						
						
						for (PrintWriter writer : writers) {
							writer.println("[CINFO] " + user_num+","+nm+","+tdy);
							
						}
						
						
						 continue;
					}
				     
				     //클라이언트의 친구추가 작업 처리
		            if (input.startsWith("[ADDF]")) {
						
						User newf=null;
						String str=input.substring(7,input.indexOf(","));
						int num=Integer.parseInt(input.substring(input.indexOf(",")+1));
						for(User user:members) {
							if(user.getID().contentEquals(str)) {
								members.get(user_num).friends.add(user);
								newf=user;
								break;
							}
						}
						
						out.println("[FNEW] "+newf.getNumber()+"/"+newf.getID()+"/"+newf.getToday()+"/"+newf.getLogin_time()+"/"+newf.getLogout_time()+"/"+newf.getOnline()+"/"+newf.getNickname());
						
						
						 continue;
					}
		            
		            
					//사용자 검색 처리
					if (input.startsWith("[search]")) {
						
						
						
						String str=input.substring(9);
						int num=str.length();
						StringBuilder info=new StringBuilder();
				
						
						for (User user : members) {
							//System.out.println("hi"+ user.getID().substring(0,num));
							if(user.getID().length()>=num) {
							if((user.getID().substring(0,num)).equals(str)&&!user.getID().equals(ID)&&!(members.get(user_num).friends.contains(user))) {
								
								String temp=user.getNumber()+"/"+user.getID()+"/"+user.getToday()+"/"+user.getLogin_time()+"/"+user.getLogout_time()+"/"+user.getOnline()+"/"+user.getNickname()+",";
								info.append(temp);
							
								
							}
							}
						}
						
						
						
						//만약 일치하는 검색 항목이 없다면
						if(info.toString().equals(""))
							out.println("AGAIN");
						
						//일치하는 검색항목이 존재한다면
						else
						out.println("[SEARCH] "+info.toString());
						
						
						 continue;
					}
					
			
				
				
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				if (out != null) {
					//writers 리스트에서 클라이언트 항목 삭제
					writers.remove(out);
					
				
				}
				if (ID != null) {
					//콘솔창 출력
					System.out.println(ID + " is leaving");
					System.out.println(user_num);
					members.get(user_num).setOnline("false");
					Date time=new Date();
					String time1=format1.format(time);
					members.get(user_num).setLogout_time(time1);
					
					//[Broadcast]
					//모든 클라이언트들에게 클라이언트 퇴장 메세지 전달
					for (PrintWriter writer : writers) {
						
						writer.println("[OFF] " + ID+"/"+members.get(user_num).getLogout_time());
					}
					
					members.get(user_num).requires.clear();
					
					
				}
				
				//online 리스트에서 제거
				if(onlines.size()!=0) {
					onlines.remove(onlines.indexOf(user_num));
				}
			
				//소켓 종료
				try { socket.close(); } catch (IOException e) {}

         		try( ObjectOutputStream outf = new ObjectOutputStream
         				(new FileOutputStream("users.txt"))) {
         			
         			 for (User user : members) {
 						    user.setOnline("false");
 							outf.writeObject(user);
 					}
         			
         		} catch(Exception e) {
         			
         			System.out.println("직렬화 실패");
         		}

				
			}
		}
	}
}




