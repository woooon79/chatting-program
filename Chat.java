package network_teamproject;

import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.*;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

//���� Ȩȭ�鿡�� ģ�� ��� �� ������ ���콺 Ŭ�����̺�Ʈ ó�� Ŭ����

public class Chat extends Frame implements ActionListener{
    int click;
    PopupMenu pm = new PopupMenu();
    MenuItem pm_item1 = new MenuItem("������");
    MenuItem pm_item2 = new MenuItem("1:1 ä��");
    String[] names;
    String[] todays;
    String friend;
    int num;
    JFrame frame;
    JList textarea;
    PrintWriter out;
    ArrayList<User> friends;
    
    public Chat(JFrame frame,JList textarea,PrintWriter out,ArrayList<User> friends) { // �⺻������
        //super("�˾��޴� ����");
this.frame=frame;
this.textarea=textarea;
this.out=out;
this.friends=friends;
    	
    	
    	
        // �˾��޴��� �޴������� �߰�
        pm.add(pm_item1);
        pm.addSeparator(); // ���м�
        pm.add(pm_item2);
        
        pm_item1.addActionListener(this);
        pm_item2.addActionListener(this);
 
        // �˾��޴��� �����ӿ� �߰�
 
     frame.add(pm); 

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
        
      

        
        //frame.setVisible(true);
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//��Ŭ���ÿ� ��Ÿ���� �˾��޴��� ���� ����
	    

			if(e.getSource() == pm_item1) {   // ģ�� ������ â ����

				
				
				UserInfo s=new UserInfo(friends.get(click));
				
			} 
			
			else if(e.getSource() == pm_item2) {   // ä�� ��û (�������� ����)

                    out.println("[REQUIRE] "+ friends.get(click).getNumber());
			       
			
			}
		



	}
	


}

