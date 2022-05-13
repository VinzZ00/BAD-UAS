import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Createmember extends JInternalFrame{

	dbConnection db;
	JPanel header, center, south;
	JLabel headerLabel, memberId, memberNameLabel, memberPhoneNumberLabel, memberAddressLabel;
	JTextField memberIdField, memberNameField, memberPhoneNumberField, memberAddressField;
	JButton Register;
	
	public Createmember(dbConnection db) {
		super("Add member", false, false, false);
		this.db = db;
		
		//header section
		headerLabel = new JLabel("Register Member");
		headerLabel.setFont(new Font("SansSerif",Font.BOLD, 25));
		
		header = new JPanel();
		header.setBorder(new EmptyBorder(10,0,0,0));
		header.add(headerLabel);
		
		//center section
		memberId = new JLabel("Member Id");
		memberNameLabel = new JLabel("Member Name");
		memberPhoneNumberLabel = new JLabel("Member Phone Number");
		memberAddressLabel = new JLabel("Member Address");
		
		memberIdField = new JTextField(db.getLatestMemberId());
		memberIdField.setEditable(false);
		memberNameField = new JTextField();
		memberPhoneNumberField = new JTextField();
		memberAddressField = new JTextField();
		
		center = new JPanel(new GridLayout(4,2,0,10));
		center.setBorder(new EmptyBorder(10,15,10,15));
		center.add(memberId);
		center.add(memberIdField);
		center.add(memberNameLabel);
		center.add(memberNameField);
		center.add(memberPhoneNumberLabel);
		center.add(memberPhoneNumberField);
		center.add(memberAddressLabel);
		center.add(memberAddressField);
		
		//South Section
		
		Register = new JButton("Register");
		
		Register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (memberNameField.getText().isEmpty() || memberPhoneNumberField.getText().isEmpty() || memberAddressField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "the Form can't be Empty", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					Member member = new Member(memberIdField.getText(), memberNameField.getText(), memberPhoneNumberField.getText(), memberAddressField.getText(), 0);
					db.insertMember(member);
					memberIdField.setText(db.getLatestMemberId());
					memberNameField.setText("");
					memberPhoneNumberField.setText("");
					memberAddressField.setText("");
				}
			}
		});
		south = new JPanel();
		south.setBorder(new EmptyBorder(0,0,10,0));
		south.add(Register);
		
		
		
		
		add(header, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);		
		
		setVisible(true);
	}

}
