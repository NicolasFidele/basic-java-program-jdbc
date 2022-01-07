package myLyrics;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class login extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	//create text boxes
	private JTextField email;
	private JPasswordField password;
	//create buttons
	private JButton btnLogin = new JButton("Login");
	private JButton btnExit = new JButton("Cancel");
	
	//main method --> launch application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    login frame = new login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //constructor
    public login() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	//create window
		setSize(400,400); //width and height of window
		setLocation(500,50); //location on screen
    	setResizable(false);
    	setTitle("Login");
    	getContentPane().setBackground(Color.CYAN);
		setLayout(null);
		//window contents
        JLabel lblTitle = new JLabel("myLyrics Music Library");
        lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblTitle.setBounds(45, 40, 350, 40);
        add(lblTitle);
		
        JLabel lblLogin = new JLabel("Sign In");
        lblLogin.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblLogin.setBounds(145, 100, 100, 40);
        add(lblLogin);
        
		JLabel lblName;
		lblName = new JLabel("Email: ");
        lblName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblName.setBounds(45,140,250,25);
		add(lblName);
		email=new JTextField();
		email.setBounds(45,167,300,25);
		add(email);

		JLabel lblPassword;
		lblPassword = new JLabel("Password: "); 
        lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPassword.setBounds(45,211,250,25);
		add(lblPassword);
		password=new JPasswordField();
		password.setBounds(45,238,300,25);
		add(password);
		
        //set register button
        btnLogin.setBounds(45,292,130,35);
        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnLogin.addActionListener(this);
        add(btnLogin);
        //set cancel button
        btnExit.setBounds(212,292,130,35);
        btnExit.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnExit.addActionListener(this);
        add(btnExit);
        setVisible(true);
    }
    //perform action on buttons
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnLogin)){
			//create variable to store email
			//to be used as parameter in myLyrics constructor
			String userEmail = email.getText(); //collect user email
            try {
                Connection connection = 
                		(Connection) DriverManager.getConnection
                		("jdbc:mysql://localhost:3306/mylyricsdb","root", "");
                PreparedStatement Pstatement = 
                		(PreparedStatement)connection.prepareStatement
                		("Select email, password from users "
                		+ "where email=? and password=?");
                Pstatement.setString(1, email.getText());
                Pstatement.setString(2, password.getText());
                ResultSet rs = Pstatement.executeQuery();
                if (rs.next()) { //if query returns data
                    dispose();
                    //load home window with email as parameter
                    myLyrics user = new myLyrics(userEmail);
                    user.setVisible(true);
                } else {
                	//if email doesn't exist or password doesn't correspond.
                    JOptionPane.showMessageDialog
                    (btnLogin, "Wrong Username or Password");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
		//clicking on exit returns to index window
		if(e.getSource().equals(btnExit)){
			dispose();
			new index();
	}
	}
}
