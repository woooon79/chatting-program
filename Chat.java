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

//기존 홈화면에서 친구 목록 중 오른쪽 마우스 클릭시이벤트 처리 클래스

public class Chat extends Frame implements ActionListener{
    int click;
    PopupMenu pm = new PopupMenu();
    MenuItem pm_item1 = new MenuItem("상세정보");
    MenuItem pm_item2 = new MenuItem("1:1 채팅");
    String[] names;
    String[] todays;
    String friend;
    int num;
    JFrame frame;
    JList textarea;
    PrintWriter out;
    ArrayList<User> friends;
    
    public Chat(JFrame frame,JList textarea,PrintWriter out,ArrayList<User> friends) { // 기본생성자
        //super("팝업메뉴 샘플");
this.frame=frame;
this.textarea=textarea;
this.out=out;
this.friends=friends;
    	
    	
    	
        // 팝업메뉴에 메뉴아이템 추가
        pm.add(pm_item1);
        pm.addSeparator(); // 구분선
        pm.add(pm_item2);
        
        pm_item1.addActionListener(this);
        pm_item2.addActionListener(this);
 
        // 팝업메뉴를 프레임에 추가
 
     frame.add(pm); 

        //팝업메뉴 보여주기 이벤트
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
		//우클릭시에 나타나는 팝업메뉴에 대한 설정
	    

			if(e.getSource() == pm_item1) {   // 친구 상세정보 창 띄우기

				
				
				UserInfo s=new UserInfo(friends.get(click));
				
			} 
			
			else if(e.getSource() == pm_item2) {   // 채팅 요청 (서버에게 전달)

                    out.println("[REQUIRE] "+ friends.get(click).getNumber());
			       
			
			}
		



	}
	


}

