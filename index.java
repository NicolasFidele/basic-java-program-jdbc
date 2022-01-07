package myLyrics;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class index extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	//configure frame
	JFrame frame = new JFrame();
	private JButton btnLog = new JButton("Login");
	private JButton btnReg = new JButton("Register");
	//main method --> launch application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    index frame = new index();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //constructor
    public index() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	//create window
		setSize(400,400); //width and height of window
		setLocation(500,200); //location on screen
    	setResizable(false);
    	setTitle("Home");
    	getContentPane().setBackground(Color.CYAN);
		setLayout(null);
		//window contents
        JLabel lblWelcome = new JLabel("Welcome to myLyrics!");
        lblWelcome.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblWelcome.setBounds(60, 50, 350, 40);
        add(lblWelcome);
        //set login button
        btnLog.setBounds(70,150,250,40);
        btnLog.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnLog.addActionListener(this);
        add(btnLog);
        //set register button
        btnReg.setBounds(70,230,250,40);
        btnReg.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnReg.addActionListener(this);
        add(btnReg);
        
        setVisible(true);
    }
    //perform action on buttons
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnLog)){
			dispose(); //close window
			login user = new login();  //call login class
			user.setTitle("Login");  //title on header
		}
		if(e.getSource().equals(btnReg)){
			dispose();
			register user = new register();
			user.setTitle("Register");
	}
	}
}
