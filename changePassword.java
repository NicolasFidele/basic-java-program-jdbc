package myLyrics;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class changePassword extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
    //declare variables
	private JButton btnChange = new JButton ("Change");
	static String email;
	public JPasswordField txtPassword;
	public String password;
	
    //Launch Application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    changePassword frame = new changePassword();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //if user accesses home page without logging in, display an empty window
    public changePassword() {}

    //constructor
    public changePassword(String userEmail) {
    	email = userEmail; //put constructor parameter into variable
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(300, 200);
    	getContentPane().setBackground(Color.LIGHT_GRAY);
    	setResizable(false);
    	setTitle("Change Password");
    	setLocation(400,250);
    	setLayout(null);
    	
        JLabel lblNew;
        lblNew = new JLabel("Enter new password: ");
        lblNew.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblNew.setBounds(60,5,250,40);
		add(lblNew);
		txtPassword=new JPasswordField();
		txtPassword.setBounds(20,50,250,40);
		txtPassword.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(txtPassword);
        //set update button
        btnChange.setBounds(85,105,120,40);
        btnChange.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnChange.addActionListener(this);
        add(btnChange);
    }
	public void actionPerformed(ActionEvent e) {
		//modify size of option pane dialog box message
		javax.swing.UIManager.put("OptionPane.messageFont", 
				new Font("Times New Roman", Font.PLAIN, 20));
		//user clicks on Update button
		if(e.getSource().equals(btnChange)) {
	    	try {
	    		//verify if password is long enough
	    		if(txtPassword.getText().length() < 7){
                	JOptionPane.showMessageDialog
                	(null,"Passwords should be more than 6 characters!");
	    		}else {
	    			//update password
		    		Connection connection = (Connection) DriverManager.getConnection
		    				("jdbc:mysql://localhost:3306/mylyricsdb","root", "");
		    		PreparedStatement Pstatement = (PreparedStatement)connection.prepareStatement
		    				("update users SET password=? where email=?");
		    		Pstatement.setString(1,txtPassword.getText());
		    		Pstatement.setString(2, email);
		    		Pstatement.executeUpdate();
		    		JOptionPane.showMessageDialog(null,"Password Changed.");
		    		dispose(); //close window
	    		}
	    	} catch(Exception exception) {
	            exception.printStackTrace();
	    	}
		}
	}   
}
