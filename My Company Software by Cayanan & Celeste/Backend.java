import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Backend implements ActionListener {
	JFrame frame = new JFrame();
	JLabel label1 = new JLabel("Username:"); 
	JLabel label2 = new JLabel("Password:");
	JLabel label3 = new JLabel("Welcome Back");
	JLabel label4 = new JLabel("Login to your account");
	JLabel label5 = new JLabel("New Here?");
	JLabel label6 = new JLabel("Sign up now!");
	JPanel panel = new JPanel();
	JTextField usernameField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JCheckBox showPassword = new JCheckBox("Show Password");
	JButton signUpBtn = new JButton("Sign Up");
	JButton signInBtn = new JButton("Sign In");
	private List<String> usernameList = new ArrayList<>(6);
	private List<String> passwordList = new ArrayList<>(6);
	private List<String> nameList = new ArrayList<>(6);
	String password;
	String username;
	char[] passwordChar;
	boolean hasCorrectPassword = false;
	boolean hasCorrectUsername = false;
	boolean usernameFound = false;
	boolean passwordFound = false;
	boolean hasCorrectName = false;
	boolean loginSuccessful = false;
	
	private Database database = new Database();
	Backend(Database database){
		this.database = database;
		
		signInBtn.setBackground(new Color(77, 134, 156));
		signInBtn.setForeground(new Color(238, 247, 255));
		signUpBtn.setBackground(new Color(238, 247, 255));
		signUpBtn.setForeground(new Color(77, 134, 156));
		
		showPassword.addActionListener(this);
		signUpBtn.addActionListener(this);
		signInBtn.addActionListener(this);
		passwordField.setEchoChar('*');
		
		showPassword.setFocusable(false);
		signInBtn.setFocusable(false);
		signUpBtn.setFocusable(false);
		
		usernameField.setMargin(new Insets(10, 10, 10, 10));
		passwordField.setMargin(new Insets(10, 10, 10, 10));
		
		label1.setFont(new Font("Helvetica", Font.PLAIN, 18));
		label1.setForeground(new Color(77, 134, 156));
		label2.setFont(new Font("Helvetica", Font.PLAIN, 18));
		label2.setForeground(new Color(77, 134, 156));
		label3.setFont(new Font("Helvetica", Font.BOLD, 45));
		label3.setForeground(new Color(77, 134, 156));
		label4.setFont(new Font("Helvetica", Font.PLAIN, 16));
		label4.setForeground(new Color(77, 134, 156));
		label5.setFont(new Font("Helvetica", Font.BOLD, 45));
		label5.setForeground(new Color(238, 247, 255));
		label6.setFont(new Font("Helvetica", Font.PLAIN, 16));
		label6.setForeground(new Color(238, 247, 255));
		passwordField.setFont(new Font("Helvetica", Font.PLAIN, 16));
		usernameField.setFont(new Font("Helvetica", Font.PLAIN, 16));
		signInBtn.setFont(new Font("Helvetica", Font.BOLD, 18));
		signUpBtn.setFont(new Font("Helvetica", Font.BOLD, 18));
		showPassword.setFont(new Font("Helvetica", Font.PLAIN, 14));
		showPassword.setBackground(new Color(238, 247, 255));
		showPassword.setForeground(new Color(77, 134, 156));
		
		
		label1.setBounds(100, 170, 200, 50);
		label2.setBounds(100, 230, 200, 50);
		label3.setBounds(90, 80, 400, 50);
		label4.setBounds(170, 115, 200, 50);
		label5.setBounds(530, 160, 400, 50);
		label6.setBounds(600, 195, 200, 50);
	
		passwordField.setBounds(200, 230, 200, 50);
		usernameField.setBounds(200, 170, 200, 50);
		showPassword.setBounds(200, 280, 200, 20);
		signUpBtn.setBounds(580, 250, 125, 50);
		signInBtn.setBounds(200, 310, 115, 50);

		panel.setBackground(new Color(77, 134, 156));
		panel.setBounds(500, 0, 300, 500);
		
		frame.setSize(800, 500);
		frame.setResizable(false);
		frame.setTitle("Login");
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(new Color(238, 247, 255));
		frame.add(label1);
		frame.add(label2);
		frame.add(label3);
		frame.add(label4);
		frame.add(label5);
		frame.add(label6);
		frame.add(passwordField);
		frame.add(showPassword);
		frame.add(usernameField);
		frame.add(signInBtn);
		frame.add(signUpBtn);
		frame.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(showPassword.isSelected()) passwordField.setEchoChar((char)0);
		else passwordField.setEchoChar('*');
		
		//SIGN IN BUTTON
		if(e.getSource() == signInBtn) {
			checkUsernameField();
			if(hasCorrectUsername) {
				checkPasswordField();
				hasCorrectUsername = false;
			}
			if(hasCorrectPassword) {
				EditForm editForm = new EditForm(database);
				frame.dispose();
				hasCorrectPassword = false;
			}
		}
		if(e.getSource() == signUpBtn) {
			Register register = new Register(database);
			frame.dispose();
		}

	}
	
	public void checkUsernameField() {
		username = usernameField.getText();
		usernameList = database.getUsernames();

		//IF THE USERNAME IS EMPTY
		if(username.isEmpty()) {JOptionPane.showMessageDialog(null, "Enter a valid username","ERROR", JOptionPane.ERROR_MESSAGE); return;} 
		
		//IF THE USERNAME IS SHORTER THAN 8 CHARACTERS
		if(username.length() < 8) {JOptionPane.showMessageDialog(null, "Must be atleast 8 characters","ERROR", JOptionPane.ERROR_MESSAGE);return;}
		
		//WHILE THE USERNAME IS NOT FOUND 
		while(!usernameFound) {
			for(int i = 0; i < usernameList.size(); i++) {
				if(username.equals(usernameList.get(i))) {
					hasCorrectUsername = true;
					usernameFound = true;
					break;
				}
				else {
					hasCorrectUsername = false;
				}
		}
		
		//IF THE USERNAME IS NOT FOUND 
			if(!usernameFound && username.length() >= 8) {
					JOptionPane.showMessageDialog(null, "Username does not exist.", "ERROR", JOptionPane.ERROR_MESSAGE);
					hasCorrectUsername = false;
					return;
				}
		}
		usernameFound = false; //reset to enter the while loop again
	}

	public void checkPasswordField() {
		passwordChar = passwordField.getPassword();
		password = new String(passwordChar);
		passwordList = database.getPasswords();
		
		//IF THE PASSWORD IS EMPTY
		if(password.isEmpty()) {JOptionPane.showMessageDialog(null, "Please enter your password", "ERROR", JOptionPane.ERROR_MESSAGE);return;}
		
		//IF THE PASSWORD IS SHORT
		if(password.length() < 8) {JOptionPane.showMessageDialog(null, "Password must be atleast 8 characters", "ERROR", JOptionPane.ERROR_MESSAGE);return;}
		
		//WHILE PASSWORD IS NOT FOUND 
		while(!passwordFound) {
			for(int i = 0; i < passwordList.size(); i++) {
				if(database.loginUser(username, password)) {
					database.indexer(username, password);
					hasCorrectPassword = true;
					passwordFound = true;
					break;
				}
				else {
					hasCorrectPassword = false;
				}
			}

			
			if(!passwordFound) {
				JOptionPane.showMessageDialog(null, "Incorrect Password", "ERROR", JOptionPane.ERROR_MESSAGE);
				hasCorrectPassword = false;
				return;
			}
		}
		passwordFound = false; //reset to enter the while loop again
	}
	
	public void register(List<String> usernameList, List<String> passwordList) {
		 	database.registerUser(usernameList, passwordList);
			database.displayNames(nameList);
	}
	
}

