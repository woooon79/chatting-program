package network_teamproject;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


//���� ä�ù� ���� Ŭ����
public class PersonalChat {
	
	PrintWriter out;
	String myID;
	String yourID;
    JFrame p_frame =new JFrame("Chatter2");
    JTextArea messageArea = new JTextArea(16, 50);
    JTextField textField = new JTextField(50);
    
PersonalChat(PrintWriter out,String myID,String yourID){
	this.out=out;
	this.myID=myID;
	this.yourID=yourID;
	
	  textField.setEditable(false);
      messageArea.setEditable(false);
      p_frame.getContentPane().add(textField, BorderLayout.SOUTH);
      p_frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
      p_frame.pack();
      
      
    //x �����ư
     p_frame.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e) {
             p_frame.setVisible(false);
             out.println("[BYE] "+myID+"/"+yourID);
          }
      });
      
	
      //����Ű�� �޼��� ������ ���� �޼��� ���� ���� �ؽ�Ʈ�ʵ� clear
      textField.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              out.println("[MSG] "+myID+"/"+yourID+"/"+textField.getText());
              textField.setText("");
          }
      });
}
}
