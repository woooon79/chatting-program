package network_teamproject;

import java.awt.Color;
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

public class UserInfo extends Frame {

	User me;
 
UserInfo( User me){

	this.me=me;
	
	JFrame info=new JFrame();
	
	info.setSize(400, 300);
	info.setLocationRelativeTo(null);
	JPanel contentPane = new JPanel();
	info.setContentPane(contentPane);
	contentPane.setLayout(null);
	

	
	JLabel label1 = new JLabel("아이디: "+me.getID());
	label1.setBounds(20, 10, 300, 60);
	contentPane.add(label1);
	
	JLabel label = new JLabel("닉네임: "+me.getNickname());
	label.setBounds(20, 50, 300, 60);
	contentPane.add(label);
	
	JLabel label2 = new JLabel("오늘의 한마디: "+me.getToday());
	label2.setBounds(20, 90, 300, 60);
	contentPane.add(label2);
	
	JLabel label3 = new JLabel("로그인 시간: "+me.getLogin_time());
	label3.setBounds(20, 130, 300, 60);
	contentPane.add(label3);
	
   	JLabel label4 = new JLabel("로그아웃 시간: "+me.getLogout_time());
	label4.setBounds(20, 170, 300, 60);
	contentPane.add(label4);

	 contentPane.setBackground(Color.PINK);
	
	info.setVisible(true);
	
    info.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
           info.setVisible(false);
        }
    });

}

}
