import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class mainMenu {

	JPanel pembungkus, userNamePane, passwordPane;
	JLabel userNameLabel, passwordLabel;
	JTextField userNameField;
	JPasswordField passwordField;
	JCheckBox showPassword;
	JButton Login;
	JPanel showPassPane, buttPane;
	User user, user2;
	String Role;
	
	dbConnection db;
	public mainMenu(User user, dbConnection db) {
		this.db = db;
		
		// TODO Auto-generated constructor stub
		if (user.getStaffRole().equals("Supervisor")) {
			JFrame svf = new JFrame();
			
			JMenuBar svmb = new JMenuBar();
			
			JMenu correctTransaction = new JMenu("Correct Transaction");
			correctTransaction.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					svf.getContentPane().removeAll();
					svf.add(new CorrectTransaction(db));
				}
			});
			
			JMenu manageProduct = new JMenu("Manage Product");
			manageProduct.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					svf.getContentPane().removeAll();
					svf.add(new ManageProduct(db));
				}
			});
			JMenu CheckSession = new JMenu("Check Session");
			CheckSession.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					svf.getContentPane().removeAll();
					svf.add(new sessionDetail(db));
				}
			});
			JMenu logout = new JMenu("Logout");
			logout.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					new Login(db);
					svf.dispose();
				}
			});
			svmb.add(manageProduct);
			svmb.add(correctTransaction);
			svmb.add(CheckSession);
			svmb.add(logout);
			
			
			svf.setJMenuBar(svmb);
			
			svf.setEnabled(true);
			svf.setSize(700,700);
			svf.setDefaultCloseOperation(svf.EXIT_ON_CLOSE);
			svf.setLocationRelativeTo(null);
			svf.setVisible(true);
			svf.setTitle("Welcome");
		} else {
			String Id = db.getLatestSessionID();
			db.initSession(Id, user.getStaffId());
			
			JFrame osf = new JFrame();
			
			JMenuBar osmb = new JMenuBar();
			
			JMenu memberAdd = new JMenu("Add Member");
			memberAdd.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					osf.getContentPane().removeAll();
					osf.add(new Createmember(db));
				}
			});
			
			JMenu cashier = new JMenu("Cashier");
			cashier.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					osf.getContentPane().removeAll();
					osf.add(new Cashier(user, db, Id));
				}
			});
			
			JMenu logout = new JMenu("Logout");
			

			logout.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					JFrame verify = new JFrame();
					pembungkus = new JPanel(new GridLayout(4,1,0,10));
					
					pembungkus.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Please ask your supervisor to login", TitledBorder.LEFT, TitledBorder.TOP));
					
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
					
					Login.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							boolean valid = validatelog();
							if (valid) {
								Role = user2.getStaffRole();
								if (Role.equals("Supervisor")) {
									new Login(db);
									verify.dispose();
									osf.dispose();
								} else {
									JOptionPane.showMessageDialog(null, "Only Supervisor can validate your log out", "Error",
											JOptionPane.ERROR_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "the email or Password is wrong", "Error",
										JOptionPane.ERROR_MESSAGE);
								userNameField.setText("");
								passwordField.setText("");
							} 
						}
					});
					
					buttPane = new JPanel(new FlowLayout());
					
					buttPane.add(Login);
					
					pembungkus.add(userNamePane);
					pembungkus.add(passwordPane);
					pembungkus.add(showPassPane);
					pembungkus.add(buttPane);
					

					
					
					verify.add(pembungkus, BorderLayout.CENTER);
					
					verify.setEnabled(true);
					verify.setTitle("Login");
					verify.setSize(400,300);
					verify.setVisible(true);
					verify.setLocationRelativeTo(null);
				}
			});
			
			osmb.add(memberAdd);
			osmb.add(cashier);
			osmb.add(logout);
			
			osf.setJMenuBar(osmb);
			
			osf.setEnabled(true);
			osf.setSize(700,700);
			osf.setDefaultCloseOperation(osf.EXIT_ON_CLOSE);
			osf.setLocationRelativeTo(null);
			osf.setVisible(true);
			osf.setTitle("Welcome");
		}
		
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
							user2 = new User(userdata.getObject(1).toString(), userdata.getObject(2).toString(), userdata.getObject(3).toString(), userdata.getObject(4).toString(), userdata.getObject(5).toString(), userdata.getObject(6).toString(), userdata.getObject(7).toString(), userdata.getObject(8).toString());
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

}
