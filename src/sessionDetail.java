import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class sessionDetail extends JInternalFrame{

	JPanel north, center;
	JButton check;
	JLabel header, sessionId, staffId, transactionCount, totalCash, sessionIdc, staffIdc, transactionCountc, totalCashc;
	JComboBox<String> combbox;
	dbConnection db;
	
	public sessionDetail(dbConnection db) {
		super("Session Detail", false, false, false);
		// TODO Auto-generated constructor stub
		this.db = db;
		
		//north section
		north = new JPanel();
		north.setBorder(new EmptyBorder(40,0,0,0));
		header = new JLabel("Check Session");
		header.setFont(new Font("SansSerif", Font.BOLD, 55));
		north.add(header);
		
		//center section
		center = new JPanel(new GridLayout(5,2));
		center.setBorder(new EmptyBorder(150,10,150,10));
		ResultSet rs = db.getsession();
		
		Vector<String> sessId = new Vector<String>();
		
		try {
			while (rs.next()) {
				sessId.add(rs.getObject(1).toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		combbox = new JComboBox<String>(sessId);
		check = new JButton("Check");
		check.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ResultSet rs2 = db.searchsession(combbox.getSelectedItem().toString());
				try {
					if (rs2.next()) {
						sessionIdc.setText(rs2.getObject(1).toString());
						staffIdc.setText(rs2.getObject(2).toString());
						transactionCountc.setText(rs2.getObject(3).toString());
						totalCashc.setText(rs2.getObject(4).toString());
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		center.add(combbox);
		center.add(check);
		
		
		sessionId = new JLabel("Session Id");
		staffId = new JLabel("Staff Id");
		transactionCount = new JLabel("Transaction Count");
		totalCash = new JLabel("Total Cash");
		
		sessionIdc = new JLabel();
		staffIdc = new JLabel();
		transactionCountc = new JLabel();
		totalCashc = new JLabel();
		
		center.add(sessionId);
		center.add(sessionIdc);
		center.add(staffId);
		center.add(staffIdc);
		center.add(transactionCount);
		center.add(transactionCountc);
		center.add(totalCash);
		center.add(totalCashc);
		
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		
		setVisible(true);
				
	}

}
