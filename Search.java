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

//사용자 검색 후 보여지는 친구외의 사용자 목록과
//오른쪽 마우스 버튼 눌렀을때 이벤트 처리
public class Search extends Frame implements ActionListener{
    int click;
    PopupMenu pm = new PopupMenu();
    MenuItem pm_item1 = new MenuItem("상세정보");
    MenuItem pm_item2 = new MenuItem("친구 추가");
    String[] names;
    String[] todays;
    String friend;
    PrintWriter out;
    int num;
    private static ArrayList<User> friends = new ArrayList<>();

    public Search(int num,PrintWriter out,String srchuser) { // 기본생성자
        //super("팝업메뉴 샘플");
 this.out=out;
 this.num=num;
        // 팝업메뉴에 메뉴아이템 추가
        pm.add(pm_item1);
        pm.addSeparator(); // 구분선
        pm.add(pm_item2);
        
        pm_item1.addActionListener(this);
        pm_item2.addActionListener(this);
 
        // 팝업메뉴를 프레임에 추가
 
        setSize(300, 400);
        
        String[] users=srchuser.split(",");
    
		DefaultListModel<String> listModel = new DefaultListModel<>(); 
		for(int i=0;i<users.length;i++) {//리스트에 들어갈 리스트모델을 만든다

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

        //x 종료버튼
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               setVisible(false);
            }
        });
       
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
        
      

        
        setVisible(true);
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//우클릭시에 나타나는 팝업메뉴에 대한 설정
	    

			if(e.getSource() == pm_item1) {   // 팝업메뉴
				UserInfo s=new UserInfo(friends.get(click));
			} 
			
			else if(e.getSource() == pm_item2) {   // 팝업메뉴

			friend=friends.get(click).getID();
            out.println("[ADDF] "+friend+","+num);
			
			
			}
		



	}
	
	
	public String returnID() {
		return friend;
	}

}
