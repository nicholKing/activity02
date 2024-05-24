import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register implements ActionListener {
	int index;
	JFrame frame = new JFrame();
	JLabel label1 = new JLabel("Username:"); 
	JLabel label2 = new JLabel("Password:");
	JLabel label3 = new JLabel("Create Account");
	JLabel label4 = new JLabel("Name:");
	JLabel label5 = new JLabel("Already have an account?");
	JPanel panel = new JPanel();
	JTextField usernameField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JTextField nameField = new JTextField();
	JCheckBox showPassword = new JCheckBox("Show Password");
	JButton signUpBtn = new JButton("Sign Up");
	JButton signInBtn = new JButton("Sign In");
	public List<String> usernameList = new ArrayList<>();
	public List<String> passwordList = new ArrayList<>();
	public List<String> nameList = new ArrayList<>();
	String name;
	String password;
	String username;
	char[] passwordChar;
	int emptyIndex = -1;
	boolean hasCorrectPassword = false;
	boolean hasCorrectUsername = false;
	boolean hasCorrectName = false;
	boolean newUsername = true;
	boolean newPassword = true;
	boolean newName = true;
	boolean usernameFound = true;
	boolean passwordFound = true;
	boolean nameFound = true;
	boolean loginSuccessful = false;
	
	private Database database = new Database();
	
	Register(Database database){
		this.database = database;
		
		usernameList.add("testusername");
		passwordList.add("testpassword");
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
		nameField.setMargin(new Insets(10, 10, 10, 10));
		
		label1.setFont(new Font("Helvetica", Font.PLAIN, 18));
		label1.setForeground(new Color(238, 247, 255));
		label2.setFont(new Font("Helvetica", Font.PLAIN, 18));
		label2.setForeground(new Color(238, 247, 255));
		label3.setFont(new Font("Helvetica", Font.BOLD, 45));
		label3.setForeground(new Color(238, 247, 255));
		label4.setFont(new Font("Helvetica", Font.PLAIN, 18));
		label4.setForeground(new Color(238, 247, 255));
		label5.setFont(new Font("Helvetica", Font.ITALIC, 12));
		label5.setForeground(new Color(238, 247, 255));

		passwordField.setFont(new Font("Helvetica", Font.PLAIN, 16));
		usernameField.setFont(new Font("Helvetica", Font.PLAIN, 16));
		nameField.setFont(new Font("Helvetica", Font.PLAIN, 16));
		signInBtn.setFont(new Font("Helvetica", Font.BOLD, 14));
		signUpBtn.setFont(new Font("Helvetica", Font.BOLD, 18));
		showPassword.setFont(new Font("Helvetica", Font.PLAIN, 14));
		showPassword.setBackground(new Color(77, 134, 156));
		showPassword.setForeground(new Color(238, 247, 255));
		
		label4.setBounds(240, 120, 200, 50);
		label1.setBounds(240, 180, 200, 50);
		label2.setBounds(240, 230, 200, 50);
		label3.setBounds(230, 50, 400, 50);
		label5.setBounds(310, 320, 150, 20);
		
		nameField.setBounds(340, 120, 200, 50);
		passwordField.setBounds(340, 240, 200, 50);
		usernameField.setBounds(340, 180, 200, 50);
		showPassword.setBounds(340, 290, 200, 20);
		signUpBtn.setBounds(310, 350, 200, 50);
		signInBtn.setBounds(453, 320, 90, 20);
		
		panel.setBackground(new Color(77, 134, 156));
		panel.setBounds(200, 30, 400, 400);

		frame.setSize(800, 500);
		frame.setResizable(false);
		frame.setTitle("Signup");
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
		frame.add(passwordField);
		frame.add(showPassword);
		frame.add(usernameField);
		frame.add(nameField);
		frame.add(signUpBtn);
		frame.add(signInBtn);
		frame.add(panel);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(showPassword.isSelected()) passwordField.setEchoChar((char)0);
		else passwordField.setEchoChar('*');
		
		if(e.getSource() == signInBtn) {
			Backend backend = new Backend(database);
			frame.dispose();
		}
		
		if(e.getSource() == signUpBtn) {
			checkUsernameField();
			if(hasCorrectUsername) {
				checkPasswordField();
				checkNameField();
			}
			if(hasCorrectPassword) {
				checkAccount();
			}
		}
		
	}
	public void checkNameField() {
		name = nameField.getText();
		nameList = database.getNames();
		findAvailableIndex(nameList);
		
		//IF THE USERNAME IS EMPTY
		if(name.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter a valid name", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		while(nameFound) {
			for(int i = 0; i < nameList.size(); i++) {
				if(name.equals(nameList.get(i))) {
					hasCorrectName = false;
					newName = false;
					nameFound = true;
					break;
				}
				else {
					nameFound = false;
					hasCorrectName = false;
					newName = true;
					hasCorrectName  = true;
				}
			}
			break;
		}
		
		//IF THE NAME IS NEW AND THERE'S AVAILABLE SLOT
		if (newName && emptyIndex != -1) {
				nameList.add(emptyIndex, name);
	            hasCorrectName = true;
		}
	}

	public void checkUsernameField() {
		username = usernameField.getText();
		usernameList = database.getUsernames();
		findAvailableIndex(usernameList);
		
		//IF THE USERNAME IS EMPTY
		if(username.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Enter a valid username", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//IF THE USERNAME IS SHORT
		if(username.length() < 8) {
			JOptionPane.showMessageDialog(null, "Must be atleast 8 characters", "ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		while(usernameFound) {
			for(int i = 0; i < usernameList.size(); i++) {
				if(username.equals(usernameList.get(i))) {
					hasCorrectUsername = false;
					newUsername = false;
					usernameFound = true;
					break;
				}
				else {
					usernameFound = false;
					hasCorrectUsername = false;
					newUsername = true;
					hasCorrectUsername  = true;
				}
			}
			break; 
		}
	
		//IF THE USERNAME IS NEW AND THERE'S AVAILABLE SLOT
		if (newUsername && emptyIndex != -1) {
	            usernameList.add(emptyIndex, username);
				nameList.add(emptyIndex, name);
	            hasCorrectUsername = true;
	    }else {
	    	usernameFound = true;
	    	newUsername = false;
	    	JOptionPane.showMessageDialog(null, "This email already exists", "ERROR", JOptionPane.ERROR_MESSAGE);
	    }
	
	}


	public void checkPasswordField() {
		passwordChar = passwordField.getPassword();
		password = new String(passwordChar);
		passwordList = database.getPasswords();
		
		//IF THE PASSWORD IS EMPTY
		if(password.length() == 0) {JOptionPane.showMessageDialog(null, "Please enter your password", "ERROR", JOptionPane.ERROR_MESSAGE);return;}
		
		//IF THE PASSWORD IS SHORT
		if(password.length() < 8) {JOptionPane.showMessageDialog(null, "Password must be atleast 8 characters", "ERROR", JOptionPane.ERROR_MESSAGE);return;}
		
		
		findAvailableIndex(passwordList);
		if(password.length() >= 8 && emptyIndex != -1) {
			passwordList.add(emptyIndex, password);
	        hasCorrectPassword = true;
		}
	}
	
	public void checkAccount() {
		for(int i = 0; i < passwordList.size(); i++) {
			if (usernameList.get(i).equals(username) && passwordList.get(i).equals(password)) {
                loginSuccessful = true;
                break;
			}
		}
		
		if(loginSuccessful) {
			register(usernameList, passwordList, nameList);
			Backend backend = new Backend(database);
			frame.dispose();
		}
	}
	
	private void findAvailableIndex(List<String> List) {
        for (int i = 0; i < List.size(); i++) {
            if (List.get(i) != null) {
                emptyIndex = i;
                break;
            }
        }
    }
	
	public void register(List<String> usernameList, List<String> passwordList, List<String> nameList) {
			database.displayNames(nameList);
		 	database.registerUser(usernameList, passwordList);
	}
}
