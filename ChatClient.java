package network_teamproject;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.MenuItem;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLDocument.Iterator;

import com.sun.webkit.PopupMenu;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;
import com.sun.xml.internal.ws.api.Component;





public class ChatClient 
{
	User me;
    String myID;
    String myName;
    String myToday;
    String serverAddress;
    int portNumber;
    int myNum;
    
    String pop;
    String sky;
    String t3h;
    String tmn;
    String tmx;
    
    Scanner in;
    PrintWriter out;
    boolean bLoginChk;
    boolean joinChk=false;
    private static ArrayList<User> friends = new ArrayList<>();
    private static ArrayList<PersonalChat> chatt = new ArrayList<>();
    DefaultListModel<String> model= new DefaultListModel<>(); ;
    

    JFrame frame = new JFrame();


 
	JTextField searchField= new JTextField(50);
    JTextArea tdyweather = new JTextArea(7, 50);
    JList textarea=new JList(model);
	private JTextField tfUsername;
	private JTextField tfPassword;
	private JTextField tfName;
	private JTextField tfEmail;
	private JTextField tfNickname;
	private JTextField tfBirth;

    JFrame login=new JFrame();
    JFrame join=new JFrame();
    JFrame srch=new JFrame();
    
	JPanel contentPane;
	JTextField txtId;

	JPasswordField txtPw;

	String sID = "";
	String sPW = "";

    
	
