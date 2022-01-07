package myLyrics;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.proteanit.sql.DbUtils;

public class myLyrics extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	//Variables for Lyrics container
	private JTextField txtSongId;
	private JButton btnViewSong = new JButton("View");
	private JButton btnEditSong = new JButton("Edit");
	private JButton btnDeleteSong = new JButton("Delete");
	private JButton btnChangePassword = new JButton("Change Password");
	private JButton btnLogout = new JButton("Logout");
	//variables for Add Song container
	private JTextField txtTitle;
	private JTextField txtArtist;
	private JTextField txtYear;
	private JTextField txtGenre;
	private JTextField txtLength;
	private JTextArea txtLyrics;
	private static JScrollPane scrollPane2; //scroll pane in lyrics textarea
	private JButton btnAdd = new JButton ("Add Song");
	//variables for table and scroll pane in table
	private static JScrollPane scrollPane;
	static JTable table;
	
	static String email; //set to static to be used by various methods
	static String userId; //set to static to be used by various methods
	static String songId;
	public String userName; //stores user name called from database table
	//run application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    myLyrics frame = new myLyrics();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //This method will be called each time update/insert/delete is performed
    //Method to display updated JTable automatically
    public static void refreshTable() {
    	try {
    	Connection conn = (Connection) DriverManager.getConnection
    			("jdbc:mysql://localhost:3306/mylyricsdb","root", "");
		PreparedStatement ps = (PreparedStatement)conn.prepareStatement
				("Select song_id, title, artist, year, genre, length "
						+ "from songs where user_id=? order by title");
		ps.setString(1, userId);
		//fetch result and store in variable
		ResultSet resultSet = ps.executeQuery();
		//use rs2xml.jar to convert result set into a table model
		table.setModel(DbUtils.resultSetToTableModel(resultSet));
		//Change size of table columns
		TableColumnModel column = table.getColumnModel();
		column.getColumn(0).setPreferredWidth(25);
		column.getColumn(1).setPreferredWidth(180);
		column.getColumn(2).setPreferredWidth(140);
		column.getColumn(3).setPreferredWidth(40);
		column.getColumn(4).setPreferredWidth(100);
		column.getColumn(5).setPreferredWidth(50);
		//change position of text in smaller widths columns
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        //change header of columns
        table.getColumnModel().getColumn(0).setHeaderValue("ID");
        table.getColumnModel().getColumn(1).setHeaderValue("Title");
        table.getColumnModel().getColumn(2).setHeaderValue("Artist");
        table.getColumnModel().getColumn(3).setHeaderValue("Year");
        table.getColumnModel().getColumn(4).setHeaderValue("Genre");
        table.getColumnModel().getColumn(5).setHeaderValue("Length");
    	}		
    	catch(Exception exception) {
            exception.printStackTrace();
		}
    }
    //if user accesses home page without logging in, display an empty window
    public myLyrics() {}
    //home window after user logs in
    public myLyrics(String newUser) {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	try {
    		//fetch user's name from database using the email entered in login window
    		email = newUser;
    		Connection connection = (Connection) DriverManager.
    				getConnection("jdbc:mysql://localhost:3306/mylyricsdb",
                    "root", "");
    		PreparedStatement Pstatement = (PreparedStatement)connection.
    				prepareStatement("Select name from users where email=?");
    		Pstatement.setString(1, email);
    		//fetch result and store in variable
    		ResultSet rs = Pstatement.executeQuery();
    		while(rs.next()) {
        		userName = rs.getString(1); //store user's name
    		}	
    	} catch(Exception exception) {
            exception.printStackTrace();
    	}
    	setSize(1014,650); //width and height of window
		setLocation(300,50); //location on screen
    	setResizable(false);
    	setTitle("My Lyrics");
		setLayout(null);
		JLabel lblWelcome;
		lblWelcome = new JLabel("Welcome "+userName); //display user's name
		lblWelcome.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		lblWelcome.setBounds(10, 10, 500, 50);
		add(lblWelcome);
		//Display field for song id, + button view, edit, delete
		JLabel lblSongId = new JLabel("Enter Song ID: ");
		lblSongId.setBounds(20, 55, 250, 100 );
		lblSongId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblSongId);
		
		txtSongId = new JTextField();
		txtSongId.setBounds(130,90,80,30);
		txtSongId.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(txtSongId);
		
        btnViewSong.setBounds(220,88,100,35);
        btnViewSong.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnViewSong.addActionListener(this);
        add(btnViewSong);
		
        btnEditSong.setBounds(330,88,100,35);
        btnEditSong.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnEditSong.addActionListener(this);
        add(btnEditSong);
        
        btnDeleteSong.setBounds(440,88,100,35);
        btnDeleteSong.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnDeleteSong.addActionListener(this);
        add(btnDeleteSong);
		
		JPanel panel1 = new JPanel();
		panel1.setBorder(new TitledBorder(null, "Lyrics", TitledBorder.LEADING, 
				TitledBorder.TOP, new Font("times new roman",Font.BOLD,18), null));
		panel1.setBounds(10, 70, 540, 70);
		add(panel1);
		panel1.setLayout(null);
		
		//buttons to change password and log out
        btnChangePassword.setBounds(680,88,170,35);
        btnChangePassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnChangePassword.addActionListener(this);
        add(btnChangePassword);
        
        btnLogout.setBounds(870,88,100,35);
        btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnLogout.addActionListener(this);
        add(btnLogout);
		//Display fields for Adding Song
		JLabel lblTitle;
		lblTitle = new JLabel("Title: ");
		lblTitle.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblTitle.setBounds(20,175,250,30);
		add(lblTitle);
		txtTitle=new JTextField();
		txtTitle.setBounds(80,180,310,30);
		txtTitle.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(txtTitle);

		JLabel lblArtist;
		lblArtist = new JLabel("Artist: ");
		lblArtist.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblArtist.setBounds(20,225,250,30);
		add(lblArtist);
		txtArtist=new JTextField();
		txtArtist.setBounds(80,230,310,30);
		txtArtist.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(txtArtist);
		
		JLabel lblYear;
		lblYear = new JLabel("Year: ");
		lblYear.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblYear.setBounds(20,275,250,30);
		add(lblYear);
		txtYear=new JTextField();
		txtYear.setBounds(80,280,310,30);
		txtYear.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(txtYear);
		
		JLabel lblGenre;
		lblGenre = new JLabel("Genre: ");
		lblGenre.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblGenre.setBounds(20,325,250,30);
		add(lblGenre);
		txtGenre=new JTextField();
		txtGenre.setBounds(80,330,310,30);
		txtGenre.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(txtGenre);
		
		JLabel lblLength;
		lblLength = new JLabel("Length: ");
		lblLength.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblLength.setBounds(20,375,230,30);
		add(lblLength);
		txtLength=new JTextField();
		txtLength.setBounds(80,380,310,30);
		txtLength.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		add(txtLength);
		
		JLabel lblLyrics;
		lblLyrics = new JLabel("Lyrics: ");
		lblLyrics.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblLyrics.setBounds(20,425,250,30);
		add(lblLyrics);
		txtLyrics=new JTextArea();
		scrollPane2 = new JScrollPane();
		txtLyrics.setBounds(80,430,310,75);
		txtLyrics.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		txtLyrics.setBorder(new LineBorder(Color.BLACK));
    	scrollPane2.setBounds(80,430,310,105);
    	scrollPane2.getViewport().setBackground(Color.WHITE);
    	scrollPane2.getViewport().add(txtLyrics);
    	add(scrollPane2);
		
		//add(txtLyrics);
        //set Add button
        btnAdd.setBounds(175,545,110,35);
        btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnAdd.addActionListener(this);
        add(btnAdd);
		//add song panel
		JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(null, "Add Song", TitledBorder.LEADING, 
				TitledBorder.TOP, new Font("times new roman",Font.BOLD,18), null));
		panel2.setBounds(10, 150, 400, 440);
		add(panel2);
		panel2.setLayout(null);
		//Display database table
		scrollPane = new JScrollPane();
		table = new JTable();
		table.setBounds(425,155,550,415);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(Color.GRAY);
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("Times New roman", Font.BOLD, 16));
		table.setBorder(new LineBorder(Color.BLACK));
		table.setRowHeight(26);
		//create mouse event when clicking on row in table
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				try {
					int row = table.getSelectedRow(); //row selected by mouse
					//query database for song ID of selected row
					//select desired column
					String Id_=(table.getModel().getValueAt(row, 0)).toString();
					Connection connection = (Connection) DriverManager.
							getConnection("jdbc:mysql://localhost:3306/mylyricsdb","root", "");
					PreparedStatement Pstatement = (PreparedStatement)connection.
							prepareStatement("Select song_id from songs where song_id=?");
		    		Pstatement.setString(1, Id_);
					ResultSet rs = Pstatement.executeQuery();
					while(rs.next()) {
						txtSongId.setText(rs.getString("song_id"));
					}
				}
				catch(Exception exception){
					exception.printStackTrace();
				}
			}
		});
		//set scrollpane in table
	  	scrollPane.setBounds(425,169,567,418);
    	scrollPane.getViewport().setBackground(Color.WHITE);
    	scrollPane.getViewport().add(table);
    	add(scrollPane);
		JPanel panel3 = new JPanel();
		panel3.setBorder(new TitledBorder(null, "My Songs", TitledBorder.LEADING, 
				TitledBorder.TOP, new Font("times new roman",Font.BOLD,16), null));
		panel3.setBounds(420, 150, 575, 440);
		add(panel3);
		panel3.setLayout(null);
		//connect to the songs database table using user_id from users table
		try {
			//fetch user_id from users table to connect to songs table
    		Connection connection = (Connection) DriverManager.
    				getConnection("jdbc:mysql://localhost:3306/mylyricsdb","root", "");
    		PreparedStatement Pstatement = (PreparedStatement)
    				connection.prepareStatement("Select user_id from users where email=?");
    		Pstatement.setString(1, email);
    		//fetch result and store in variable
    		ResultSet rs = Pstatement.executeQuery();
    		while(rs.next()) {
        		userId = rs.getString(1); //store user_id into variable
    		}	
    		refreshTable();
		}
		catch(Exception exception) {
            exception.printStackTrace();
		}
    }
    //METHOD containing all button click events
	@Override
	public void actionPerformed(ActionEvent e) {
		//modify size of option pane dialog box message
		javax.swing.UIManager.put("OptionPane.messageFont", 
				new Font("Times New Roman", Font.PLAIN, 20));
		//Action when user clicks on Add Song Button
		if(e.getSource().equals(btnAdd)){
				try {
					Connection connection = DriverManager.getConnection
							("jdbc:mysql://localhost:3306/mylyricsdb", "root", "");
					String songTitle = txtTitle.getText();
					//check if fields have been left empty
					if(txtTitle.getText().equals("") && txtLyrics.getText().equals("")) {
						JOptionPane.showMessageDialog(null,"Title and Lyrics should not be empty!");
					} else {
						PreparedStatement Pstatement=connection.prepareStatement
								("insert into songs"
								+ "(title, artist,year,genre,length,lyrics,user_id) "
								+ "values(?,?,?,?,?,?,?)");
						//fetch text entered by user and store in database table
						Pstatement.setString(1,txtTitle.getText());
		                Pstatement.setString(2,txtArtist.getText());
		                Pstatement.setString(3,txtYear.getText());
		                Pstatement.setString(4,txtGenre.getText());
		                Pstatement.setString(5,txtLength.getText());
		                Pstatement.setString(6,txtLyrics.getText());
		                Pstatement.setString(7, userId);
		                Pstatement.executeUpdate();
		                JOptionPane.showMessageDialog(null,"Song successfully added.");
		                //clear fields
		    			txtTitle.setText("");
		    			txtArtist.setText("");
		                txtYear.setText("");
		                txtGenre.setText("");
		                txtLength.setText("");
		                txtLyrics.setText("");
		                refreshTable(); //call method to display updated table
					} 
				}catch (Exception exception) {
                    exception.printStackTrace();
                }
		}
		//Action When user clicks on View Button
		if(e.getSource().equals(btnViewSong)){
			//create variable to store song id
			String songId = txtSongId.getText(); //collect user email
            try {
                Connection connection = (Connection) DriverManager.
                		getConnection("jdbc:mysql://localhost:3306/mylyricsdb",
                    "root", "");
                PreparedStatement Pstatement = (PreparedStatement)connection.
                		prepareStatement("Select song_id from songs "
                		+ "where song_id=?");
                Pstatement.setString(1, songId);
                ResultSet rs = Pstatement.executeQuery();
                if (rs.next()) { //if query returns data
                    //create object that will store songId as parameter
                    //To be used in viewLyrics window to display corresponding Lyrics
                    viewLyrics view = new viewLyrics(songId);
                    view.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(btnEditSong, 
                    		"No song is associated with this Id!"+"\nPlease try again.");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
		//Action When user clicks on Edit button
		if(e.getSource().equals(btnEditSong)){
			String songId = txtSongId.getText();
            try {
                Connection connection = (Connection) DriverManager.getConnection
                		("jdbc:mysql://localhost:3306/mylyricsdb","root", "");
                PreparedStatement Pstatement = (PreparedStatement)connection.
                		prepareStatement("Select song_id from songs "
                		+ "where song_id=?");
                Pstatement.setString(1, songId);
                ResultSet rs = Pstatement.executeQuery();
                if (rs.next()) { //if query returns data
                    dispose();
                    //load editSong window with songId as parameter
                    //songId will then be stored in a variable to fetch data from table
                    editSong edit = new editSong(songId);
                    edit.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(btnEditSong, 
                    		"No song is associated with this Id!"+"\nPlease try again.");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
		//Action When user clicks on Delete Button
		if(e.getSource().equals(btnDeleteSong)){
			//create variable to store email
			String songId = txtSongId.getText(); //collect user email
			//variable to declare option pane confirm box
			//0 for yes, 1 for no
			int delete = JOptionPane.showConfirmDialog
					(null, "Are you sure?","Confirm Delete",JOptionPane.YES_NO_OPTION);
			if(delete==0) {
            try {
                Connection connection = (Connection) DriverManager.getConnection
                		("jdbc:mysql://localhost:3306/mylyricsdb","root", "");
                PreparedStatement Pstatement = (PreparedStatement)connection.
                		prepareStatement("Select song_id from songs "
                		+ "where song_id=?");
                Pstatement.setString(1, songId);
                ResultSet rs = Pstatement.executeQuery();
                if (rs.next()) { //if query returns data, delete, else display message
                	//fetch ID from database table
	                PreparedStatement Pstatement1 = (PreparedStatement)connection.
	                		prepareStatement("DELETE from songs "
	                		+ "where song_id=?");
	                Pstatement1.setString(1, songId);                       
	                Pstatement1.executeUpdate();
	                JOptionPane.showMessageDialog(null, "Song Deleted");
	                refreshTable();  //call method to update table
	                Pstatement1.close();
                }else {
                	//display message if ID not in database table
                    JOptionPane.showMessageDialog(null, 
                    		"No song is associated with this Id!"+"\nPlease try again.");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            }
        }
		//if user clicks on Change password
		if(e.getSource().equals(btnChangePassword)){
            try {
                Connection connection = (Connection) DriverManager.
                		getConnection("jdbc:mysql://localhost:3306/mylyricsdb",
                    "root", "");
                PreparedStatement Pstatement = (PreparedStatement)connection.
                		prepareStatement("Select password from users "
                		+ "where email=?");
                Pstatement.setString(1, email);
                ResultSet rs = Pstatement.executeQuery();
                if (rs.next()) {
                	//open change password window
                	//parameter email will be used to fetch data from table
                    changePassword change = new changePassword(email);
                    change.setVisible(true);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
		//If user clicks on Logout
		if(e.getSource().equals(btnLogout)){
            try {
            	int logout = JOptionPane.showConfirmDialog(btnLogout, "Are you sure?","Logout", JOptionPane.YES_NO_OPTION);
                if (logout == JOptionPane.YES_OPTION) {
                    dispose();
                    new index(); //return to home window
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
	}
}
 