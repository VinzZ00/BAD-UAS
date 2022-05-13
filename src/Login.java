import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class Login extends JFrame implements ActionListener{

	JPanel pembungkus, userNamePane, passwordPane;
	JLabel userNameLabel, passwordLabel;
	JTextField userNameField;
	JPasswordField passwordField;
	JCheckBox showPassword;
	JButton Login;
	JPanel showPassPane, buttPane;
	dbConnection db;
	User user;
	
	public Login(dbConnection db) {
		// TODO Auto-generated constructor stub
		this.db = db;
		pembungkus = new JPanel(new GridLayout(4,1,0,10));
		
		pembungkus.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Welcome To login page", TitledBorder.LEFT, TitledBorder.TOP));
		
		//UsernamePanel
		userNameLabel = new JLabel("User Name :");
		userNameField = new JTextField();
		
		userNamePane = new JPanel(new GridLayout(1,2));
		userNamePane.setBorder(new EmptyBorder(0,20,0,20));
		userNamePane.add(userNameLabel);
		userNamePane.add(userNameField);
		
		//PasswordPanel
		passwordLabel = new JLabel("Password");
		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		
		passwordPane = new JPanel(new GridLayout(1,2));
		passwordPane.setBorder(new EmptyBorder(0,20,0,20));
		passwordPane.add(passwordLabel);
		passwordPane.add(passwordField);
		
		showPassword = new JCheckBox("Show Password", false);
		showPassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (showPassword.isSelected()) {
					passwordField.setEchoChar((char)0);
				} else {
					passwordField.setEchoChar('*');
				}
			}
		});
		
		showPassPane = new JPanel(new FlowLayout());
		showPassPane.add(showPassword);
		
		Login = new JButton("Login");
		
		Login.addActionListener(this);
		
		buttPane = new JPanel(new FlowLayout());
		
		buttPane.add(Login);
		
		pembungkus.add(userNamePane);
		pembungkus.add(passwordPane);
		pembungkus.add(showPassPane);
		pembungkus.add(buttPane);
		
		
		
		
		add(pembungkus, BorderLayout.CENTER);
		
		setEnabled(true);
		setTitle("Login");
		setSize(400,300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private boolean validatelog() {
		ResultSet userdata = db.getuserdata();
		boolean valid = false;
		try {
			while (userdata.next()) {
				// TODO Auto-generated method stub
				String username = userdata.getObject(7).toString();
				String password = userdata.getObject(8).toString();
					if (userNameField.getText().equals(username)) {
						if (String.valueOf(passwordField.getPassword()).equals(password)) {	
							valid = true;
							user = new User(userdata.getObject(1).toString(), userdata.getObject(2).toString(), userdata.getObject(3).toString(), userdata.getObject(4).toString(), userdata.getObject(5).toString(), userdata.getObject(6).toString(), userdata.getObject(7).toString(), userdata.getObject(8).toString());
							break;
						} else {
							valid = false;
						}
					} else {
						valid = false;
					}
			}
		} catch (HeadlessException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valid;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Login) {
			// TODO Auto-generated method stub
			boolean valid = validatelog();
			if (valid) {
				JOptionPane.showMessageDialog(null, "Welcome In", "Congrats", JOptionPane.INFORMATION_MESSAGE);
				new mainMenu(user, db);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null, "the email or Password is wrong", "Error",
						JOptionPane.ERROR_MESSAGE);
				userNameField.setText("");
				passwordField.setText("");
			} 
		}
	}
}
