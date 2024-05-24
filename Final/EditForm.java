import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditForm implements ActionListener{
	int index;
	String name = "User";
	String newName;
	String currentPassword;
	String oldPassword;
	String newPassword;
	JFrame frame = new JFrame();
	JLabel label3 = new JLabel("Welcome");
	JLabel label4 = new JLabel("Edit Account");
	JLabel label5 = new JLabel("Hope you are having a great day :)");
	JPanel panel = new JPanel();
	JTextField nameField = new JTextField(20);
	JTextField oldpasswordField = new JTextField();
	JTextField newpasswordField = new JTextField();
	JButton changeNameButton = new JButton("Change Name");
	JButton changePassButton = new JButton("Change Password");
	JButton logOutButton = new JButton("Log Out");
	public List<String> usernameList = new ArrayList<>();
	public List<String> passwordList = new ArrayList<>();
	public List<String> nameList = new ArrayList<>();

	private Database database = new Database();
	EditForm(Database database){
		this.database = database;

		usernameList = database.getUsernames();
		passwordList = database.getPasswords();
		nameList = database.getNames();

		index = database.index;
		System.out.println(index);

		label3.setText("Hello " + nameList.get(index) + "!");
		label3.setFont(new Font("Helvetica", Font.BOLD, 35));
		label3.setBounds(50, 50, 450, 50);
		label3.setForeground(new Color(238, 247, 255));
		
		label5.setFont(new Font("Helvetica", Font.PLAIN, 16));
		label5.setBounds(50, 85, 450, 50);
		label5.setForeground(new Color(238, 247, 255));

		label4.setFont(new Font("Helvetica", Font.BOLD, 30));
		label4.setBounds(50, 240, 200, 50);
		label4.setForeground(new Color(77, 134, 156));



		changeNameButton.setBounds(50, 300, 180, 50);
		changeNameButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
		changeNameButton.setForeground(new Color(238, 247, 255));
		changeNameButton.setBackground(new Color(77, 134, 156));
		changeNameButton.setFocusable(false);
		changeNameButton.addActionListener(this);

		changePassButton.setBounds(240, 300, 200, 50);
		changePassButton.setFont(new Font("Helvetica", Font.PLAIN, 18));
		changePassButton.setForeground(new Color(238, 247, 255));
		changePassButton.setBackground(new Color(77, 134, 156));
		changePassButton.setFocusable(false);
		changePassButton.addActionListener(this);

		logOutButton.setBounds(170, 360, 150, 50);
		logOutButton.setFont(new Font("Helvetica", Font.BOLD, 18));
		logOutButton.setForeground(new Color(238, 247, 255));
		logOutButton.setBackground(new Color(77, 134, 156));
		logOutButton.setFocusable(false);
		logOutButton.addActionListener(this);

		
		
		panel.setBackground(new Color(238, 247, 255));
		panel.setBounds(0, 220, 500, 250);

		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setTitle("Edit Form");
		frame.setVisible(true);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(new Color(77, 134, 156));
		frame.add(label3);
		frame.add(label4);
		frame.add(label5);
		frame.add(oldpasswordField);
		frame.add(newpasswordField);
		frame.add(changeNameButton);
		frame.add(changePassButton);
		frame.add(logOutButton);
		frame.add(panel);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == logOutButton) {
			frame.dispose();
			new Backend(database);
		}

		if (e.getSource() == changeNameButton) {
			Object[] message = {
					"Enter new name:", nameField
			};
					
			int option = JOptionPane.showOptionDialog(
					frame,
					message,
					"Change Name",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					new String[] { "OK", "Cancel" },
					"OK"
			);
					
			if (option == JOptionPane.OK_OPTION) {
				newName = nameField.getText();
				if (newName != null && !newName.trim().isEmpty()) {
					label3.setText("Hello " + newName + "!");
					nameList.set(index, newName);
				} else {
					JOptionPane.showMessageDialog(frame, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		if (e.getSource() == changePassButton) {
			currentPassword = passwordList.get(index);
				Object[] message = {
					"Enter old password:", oldpasswordField,
					"Enter new password:", newpasswordField
			};

			int option = JOptionPane.showOptionDialog(
					frame,
					message,
					"Change Password",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					new String[] { "OK", "Cancel" },
					"OK"
			);

			if (option == JOptionPane.OK_OPTION) {
				oldPassword = new String(oldpasswordField.getText());
				newPassword = new String(newpasswordField.getText());
				if (oldPassword.equals(currentPassword)) {
					if (newPassword != null && !newPassword.trim().isEmpty()) {
						oldPassword = newPassword;
						JOptionPane.showMessageDialog(frame, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
						passwordList.set(index, oldPassword);
						register(usernameList, passwordList, nameList);
					} else {
						JOptionPane.showMessageDialog(frame, "New password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Old password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	public void register(List<String> usernameList, List<String> passwordList, List<String> nameList) {
			database.displayNames(nameList);
		 	database.registerUser(usernameList, passwordList);
	}
}
