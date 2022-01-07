package myLyrics;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class register extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	//create text boxes
	private JTextField name;
	private JComboBox gender;
	private JTextField email;
	private JPasswordField password;
	private JPasswordField confirmPassword;
	//create buttons
	private JButton btnRegister = new JButton("Register");
	private JButton btnCancel = new JButton("Cancel");

	//main method --> launch application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    register frame = new register();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //constructor
    public register() {
    	//create window
		setSize(520,610); //width and height of window
		setLocation(500,50); //location on screen
		getContentPane().setBackground(Color.CYAN);
		setResizable(false);
		setLayout(null);
		//window contents
        JLabel lblCreate = new JLabel("Create an Account!");
        lblCreate.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblCreate.setBounds(135, 0, 520, 82);
        add(lblCreate);
        
		JLabel lblName;
		lblName = new JLabel("Enter your full name: ")	; //create object
        lblName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblName.setBounds(62,80,375,25);
		add(lblName);
		name=new JTextField();
		name.setBounds(62,107,375,25);
		add(name);
		
		JLabel lblGender;
		lblGender = new JLabel("Choose your gender: ")	; //create object
        lblGender.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblGender.setBounds(62,151,375,25);
		add(lblGender);
		String[] gen={"Male","Female"};
		gender=new JComboBox(gen);
		gender.setFont(new Font("Serif", Font.PLAIN, 18));
		gender.setBackground(Color.WHITE);
		gender.setBounds(62,178,375,25);
		add(gender);
        
		JLabel lblEmail;
		lblEmail = new JLabel("Enter your email address: ")	; //create object
        lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblEmail.setBounds(62,222,375,25);
		add(lblEmail);
		email=new JTextField();
		email.setBounds(62,249,375,25);
		add(email);
		
		JLabel lblPassword;
		lblPassword= new JLabel("Input Password: ")	; //create object
        lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPassword.setBounds(62,293,375,25);
		add(lblPassword);
		password=new JPasswordField();
		password.setBounds(62,320,375,25);
		add(password);
		
		JLabel lblConfirmPassword;
		lblConfirmPassword = new JLabel("Confirm Password: ")	; //create object
        lblConfirmPassword.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblConfirmPassword.setBounds(62,364,375,25);
		add(lblConfirmPassword);
		confirmPassword=new JPasswordField();
		confirmPassword.setBounds(62,391,375,25);
		add(confirmPassword);
        
        //set register button
        btnRegister.setBounds(62,452,150,40);
        btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnRegister.addActionListener(this);
        add(btnRegister);
        //set cancel button
        btnCancel.setBounds(285,452,150,40);
        btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnCancel.addActionListener(this);
        add(btnCancel);
        
        setVisible(true);
    }
    //perform action on buttons
	public void actionPerformed(ActionEvent e) {
		//modify size of option pane dialog box message
		javax.swing.UIManager.put("OptionPane.messageFont", 
				new Font("Times New Roman", Font.PLAIN, 20));
		if(e.getSource().equals(btnRegister)){
				try {
					Connection connection = 
							DriverManager.getConnection
							("jdbc:mysql://localhost:3306/mylyricsdb", "root", "");PreparedStatement Pstatement=connection.prepareStatement
							("insert into users(name, gender,email,password) values(?,?,?,?)");
					Pstatement.setString(1,name.getText());
	                Pstatement.setString(2,gender.getSelectedItem().toString());
	                Pstatement.setString(3,email.getText());
	                Pstatement.setString(4,password.getText());
	                //check if email already exists in table
	                PreparedStatement Pstatement2 = 
	                		(PreparedStatement)connection.prepareStatement
	                		("Select email from users "
	                		+ "where email=?");
	                Pstatement2.setString(1, email.getText());
	                ResultSet rs = Pstatement2.executeQuery();
	                if(rs.next()) {
	                		JOptionPane.showMessageDialog
	                		(null,"Email already exists!" + 
	                		"\nPlease choose another.");
	                		//check if passwords match
	                	} else if(!password.getText().equalsIgnoreCase(confirmPassword.getText())) {
	    		                JOptionPane.showMessageDialog
	    		                (null,"Passwords did not match!" 
	    		                + "\nPlease try again.");
	    		                //check if length of password is good
	    	                } else if(password.getText().length() < 7){
	    	                	JOptionPane.showMessageDialog
	    	                	(null,"Passwords should be more than 6 characters!");
	    	                } else {
	    	                	//if no errors found, execute update
			                	Pstatement.executeUpdate();
			                	JOptionPane.showMessageDialog(null,"Registration Success."+
			                	"\nUse your email and password to sign in.");
				                dispose();
				                new login();
	    	                }                
						}catch (Exception exception) {
		                    exception.printStackTrace();
		                }
		}
		//return to index page if user clicks on cancel
		if(e.getSource().equals(btnCancel)){
			dispose();
			new index();
	}
	}
}