	 public ChatClient(String serverAddress, int portNumber) {
	    	//클라이언트 생성자로 ip주소와 포트넘버를 전달하여 초기화
	        this.serverAddress = serverAddress;
	        this.portNumber=portNumber;
	        
	        //GUI를 배치하여 클라이언트 구성
	        JPanel jp = new JPanel();

	    
	        
	        
	    	JButton searchbtn = new JButton("사용자 검색");
	    	JButton myinfo=new JButton("내 정보");
	    	//searchbtn.setBounds(206, 400, 139, 29);
	    	

	       

	      jp.add(searchField);
	      jp.add(searchbtn);
	      jp.add(myinfo);
	  
	    
	  
	    	frame.getContentPane().add(jp,"North");	        
	        frame.getContentPane().add(new JScrollPane(textarea),"Center");
	        frame.getContentPane().add(tdyweather,"South");
	        frame.pack();
	      
 
	        
	        
	        //사용자 검색버튼 이벤트 핸들러
	        searchbtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					out.println("[search] "+ searchField.getText());
					System.out.println("[search] "+ searchField.getText());
				
					
				}
			});
	        
	        
	      //내 정보 버튼 이벤트 핸들러
	        myinfo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					MyInfo m=new MyInfo(out,me);
				
					
				}
			});
	        
	        
	    }

   //입력한 아이디와 비밀번호 처리 함수
	private void GetIdandPw(JTextField txtId, JPasswordField txtPw) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		sID = txtId.getText();
		String pw = "";
		
		char[] secret_pw = txtPw.getPassword();
		//secret_pw 배열에 저장된 암호의 자릿수 만큼 for문 돌리면서 cha 에 한 글자씩 저장 
		for(char cha : secret_pw){ 
			Character.toString(cha); //cha 에 저장된 값 string으로 변환
		//pw 에 저장하기, pw 에 값이 비어있으면 저장, 값이 있으면 이어서 저장
			pw += (pw.equals("")) ? ""+cha+"" : ""+cha+""; }
		
		//비밀번호 암호화작업
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(pw.getBytes());
		String hex = String.format("%064x", new BigInteger(1, md.digest()));

		//서버에게 아이디,비밀번호 전송
		out.println("[IDPW] "+sID+" "+hex);
		
	
	}
	
	//로그인창 작업 함수
	public void Login() {

		login.setTitle("Login page");
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setBounds(50, 100, 480, 164);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		login.setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId = new JLabel("ID");
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		lblId.setBounds(50, 30, 60, 15);
		contentPane.add(lblId);

		txtId = new JTextField();
		txtId.setText("");
		// sID = txtId.getText();
		txtId.setBounds(120, 27, 167, 21);
		contentPane.add(txtId);
		txtId.setColumns(10);

		JLabel lblPw = new JLabel("Password");
		lblPw.setHorizontalAlignment(SwingConstants.CENTER);
		lblPw.setBounds(50, 55, 60, 15);
		contentPane.add(lblPw);

		txtPw = new JPasswordField();
		txtPw.setText("");
		// sPW = txtId.getText();
		txtPw.setBounds(120, 52, 167, 21);
		txtPw.setEchoChar('*');
		contentPane.add(txtPw);
		txtPw.setColumns(10);

		JButton btnNewButton_Login = new JButton("Login");
		
		//로그인버튼 이벤트 핸들러
		btnNewButton_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 로그인 했을 때 작업
				 bLoginChk = true;
				 
				 //아이디 또는 비밀번호 입력 없을시 처리
				 if(txtId.getText().equals("")||txtPw.getPassword().length==0)
	               JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력해주세요");
				else
					try {
						GetIdandPw(txtId, txtPw);
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	
			}
		});
		btnNewButton_Login.setBounds(300, 27, 102, 46);
		contentPane.add(btnNewButton_Login);

		JButton btnNewButton_Sign = new JButton("회원 가입");
		//회원가입 버튼 이벤트 처리
		btnNewButton_Sign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 		
				// 회원가입 했을 때 작업
				
				JoinFrame();

			}
		});
		
		btnNewButton_Sign.setBounds(320, 80, 102, 30);
		btnNewButton_Sign.setBackground(new Color(255,128,0));
		contentPane.add(btnNewButton_Sign);

		//x 버튼 입력시 창 안보이기
		   login.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent e) {
	               login.setVisible(false);
	            }
	        });
	       
	}


    /**
     * Constructs the client by laying out the GUI and registering a listener with the
     * textfield so that pressing Return in the listener sends the textfield contents
     * to the server. Note however that the textfield is initially NOT editable, and
     * only becomes editable AFTER the client receives the NAMEACCEPTED message from
     * the server.
     */
   
    //화원가입창 작업 함수
	public void JoinFrame() {
		


		join.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		join.setSize(430, 490);
		join.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		join.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblJoin = new JLabel("회원가입");
		lblJoin.setBounds(159, 41, 101, 20);
		contentPane.add(lblJoin);
		
		JLabel label = new JLabel("ID");
		label.setBounds(69, 113, 69, 20);
		contentPane.add(label);
		
		
		JLabel lblUsername = new JLabel("Password");
		lblUsername.setBounds(69, 163, 69, 20);
		contentPane.add(lblUsername);
		

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(69, 210, 69, 20);
		contentPane.add(lblName);
		
		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(69, 257, 69, 20);
		contentPane.add(lblEmail);
		
		JLabel lblNickname = new JLabel("Nickname");
		lblNickname.setBounds(69, 304, 69, 20);
		contentPane.add(lblNickname);
		
		JLabel lblBirth = new JLabel("Birthday");
		lblBirth.setBounds(69, 351, 69, 20);
		contentPane.add(lblBirth);
		
		tfUsername = new JTextField();
		tfUsername.setText("");
		tfUsername.setColumns(10);
		tfUsername.setBounds(159, 106, 186, 35);
		contentPane.add(tfUsername);
		
		tfPassword = new JTextField();
		tfPassword.setText("");
		tfPassword.setColumns(10);
		tfPassword.setBounds(159, 156, 186, 35);
		contentPane.add(tfPassword);
		
		tfName = new JTextField();
		tfName.setText("");
		tfName.setColumns(10);
		tfName.setBounds(159, 203, 186, 35);
		contentPane.add(tfName);
		
		tfEmail = new JTextField();
		tfEmail.setText("");
		tfEmail.setColumns(10);
		tfEmail.setBounds(159, 250, 186, 35);
		contentPane.add(tfEmail);
		
		tfNickname = new JTextField();
		tfNickname.setText("");
		tfNickname.setColumns(10);
		tfNickname.setBounds(159, 297, 186, 35);
		contentPane.add(tfNickname);
		
		tfBirth = new JTextField();
		tfBirth.setText("");
		tfBirth.setColumns(10);
		tfBirth.setBounds(159, 344, 186, 35);
		contentPane.add(tfBirth);
		
		JButton joinCompleteBtn = new JButton("회원가입완료");
		joinCompleteBtn.setBounds(206, 400, 139, 29);
		contentPane.add(joinCompleteBtn);
		
		join.setVisible(true);
		
		//회원가입완료 액션
		joinCompleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfNickname.getText().equals("")||tfEmail.getText().equals("")||tfName.getText().equals("")||tfPassword.getText().equals("")||tfBirth.getText().equals("")||tfUsername.getText().equals(""))
					JOptionPane.showMessageDialog(null, "모든 항목을 입력해주세요");
				else {
				out.println("[CHK] "+tfUsername.getText());
				}
	
			}
		});
	}

	//사용자 오프라인/온라인 변경 반영 위해, 친구목록의 model 재설정
    public void resetModel() {
    	  for(User user:friends) {
          	String id=user.getID();
          	String online=user.getOnline();
          	String chk;
          	if(online.contentEquals("true"))
          		chk="[ON] ";
          	else
          		chk="[OFF] ";
          	
          	chk=chk.concat(id);
          	
          	model.addElement(chk);
          	
          }
          
          textarea.setModel(model);
    }
    
    


	 
	 //클라이언트 이름 입력 GUI 구현
    private String getName() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE
        );
    }

    
    private void run() throws IOException, NoSuchAlgorithmException {
        try {
        	
        	//클라이언트 소켓 생성
        	Socket socket = new Socket(serverAddress, portNumber);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            
       

            while (in.hasNextLine()) {
            //서버에게 문자열 전달 받는다.
                String line = in.nextLine();
                
   
               //서버와 연결후, 로그인 화면 표시
                if (line.startsWith("SUBMITNAME")) {
                	Login();
                	//frame.setVisible(false);
                	login.setVisible(true);
                } 
                
                
                

                //사용자(자기자신) 정보 서버에게 전달 받는다
                else if (line.startsWith("NAMEACCEPTED")) {
                	String name=line.substring(13);
                	
                	String[] temp=name.split(",");
                	
                	me=new User();
                	
                	me.setID(temp[0]);
                	me.setNumber(Integer.parseInt(temp[1]));
                	me.setToday(temp[2]);
                	me.setNickname(temp[3]);
                	myID=temp[0];

                	
    				login.setVisible(false); // 기존의 로그인 화면 꺼주기
    				
    				
    				 Chat c=new Chat(frame,textarea,out,friends);
                    
    				 frame.setTitle(myID);
                    frame.setVisible(true);

                } 
                
         
                //채팅방 생성 (상대방이 요청승인할 때까지 비활성화)
                else if (line.startsWith("[CHATTER]")) {
                    String temp=line.substring(10);
                     
                    PersonalChat c=new PersonalChat(out,myID,temp);
                    chatt.add(c);
                    c.p_frame.setTitle("Chatter - " + temp); 
                    c.p_frame.setVisible(true);
                    
                }
                
                //online으로 상태 변경된 사용자 정보 받아서, 화면의 친구목록대상인 model과  클래스상에서 관리하는 friends 변수에 반영
                else if (line.startsWith("[ON]")) {
                    String temp=line.substring(5,line.indexOf("/"));
                    String time=line.substring(line.indexOf("/")+1);
                    for(User user:friends) {
                    	if(user.getID().equals(temp)) {
                    		user.setOnline("true");
                    		user.setLogin_time(time);
                    	}
                    }
                    
                 model.removeAllElements();
                 
                 
                //친구목록 화면에 on/off 반영 위해 model 재설정
                resetModel();
                    
                }
                
                //공공데이터 날씨 정보 받아서 입력
                else if (line.startsWith("[WEATHER]")) {
                	
                    String temp=line.substring(10);
                    temp=temp.replaceAll(",","\n");
                    String h="오늘 가천대학교 글로벌 캠퍼스 날씨는?\n\n"+temp;
                   tdyweather.setText(h);
                   tdyweather.setEditable(false);
                    
                }
                
                //offline으로 상태 변경된 사용자 정보 받아서, 화면의 친구목록대상인 model과  클래스상에서 관리하는 friends 변수에 반영
                //online 설정과 유사
                else if (line.startsWith("[OFF]")) {
                    String temp=line.substring(6);
                    String id=temp.substring(0,temp.indexOf("/"));
                    String time=temp.substring(temp.indexOf("/")+1);
                    
                    for(User user:friends) {
                    	if(user.getID().equals(id))
                    		user.setOnline("false");
                    	    user.setLogout_time(time);
                    }
                    
                 model.removeAllElements();
                 
                 
                
                resetModel();
                    
                }
                
                //내 정보 변경 처리
                else if (line.startsWith("[CINFO]")) {
                    String temp=line.substring(8);
                    String[] t=temp.split(",");
                    int n=Integer.parseInt(t[0]);
                    		
                    if(me.getNumber()==n) {
                    	me.setNickname(t[1]);
                		me.setToday(t[2]);
                		continue;
                    }
                    
                    for(User user:friends) {
                    	if(user.getNumber()==n) {
                    		user.setNickname(t[1]);
                    		user.setToday(t[2]);
                    		
                    	}
                    		
                    }

                    }
                
                //서버에게 메세지 전달받은후 채팅창에 보인다
                else if (line.startsWith("[MSG]")) {
                    String temp=line.substring(6);
                    String[] t=temp.split(",");
                    String msg=t[0];
                    String id=t[1];
                    
                    for(PersonalChat a:chatt) {
                    	if(a.yourID.equals(id))
                    	    a.messageArea.append(msg+"\n");
                    }
                    

                    }
                    
                    
                //서버에게 추가한 친구 정보 받아서 내가 클래스상에서 관리하는 친구정보 friends 변수에  정보추가하고
                //화면에도 반영
                else if (line.startsWith("[FNEW]")) {
                  String temp=line.substring(7);
                  String[] t_friend=temp.split("/");
              	
              	User friend=new User();
             	friend.setNumber(Integer.parseInt(t_friend[0]));
            	friend.setID(t_friend[1]);
            	friend.setToday(t_friend[2]);
            	friend.setLogin_time(t_friend[3]);
            	friend.setLogout_time(t_friend[4]);
            	friend.setOnline(t_friend[5]);
            	friend.setNickname(t_friend[6]);
              	
              	friends.add(friend);
                  
              	String id=friend.getID();
            	String online=friend.getOnline();
            	String chk;
            	if(online.contentEquals("true"))
            		chk="[ON] ";
            	else
            		chk="[OFF] ";
            	
            	chk=chk.concat(id);
            	
            	model.addElement(chk);

            textarea.setModel(model);
                  
                  
                }
                
                //초기 로그인시 기존 친구정보 서버에게 전달받아서 처리
                else if (line.startsWith("FRIENDS")) {
                	
                	
                	
                    String temp=line.substring(8);
                    
                    if(temp.equals(""))
                    	continue;
                    
                    String[] t_friends=temp.split(",");
                    for(int i=0;i<t_friends.length;i++) {
                    	String[] t_friend=t_friends[i].split("/");
                    	
                    	User friend=new User();
                    	friend.setNumber(Integer.parseInt(t_friend[0]));
                    	friend.setID(t_friend[1]);
                    	friend.setToday(t_friend[2]);
                    	friend.setLogin_time(t_friend[3]);
                    	friend.setLogout_time(t_friend[4]);
                    	friend.setOnline(t_friend[5]);
                    	friend.setNickname(t_friend[6]);
                    	
                    	friends.add(friend);
                    	
                    }
                    
                    
                    
                    for(User user:friends) {
                    	String id=user.getID();
                    	String online=user.getOnline();
                    	String chk;
                    	if(online.contentEquals("true"))
                    		chk="[ON] ";
                    	else
                    		chk="[OFF] ";
                    	
                    	chk=chk.concat(id);
                    	
                    	model.addElement(chk);
                    	
                    }
                    
                    textarea.setModel(model);
                    
                    
                }
                
                //사용자 검색시 Search 클래스 변수 생성
                else if (line.startsWith("[SEARCH]")) {
                    //Search(line.substring(9));
                	Search s=new Search(myNum,out,line.substring(9));
                   
                }
                
                //상대방의 메세지요청 거절을 서버에게 전달받아서 처리
                else if (line.startsWith("[SORRY]")) {
                	String id=line.substring(8);
                	
                	String m=id+"님이 요청을 거절하였습니다";
                	for(PersonalChat a:chatt) {
                		if(a.yourID.contentEquals(id)) {
                			
                			a.p_frame.setVisible(false);
                			JOptionPane.showMessageDialog(null, 
									m, "Message", 
									JOptionPane.ERROR_MESSAGE);
                			
                			break;
                		}
                	}
                   
                }  
                //상대방의 메세지 요청승인을 서버에게 전달 받아서 처리-> 채팅창 활성화
                else if (line.startsWith("[APPROVE]")) {
                   
                	String id=line.substring(10);
                	
                	for(PersonalChat a:chatt) {
                		if(a.yourID.contentEquals(id)) {
                			JOptionPane.showMessageDialog(null, 
							 "승인완료");
                			a.textField.setEditable(true);
                			break;
                		}
                	}
                   
                }
                
                //상대방이 채팅방을 나갔을떄 처리(나의 채팅방도 종료)
                else if (line.startsWith("[BYE]")) {
                    
            String temp=line.substring(6);
            for(PersonalChat a:chatt) {
        		if(a.yourID.contentEquals(temp)) {
        			a.p_frame.setVisible(false);
        			break;
        		}
        	}
            
                   
                }
                
                //상대방에게 메세지 요청이 왔을때 처리(거절/수락)
                else if (line.startsWith("[REQUIRE2]")) {
                    String temp=line.substring(11);
                    String senderid=temp.substring(0,temp.indexOf("/"));
                    String sendernick=temp.substring(temp.indexOf("/")+1);
                    
                    temp="\'"+senderid+"("+sendernick+")\'"+" 님의 메세지요청이 있습니다.\n수락하시겠습니까?";
                    
                    int result = JOptionPane.showConfirmDialog(null, 
							temp, "Confirm", 
							JOptionPane.YES_NO_OPTION);
                    
                    
			if(result == JOptionPane.CLOSED_OPTION) {
			  out.println("[DENY] "+senderid);
			  
			}
			else if(result == JOptionPane.YES_OPTION) {
				out.println("[ACCEPT] "+senderid);
				  PersonalChat c=new PersonalChat(out,myID,senderid);
                  chatt.add(c);
                  c.p_frame.setTitle("Chatter - " + senderid); 
                  c.p_frame.setVisible(true);
                  c.textField.setEditable(true);
			}
			else
				out.println("[DENY] "+senderid);
                    
                }
                
                //사용자 검색시 일치항목이 없을때
                else if (line.startsWith("AGAIN")) {
                	JOptionPane.showMessageDialog(null, "일치 항목이 없습니다");
                    
                }
                
           
       
                else if (line.startsWith("DISALLOW")) {
                	 // 로그인 실패처리
    					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 확인 후\n다시 로그인해주세요.");
    				
                }
                
                //회원가입시 아이디가 중복되었을때 처리
                else if (line.startsWith("EXIST")) {
                
    		
    				JOptionPane.showMessageDialog(null, "중복된 ID입니다.");
    			
                }
                
                //회원가입 완료 처리
                else if(line.startsWith("NONEXIST")) {
		            JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
		            MessageDigest md = MessageDigest.getInstance("SHA-256");
		            md.update(tfPassword.getText().getBytes());
		            String new_hex = String.format("%064x", new BigInteger(1, md.digest()));
		            
    				out.println("[NEW] "+tfUsername.getText()+" "+new_hex+" "+tfName.getText()+" "+tfEmail.getText()+" "+tfNickname.getText()+" "+tfBirth.getText());
    				join.dispose();
                }
               
                
         
                
            }
        } finally {
            frame.setVisible(false);
            frame.dispose();
        }
    }

    public static void main(String[] args) throws Exception {
		
		//ServerInfo의 returnIP()메서드와 returnPORT() 메서드를 통해 서버의 IP 주소값과 포트넘버 값을 가져온다.
        ServerInfo info=new ServerInfo(); 
        String ip=info.returnIP();
        int portnumber=info.returnPORT();
        
      

        ChatClient client = new ChatClient(ip,portnumber);
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //client.frame.setVisible(true);
        client.run();
    }
}


