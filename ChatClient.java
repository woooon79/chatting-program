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
	    	//Ŭ���̾�Ʈ �����ڷ� ip�ּҿ� ��Ʈ�ѹ��� �����Ͽ� �ʱ�ȭ
	        this.serverAddress = serverAddress;
	        this.portNumber=portNumber;
	        
	        //GUI�� ��ġ�Ͽ� Ŭ���̾�Ʈ ����
	        JPanel jp = new JPanel();

	    
	        
	        
	    	JButton searchbtn = new JButton("����� �˻�");
	    	JButton myinfo=new JButton("�� ����");
	    	//searchbtn.setBounds(206, 400, 139, 29);
	    	

	       

	      jp.add(searchField);
	      jp.add(searchbtn);
	      jp.add(myinfo);
	  
	    
	  
	    	frame.getContentPane().add(jp,"North");	        
	        frame.getContentPane().add(new JScrollPane(textarea),"Center");
	        frame.getContentPane().add(tdyweather,"South");
	        frame.pack();
	      
 
	        
	        
	        //����� �˻���ư �̺�Ʈ �ڵ鷯
	        searchbtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					out.println("[search] "+ searchField.getText());
					System.out.println("[search] "+ searchField.getText());
				
					
				}
			});
	        
	        
	      //�� ���� ��ư �̺�Ʈ �ڵ鷯
	        myinfo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					MyInfo m=new MyInfo(out,me);
				
					
				}
			});
	        
	        
	    }

   //�Է��� ���̵�� ��й�ȣ ó�� �Լ�
	private void GetIdandPw(JTextField txtId, JPasswordField txtPw) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		sID = txtId.getText();
		String pw = "";
		
		char[] secret_pw = txtPw.getPassword();
		//secret_pw �迭�� ����� ��ȣ�� �ڸ��� ��ŭ for�� �����鼭 cha �� �� ���ھ� ���� 
		for(char cha : secret_pw){ 
			Character.toString(cha); //cha �� ����� �� string���� ��ȯ
		//pw �� �����ϱ�, pw �� ���� ��������� ����, ���� ������ �̾ ����
			pw += (pw.equals("")) ? ""+cha+"" : ""+cha+""; }
		
		//��й�ȣ ��ȣȭ�۾�
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(pw.getBytes());
		String hex = String.format("%064x", new BigInteger(1, md.digest()));

		//�������� ���̵�,��й�ȣ ����
		out.println("[IDPW] "+sID+" "+hex);
		
	
	}
	
	//�α���â �۾� �Լ�
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
		
		//�α��ι�ư �̺�Ʈ �ڵ鷯
		btnNewButton_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �α��� ���� �� �۾�
				 bLoginChk = true;
				 
				 //���̵� �Ǵ� ��й�ȣ �Է� ������ ó��
				 if(txtId.getText().equals("")||txtPw.getPassword().length==0)
	               JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� �Է����ּ���");
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

		JButton btnNewButton_Sign = new JButton("ȸ�� ����");
		//ȸ������ ��ư �̺�Ʈ ó��
		btnNewButton_Sign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 		
				// ȸ������ ���� �� �۾�
				
				JoinFrame();

			}
		});
		
		btnNewButton_Sign.setBounds(320, 80, 102, 30);
		btnNewButton_Sign.setBackground(new Color(255,128,0));
		contentPane.add(btnNewButton_Sign);

		//x ��ư �Է½� â �Ⱥ��̱�
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
   
    //ȭ������â �۾� �Լ�
	public void JoinFrame() {
		


		join.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		join.setSize(430, 490);
		join.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		join.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblJoin = new JLabel("ȸ������");
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
		
		JButton joinCompleteBtn = new JButton("ȸ�����ԿϷ�");
		joinCompleteBtn.setBounds(206, 400, 139, 29);
		contentPane.add(joinCompleteBtn);
		
		join.setVisible(true);
		
		//ȸ�����ԿϷ� �׼�
		joinCompleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tfNickname.getText().equals("")||tfEmail.getText().equals("")||tfName.getText().equals("")||tfPassword.getText().equals("")||tfBirth.getText().equals("")||tfUsername.getText().equals(""))
					JOptionPane.showMessageDialog(null, "��� �׸��� �Է����ּ���");
				else {
				out.println("[CHK] "+tfUsername.getText());
				}
	
			}
		});
	}

	//����� ��������/�¶��� ���� �ݿ� ����, ģ������� model �缳��
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
    
    


	 
	 //Ŭ���̾�Ʈ �̸� �Է� GUI ����
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
        	
        	//Ŭ���̾�Ʈ ���� ����
        	Socket socket = new Socket(serverAddress, portNumber);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            
       

            while (in.hasNextLine()) {
            //�������� ���ڿ� ���� �޴´�.
                String line = in.nextLine();
                
   
               //������ ������, �α��� ȭ�� ǥ��
                if (line.startsWith("SUBMITNAME")) {
                	Login();
                	//frame.setVisible(false);
                	login.setVisible(true);
                } 
                
                
                

                //�����(�ڱ��ڽ�) ���� �������� ���� �޴´�
                else if (line.startsWith("NAMEACCEPTED")) {
                	String name=line.substring(13);
                	
                	String[] temp=name.split(",");
                	
                	me=new User();
                	
                	me.setID(temp[0]);
                	me.setNumber(Integer.parseInt(temp[1]));
                	me.setToday(temp[2]);
                	me.setNickname(temp[3]);
                	myID=temp[0];

                	
    				login.setVisible(false); // ������ �α��� ȭ�� ���ֱ�
    				
    				
    				 Chat c=new Chat(frame,textarea,out,friends);
                    
    				 frame.setTitle(myID);
                    frame.setVisible(true);

                } 
                
         
                //ä�ù� ���� (������ ��û������ ������ ��Ȱ��ȭ)
                else if (line.startsWith("[CHATTER]")) {
                    String temp=line.substring(10);
                     
                    PersonalChat c=new PersonalChat(out,myID,temp);
                    chatt.add(c);
                    c.p_frame.setTitle("Chatter - " + temp); 
                    c.p_frame.setVisible(true);
                    
                }
                
                //online���� ���� ����� ����� ���� �޾Ƽ�, ȭ���� ģ����ϴ���� model��  Ŭ�����󿡼� �����ϴ� friends ������ �ݿ�
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
                 
                 
                //ģ����� ȭ�鿡 on/off �ݿ� ���� model �缳��
                resetModel();
                    
                }
                
                //���������� ���� ���� �޾Ƽ� �Է�
                else if (line.startsWith("[WEATHER]")) {
                	
                    String temp=line.substring(10);
                    temp=temp.replaceAll(",","\n");
                    String h="���� ��õ���б� �۷ι� ķ�۽� ������?\n\n"+temp;
                   tdyweather.setText(h);
                   tdyweather.setEditable(false);
                    
                }
                
                //offline���� ���� ����� ����� ���� �޾Ƽ�, ȭ���� ģ����ϴ���� model��  Ŭ�����󿡼� �����ϴ� friends ������ �ݿ�
                //online ������ ����
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
                
                //�� ���� ���� ó��
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
                
                //�������� �޼��� ���޹����� ä��â�� ���δ�
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
                    
                    
                //�������� �߰��� ģ�� ���� �޾Ƽ� ���� Ŭ�����󿡼� �����ϴ� ģ������ friends ������  �����߰��ϰ�
                //ȭ�鿡�� �ݿ�
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
                
                //�ʱ� �α��ν� ���� ģ������ �������� ���޹޾Ƽ� ó��
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
                
                //����� �˻��� Search Ŭ���� ���� ����
                else if (line.startsWith("[SEARCH]")) {
                    //Search(line.substring(9));
                	Search s=new Search(myNum,out,line.substring(9));
                   
                }
                
                //������ �޼�����û ������ �������� ���޹޾Ƽ� ó��
                else if (line.startsWith("[SORRY]")) {
                	String id=line.substring(8);
                	
                	String m=id+"���� ��û�� �����Ͽ����ϴ�";
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
                //������ �޼��� ��û������ �������� ���� �޾Ƽ� ó��-> ä��â Ȱ��ȭ
                else if (line.startsWith("[APPROVE]")) {
                   
                	String id=line.substring(10);
                	
                	for(PersonalChat a:chatt) {
                		if(a.yourID.contentEquals(id)) {
                			JOptionPane.showMessageDialog(null, 
							 "���οϷ�");
                			a.textField.setEditable(true);
                			break;
                		}
                	}
                   
                }
                
                //������ ä�ù��� �������� ó��(���� ä�ù浵 ����)
                else if (line.startsWith("[BYE]")) {
                    
            String temp=line.substring(6);
            for(PersonalChat a:chatt) {
        		if(a.yourID.contentEquals(temp)) {
        			a.p_frame.setVisible(false);
        			break;
        		}
        	}
            
                   
                }
                
                //���濡�� �޼��� ��û�� ������ ó��(����/����)
                else if (line.startsWith("[REQUIRE2]")) {
                    String temp=line.substring(11);
                    String senderid=temp.substring(0,temp.indexOf("/"));
                    String sendernick=temp.substring(temp.indexOf("/")+1);
                    
                    temp="\'"+senderid+"("+sendernick+")\'"+" ���� �޼�����û�� �ֽ��ϴ�.\n�����Ͻðڽ��ϱ�?";
                    
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
                
                //����� �˻��� ��ġ�׸��� ������
                else if (line.startsWith("AGAIN")) {
                	JOptionPane.showMessageDialog(null, "��ġ �׸��� �����ϴ�");
                    
                }
                
           
       
                else if (line.startsWith("DISALLOW")) {
                	 // �α��� ����ó��
    					JOptionPane.showMessageDialog(null, "���̵� �Ǵ� ��й�ȣ�� Ȯ�� ��\n�ٽ� �α������ּ���.");
    				
                }
                
                //ȸ�����Խ� ���̵� �ߺ��Ǿ����� ó��
                else if (line.startsWith("EXIST")) {
                
    		
    				JOptionPane.showMessageDialog(null, "�ߺ��� ID�Դϴ�.");
    			
                }
                
                //ȸ������ �Ϸ� ó��
                else if(line.startsWith("NONEXIST")) {
		            JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�.");
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
		
		//ServerInfo�� returnIP()�޼���� returnPORT() �޼��带 ���� ������ IP �ּҰ��� ��Ʈ�ѹ� ���� �����´�.
        ServerInfo info=new ServerInfo(); 
        String ip=info.returnIP();
        int portnumber=info.returnPORT();
        
      

        ChatClient client = new ChatClient(ip,portnumber);
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //client.frame.setVisible(true);
        client.run();
    }
}


