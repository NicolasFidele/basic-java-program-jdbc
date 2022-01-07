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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class viewLyrics extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
    //declare variables for text fields, text area and buttons
	private JTextField txtTitle;
	private JTextArea txtLyrics;
	private static JScrollPane scrollPane;
	private JButton btnBack = new JButton ("Back");

	//Declare variables to be used to display title and lyrics
	static String songId;
	public String title;
	public String lyrics;
	//Declare variables to be used to return to myLyrics window
//	public String userId;
//	public String userEmail;

    //Launch Application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    viewLyrics frame = new viewLyrics();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //display empty window if app accessed w/o login
    public viewLyrics() {}

    //constructor
    public viewLyrics(String viewSong) {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	try {
    		songId = viewSong; //variable songId used to store the ID entered on myLyrics window
    		//variable will then be used to fetch data to be edited
    		Connection connection = (Connection) DriverManager.getConnection
    				("jdbc:mysql://localhost:3306/mylyricsdb","root", "");
    		PreparedStatement Pstatement = (PreparedStatement)connection.prepareStatement
    				("Select title, lyrics from songs where song_id=?");
    		Pstatement.setString(1, songId);
    		//fetch result and store in variables
    		ResultSet rs = Pstatement.executeQuery();
    		while(rs.next()) {
    			//these variables store the fetched data
        		title = rs.getString(1);
        		lyrics = rs.getString(2);
    		}	
    	} catch(Exception exception) {
            exception.printStackTrace();
    	}
    	//Build Frame
    	//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(560, 790);
    	setResizable(false);
    	setTitle("View Lyrics");
    	setLocation(200,10);
		getContentPane().setBackground(Color.CYAN);
    	setLayout(null);
    	
        //variables storing fetched data are used to display text in each textfield
    	//display song title
        JLabel lblTitle;
		lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 36));
		lblTitle.setBounds(20,20,450,30);
		add(lblTitle);
		//display lyrics
    	scrollPane = new JScrollPane();
    	txtLyrics = new JTextArea(lyrics);
    	txtLyrics.setEditable(false);
    	txtLyrics.setBounds(20,20,520,600);
    	txtLyrics.setFont(new Font("Times New Roman", Font.PLAIN, 22));
    	txtLyrics.setBorder(new LineBorder(Color.BLACK));
    	
    	scrollPane.setBounds(20,60,520,600);
    	scrollPane.getViewport().setBackground(Color.WHITE);
    	scrollPane.getViewport().add(txtLyrics);
    	add(scrollPane);
    	
        //set update button
        btnBack.setBounds(200,680,150,40);
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnBack.addActionListener(this);
        add(btnBack);
    }
		//Action for Back Button
    	public void actionPerformed(ActionEvent e) {
    		//modify size of option pane dialog box message
    		javax.swing.UIManager.put("OptionPane.messageFont", 
    				new Font("Times New Roman", Font.PLAIN, 20));
    		//user clicks on Back button
    		if(e.getSource().equals(btnBack)){
    			dispose();
    		}
    	}   
}
