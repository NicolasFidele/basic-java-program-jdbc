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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class editSong extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
    //declare variables for text fields, text area and buttons
	private JTextField txtTitle;
	private JTextField txtArtist;
	private JTextField txtYear;
	private JTextField txtGenre;
	private JTextField txtLength;
	private JTextArea txtLyrics;
	private static JScrollPane scrollPane;
	private JButton btnUpdate = new JButton ("Update");

	//Declare variables to be used to update database
	static String songId;
	public String title;
	public String artist;
	public String year;
	public String genre;
	public String length;
	public String lyrics;
	//Declare variables to be used to get back to myLyrics window
	public String userId;
	public String userEmail;

    //Launch Application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    editSong frame = new editSong();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //if user accesses home page without logging in, display an empty window
    public editSong() {}

    //constructor
    public editSong(String editSong) {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	try {
    		songId = editSong; //variable songId used to store the ID entered on myLyrics window
    		//variable will then be used to fetch data to be edited
    		Connection connection = (Connection) DriverManager.getConnection
    				("jdbc:mysql://localhost:3306/mylyricsdb","root", "");
    		PreparedStatement Pstatement = (PreparedStatement)connection.prepareStatement
    				("Select title, artist, year, genre, length, lyrics "
    						+ "from songs where song_id=?");
    		Pstatement.setString(1, songId);
    		//fetch result and store in variables
    		ResultSet rs = Pstatement.executeQuery();
    		while(rs.next()) {
    			//these variables store the fetched data
    			//used to display in textfields in the edit song window
        		title = rs.getString(1);
        		artist = rs.getString(2);
        		year = rs.getString(3);
        		genre = rs.getString(4);
        		length = rs.getString(5);
        		lyrics = rs.getString(6);
    		}	
    	} catch(Exception exception) {
            exception.printStackTrace();
    	}
    	//Build Frame
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(1014, 520);
    	setResizable(false);
    	setTitle("Edit song");
    	setLocation(200,50);
    	setLayout(null);
    	
    	//Display Title
        JLabel lblEdit = new JLabel("Edit records");
        lblEdit.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblEdit.setBounds(400, 10, 490, 82);
        add(lblEdit);
        
        //variables storing fetched data are used to display text in each textfield
        JLabel lblTitle;
		lblTitle = new JLabel("Title: ");
		lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblTitle.setBounds(20,100,250,30);
		add(lblTitle);
		txtTitle=new JTextField(title);
		txtTitle.setBounds(80,100,310,30);
		txtTitle.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(txtTitle);

		JLabel lblArtist;
		lblArtist = new JLabel("Artist: ");
		lblArtist.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblArtist.setBounds(20,160,250,30);
		add(lblArtist);
		txtArtist=new JTextField(artist);
		txtArtist.setBounds(80,160,310,30);
		txtArtist.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(txtArtist);
		
		JLabel lblYear;
		lblYear = new JLabel("Year: ");
		lblYear.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblYear.setBounds(20,220,250,30);
		add(lblYear);
		txtYear=new JTextField(year);
		txtYear.setBounds(80,220,310,30);
		txtYear.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(txtYear);
		
		JLabel lblGenre;
		lblGenre = new JLabel("Genre: ");
		lblGenre.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblGenre.setBounds(20,280,250,30);
		add(lblGenre);
		txtGenre=new JTextField(genre);
		txtGenre.setBounds(80,280,310,30);
		txtGenre.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(txtGenre);
		
		JLabel lblLength;
		lblLength = new JLabel("Length: ");
		lblLength.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblLength.setBounds(20,340,230,30);
		add(lblLength);
		txtLength=new JTextField(length);
		txtLength.setBounds(80,340,310,30);
		txtLength.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(txtLength);
		
		//scroll pane enables scrolling in the text area
    	scrollPane = new JScrollPane();
    	txtLyrics = new JTextArea(lyrics);
    	txtLyrics.setBounds(20,20,500,400);
    	txtLyrics.setFont(new Font("Times New Roman", Font.PLAIN, 18));
    	txtLyrics.setBorder(new LineBorder(Color.BLACK));
    	
    	scrollPane.setBounds(440,100,550,270);
    	scrollPane.getViewport().setBackground(Color.WHITE);
    	scrollPane.getViewport().add(txtLyrics);
    	add(scrollPane);
    	
        //set update button
        btnUpdate.setBounds(400,400,150,40);
        btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btnUpdate.addActionListener(this);
        add(btnUpdate);
    }
	//Action for Update Button
	public void actionPerformed(ActionEvent e) {
		//modify size of option pane dialog box message
		javax.swing.UIManager.put("OptionPane.messageFont", 
				new Font("Times New Roman", Font.PLAIN, 20));
		//user clicks on Update button
		if(e.getSource().equals(btnUpdate)){
				try {
					Connection connection = DriverManager.getConnection
							("jdbc:mysql://localhost:3306/mylyricsdb", "root", "");
					//update row in table where song_id = ID entered by user
					PreparedStatement Pstatement1=connection.prepareStatement
							("UPDATE songs SET title=?, artist=?, year=?, genre=?, length=?, lyrics=? "
									+ "WHERE song_id=?");
					//match statements to corresponding variables
					Pstatement1.setString(1,txtTitle.getText());
	                Pstatement1.setString(2,txtArtist.getText());
	                Pstatement1.setString(3,txtYear.getText());
	                Pstatement1.setString(4,txtGenre.getText());
	                Pstatement1.setString(5,txtLength.getText());
	                Pstatement1.setString(6,txtLyrics.getText());
	                Pstatement1.setString(7,songId);	
		            Pstatement1.executeUpdate();
		            JOptionPane.showMessageDialog(btnUpdate,"Song edited.");
		            //Select user_id for edited song and store in variable
		            //This user_id will be used to fetch the corresponding user email 
	                PreparedStatement Pstatement2 = (PreparedStatement)connection.prepareStatement
	                		("Select user_id from songs "
	                		+ "where song_id=?");
	                Pstatement2.setString(1, songId);
	                ResultSet rs = Pstatement2.executeQuery();
	                if(rs.next()) {
	                	userId = rs.getString(1);
	                	//Fetch email and use it to log back on to myLyrics page when update is done.
	                	//Else the myLyrics window will not open
		                PreparedStatement Pstatement3 = (PreparedStatement)connection.prepareStatement
		                		("Select email from users where user_id=?");
		                Pstatement3.setString(1, userId);
		                ResultSet rs1 = Pstatement3.executeQuery();
		                if(rs1.next()) {
		                	userEmail = rs1.getString(1);
		                	dispose();
		                	myLyrics user = new myLyrics(userEmail);
		                	user.setVisible(true);
		                }
	                }
				}catch (Exception exception) {
                    exception.printStackTrace();
                }
		}
	}   
}
