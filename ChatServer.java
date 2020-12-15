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
    
	//����� ���̵� ����Ʈ
	private static ArrayList<String> IDs = new ArrayList<>();

	//����� ���� ����Ʈ
	private static ArrayList<User> members = new ArrayList<>();
	//�¶��� ����� ����Ʈ
	private static ArrayList<Integer> onlines=new ArrayList<>(); 
	// ��� Ŭ���̾�Ʈ���� PrintWriter ����Ʈ
	// Broadcast �� ���
	// Ư�� Ŭ���̾�Ʈ �������� Set�� �ƴ� ArrayList�� Ŭ���̾�Ʈ����  ��ȣ �ο�
	private static ArrayList <PrintWriter> writers = new ArrayList<>();

    static File file;
    static int socketnum=-1;
    
    //�α���,�α׾ƿ� ��� ���� ����
	static SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy�� MM��dd�� HH��mm��ss��");

	public static void main(String[] args) throws Exception {
		//Ŭ���̾�Ʈ�� ���ο� ���� ���� �غ�
		System.out.println("The chat server is running...");
		ExecutorService pool = Executors.newFixedThreadPool(500);
		 ServerInfo server=new ServerInfo();
         int portnum=server.returnPORT(); //ServerInfo Ŭ������ returnPORT() �޼ҵ带 ����Ͽ� ��Ʈ�ѹ� ���� �����´�
         FileInputStream file=null;
         ObjectInputStream in=null;
        
         
         try{

  	
             file= new FileInputStream("users.txt");
             in = new ObjectInputStream(file);
             
         }catch (FileNotFoundException e) {
             // TODO: handle exception
        	 System.out.println("������ ã�� �� �����ϴ�");
         }catch(IOException e){
             
         }


            	 User u = null;
            	 User m=null;

         		try {
         			  while(true){
         			// ���Ͽ��� user�� u�� ������ȭ ����
         			u = (User)in.readObject();
         			System.out.println(u.getID()+" "+u.number);
         			members.add(u);
         			IDs.add(u.getID());
         			socketnum++;
         			//�о���ٰ� ���̻� ������ ���ٸ� EOFException ���� �߻� 
         			
         			  }
         			
         		} catch(Exception e) {
         			//���ܹ߻��ϸ� while�� Ż��
         			System.out.println("���ϳ�");
         			
         		}

            
         
         try (ServerSocket listener = new ServerSocket(portnum)) {
			//Ŭ���̾�Ʈ�� ���ο� ������ ������ ������ ���ŷ
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
				
				//���� ���������� �о����
				Weather w=new Weather();
				String w_info=w.weather();
				
				
				 String[] whr=w_info.split("/");
	
				 StringBuilder tdyweather=new StringBuilder();
				
				 for(int i=0;i<whr.length;i++) {
					 String value=whr[i].substring(whr[i].indexOf(":")+1);
					  
			     //���� ���� ����
                 switch(whr[i].substring(0,whr[i].indexOf(":"))) {
	        	 case"POP":
	        		 tdyweather.append("����Ȯ��: "+value+"%"+",");
	        		 break;
	        		 
	        	 case "T3H":
	        		 tdyweather.append("3�ð� ���: "+value+"��"+",");
	        		 break;
	        		 
	        	 case "SKY":
	        		 if(value.equals("1"))
	        			 tdyweather.append("�ϴû���: ����"+",");
	        		 else if (value.equals("3"))
	        			 tdyweather.append("�ϴû���: ��������"+",");
	        		 else if (value.equals("4"))
	        			 tdyweather.append("�ϴû���: �帲"+",");
	        		 break;
	        		 
	        	 case "PTY":
	        		 if(value.equals("0"))
	        			 tdyweather.append("��������: ����"+",");
	        		 else if (value.equals("1"))
	        			 tdyweather.append("��������: ��"+",");
	        		 else if (value.equals("2"))
	        			 tdyweather.append("��������: ��/��"+",");
	        		 else if (value.equals("3"))
	        			 tdyweather.append("��������: ��"+",");
	        		 else if (value.equals("4"))
	        			 tdyweather.append("��������: �ҳ���"+",");
	        		 else if (value.equals("5"))
	        			 tdyweather.append("��������: �����"+",");
	        		 else if (value.equals("6"))
	        			 tdyweather.append("��������: �����/������"+",");
	        		 else if (value.equals("7"))
	        			 tdyweather.append("��������: ������"+",");
	        		 break;
	        		 

	        		 
	        	 }
				 }
				
				
				

				//�̸� �ߺ����� ���� ������ ����ؼ� �̸� ��û	
				
				while (true) {
					
					out.println("SUBMITNAME");
					//Ŭ���̾�Ʈ���� �̸���û �޼���������
				
					//Ŭ���̾�Ʈ���� �̸� ���޹޴´�.
					String input = in.nextLine();	

					
					//ȸ�����Խ� ���̵� �ߺ�Ȯ��
					if (input.startsWith("[CHK]")) {
						String tid=input.substring(6);
						if(IDs.contains(tid))
							out.println("EXIST");
						else
							out.println("NONEXIST");
						continue;
						
					}
					
					//ȸ������
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
					
					//���̵� ��й�ȣȮ��
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
				
				//����ڿ���  �������� ����	
				out.println("[WEATHER] "+tdyweather.toString());
				//����ڿ��� ģ������ ����
				out.println("FRIENDS "+f.toString());
               
				//����ڿ��� ����� ���� ����
				out.println("NAMEACCEPTED " + ID+","+user_num+","+members.get(user_num).getToday()+","+members.get(user_num).getNickname());
	
				//[Broadcast]
				//��� Ŭ���̾�Ʈ�鿡�� ���ο� �����ڰ� �����ߴٴ� �޼��� ���� 
				for (PrintWriter writer : writers) {
					writer.println("[ON] " + ID+"/"+members.get(user_num).getLogin_time());
				}
				
				//writers,onlines ����Ʈ�� ���ο� ������ �߰�(Ŭ���̾�Ʈ ����)
				writers.add(out);
				onlines.add(user_num);
				
	
				//����  offline�� ����ڿ��� �޼��� ��û �Ծ��ٸ�, ���ӽ� �� ����� �˸�
					if(!members.get(user_num).requires.isEmpty()) {
						int num=members.get(user_num).requires.size();
						int[] arr=new int[num];
						
						for(int i=0;i<num;i++) {
							arr[i]=members.get(user_num).requires.get(i);
							
							out.println("[REQUIRE2] "+members.get(arr[i]).getID()+"/"+members.get(arr[i]).getNickname());
						}
					
						
					}

		
				while (true) {
					//Ŭ���̾�Ʈ���� �޼��� ���޹���
					String input = in.nextLine();
					
					
				
					
					//�޼����� "/quit" ���ڿ��� ���۽� return => Ŭ���̾�Ʈ ����
					if (input.toLowerCase().startsWith("/quit")) {
						return;
					}
					
					//������ �޼��� ��û ���������� �۾�ó��
					if (input.startsWith("[DENY]")) {
						
						String str=input.substring(7);
						
						int num=IDs.indexOf(str);
						int sender=onlines.indexOf(num);
						
						writers.get(sender).println("[SORRY] "+ID);
						
						
						 continue;
					}			
					
					//������ ä�ÿ����� �������� �۾�ó��(�� ä�ù浵 ��������)
	                 if (input.startsWith("[BYE]")) {
						
						String str=input.substring(6);
						
						String id1=str.substring(0,str.indexOf("/"));
						String id2=str.substring(str.indexOf("/")+1);
						
						int num=IDs.indexOf(id2);
						int sender=onlines.indexOf(num);
						
						writers.get(sender).println("[BYE] "+id1);
						
						
						
						 continue;
					}	
					
	                 //������ �޼��� �����û ���� ó��
					if (input.startsWith("[ACCEPT]")) {
						
						String str=input.substring(9);
						int num=IDs.indexOf(str);
						int sender=onlines.indexOf(num);
						writers.get(sender).println("[APPROVE] "+ID);
						 continue;
					}
					
					//Ŭ���̾�Ʈ�鰣�� ä�ù� ��ȭ�۾� ó��
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
					
			       
		           //ä�ÿ��� ��û ó��
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
		           
					//�� ���� ���� �۾� ó��
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
				     
				     //Ŭ���̾�Ʈ�� ģ���߰� �۾� ó��
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
		            
		            
					//����� �˻� ó��
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
						
						
						
						//���� ��ġ�ϴ� �˻� �׸��� ���ٸ�
						if(info.toString().equals(""))
							out.println("AGAIN");
						
						//��ġ�ϴ� �˻��׸��� �����Ѵٸ�
						else
						out.println("[SEARCH] "+info.toString());
						
						
						 continue;
					}
					
			
				
				
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				if (out != null) {
					//writers ����Ʈ���� Ŭ���̾�Ʈ �׸� ����
					writers.remove(out);
					
				
				}
				if (ID != null) {
					//�ܼ�â ���
					System.out.println(ID + " is leaving");
					System.out.println(user_num);
					members.get(user_num).setOnline("false");
					Date time=new Date();
					String time1=format1.format(time);
					members.get(user_num).setLogout_time(time1);
					
					//[Broadcast]
					//��� Ŭ���̾�Ʈ�鿡�� Ŭ���̾�Ʈ ���� �޼��� ����
					for (PrintWriter writer : writers) {
						
						writer.println("[OFF] " + ID+"/"+members.get(user_num).getLogout_time());
					}
					
					members.get(user_num).requires.clear();
					
					
				}
				
				//online ����Ʈ���� ����
				if(onlines.size()!=0) {
					onlines.remove(onlines.indexOf(user_num));
				}
			
				//���� ����
				try { socket.close(); } catch (IOException e) {}

         		try( ObjectOutputStream outf = new ObjectOutputStream
         				(new FileOutputStream("users.txt"))) {
         			
         			 for (User user : members) {
 						    user.setOnline("false");
 							outf.writeObject(user);
 					}
         			
         		} catch(Exception e) {
         			
         			System.out.println("����ȭ ����");
         		}

				
			}
		}
	}
}




