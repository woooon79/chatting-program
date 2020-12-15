package network_teamproject;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

//����� �˻� �� �������� ģ������ ����� ��ϰ�
//������ ���콺 ��ư �������� �̺�Ʈ ó��
public class Search extends Frame implements ActionListener{
    int click;
    PopupMenu pm = new PopupMenu();
    MenuItem pm_item1 = new MenuItem("������");
    MenuItem pm_item2 = new MenuItem("ģ�� �߰�");
    String[] names;
    String[] todays;
    String friend;
    PrintWriter out;
    int num;
    private static ArrayList<User> friends = new ArrayList<>();

    public Search(int num,PrintWriter out,String srchuser) { // �⺻������
        //super("�˾��޴� ����");
 this.out=out;
 this.num=num;
        // �˾��޴��� �޴������� �߰�
        pm.add(pm_item1);
        pm.addSeparator(); // ���м�
        pm.add(pm_item2);
        
        pm_item1.addActionListener(this);
        pm_item2.addActionListener(this);
 
        // �˾��޴��� �����ӿ� �߰�
 
        setSize(300, 400);
        
        String[] users=srchuser.split(",");
    
		DefaultListModel<String> listModel = new DefaultListModel<>(); 
		for(int i=0;i<users.length;i++) {//����Ʈ�� �� ����Ʈ���� �����

			String[] t_friend=users[i].split("/");
        	
        	User friend=new User();
        	friend.setNumber(Integer.parseInt(t_friend[0]));
        	friend.setID(t_friend[1]);
        	friend.setToday(t_friend[2]);
        	friend.setLogin_time(t_friend[3]);
        	friend.setLogout_time(t_friend[4]);
        	friend.setOnline(t_friend[5]);
        	friend.setNickname(t_friend[6]);
        	
        	friends.add(friend);
			
			
		  listModel.addElement(t_friend[1]);
      
		}
       
        JList textarea=new JList(listModel);
       add(new JScrollPane(textarea),"Center"); 
     add(pm); 

        //x �����ư
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               setVisible(false);
            }
        });
       
        //�˾��޴� �����ֱ� �̺�Ʈ
        textarea.addMouseListener(new MouseAdapter() {
 
        	public void mousePressed(MouseEvent e)  {check(e);}
        	public void mouseReleased(MouseEvent e) {check(e);}

        	public void check(MouseEvent e) {
        	    if (e.isPopupTrigger()) { //if the event shows the menu
        	        textarea.setSelectedIndex(textarea.locationToIndex(e.getPoint())); //select the item
        	        click=textarea.locationToIndex(e.getPoint());
        	        pm.show(textarea, e.getX(), e.getY()); //and show the menu
        	    }
        	}

        });
        
      

        
        setVisible(true);
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//��Ŭ���ÿ� ��Ÿ���� �˾��޴��� ���� ����
	    

			if(e.getSource() == pm_item1) {   // �˾��޴�
				UserInfo s=new UserInfo(friends.get(click));
			} 
			
			else if(e.getSource() == pm_item2) {   // �˾��޴�

			friend=friends.get(click).getID();
            out.println("[ADDF] "+friend+","+num);
			
			
			}
		



	}
	
	
	public String returnID() {
		return friend;
	}

}
