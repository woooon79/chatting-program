package network_teamproject;




import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


//내 정보 수정 관리 클래스
//정보 수정 창 띄우기
public class MyInfo extends Frame  {
	PrintWriter out;
	User me;
 
MyInfo(PrintWriter out, User me){
	this.out=out;
	this.me=me;
	
	JFrame info=new JFrame();

	info.setSize(400, 300);
	info.setLocationRelativeTo(null);
	JPanel contentPane = new JPanel();
	//contentPane.setBorder(new EmptyBorder(2, 2, 2, 2));
	info.setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JTextField id;
	JTextField name;
	JTextField today;
	
	JLabel lblJoin = new JLabel("오늘의 한마디");
	lblJoin.setBounds(20, 40, 100, 60);
	contentPane.add(lblJoin);
	
	JLabel label = new JLabel("닉네임");
	label.setBounds(20, 100, 100, 60);
	contentPane.add(label);
	
	
	


	
	today = new JTextField();
	today.setText(me.getToday());
	today.setColumns(10);
	today.setBounds(159, 51, 186, 35);
	contentPane.add(today);
	
	
	name = new JTextField();
	name.setText(me.getNickname());
	name.setColumns(10);
	name.setBounds(159, 111, 186, 35);
	contentPane.add(name);
	
	
	JButton joinCompleteBtn = new JButton("정보수정완료");
	joinCompleteBtn.setBounds(130, 180, 139, 29);
	contentPane.add(joinCompleteBtn);
	
	info.setVisible(true);
	//회원가입완료 액션
	
    info.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
           info.setVisible(false);
        }
    });
    
    //정보수정 완료 버튼 눌렀을 때 이벤트 처리

joinCompleteBtn.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		out.println("[MYINFO] "+today.getText()+"/"+name.getText());
		
        info.setVisible(false);
	
		
	}
	

	
	
	
});

}

}
