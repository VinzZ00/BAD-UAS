import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CorrectTransaction extends JInternalFrame{

	JPanel north, center, south;
	JLabel headerLabel, headerTransactionLabel;
	JTable headerTable, detailTable;
	JScrollPane hsp, dsp;
	DefaultTableModel hdtm, ddtm;
	
	JLabel memberIdLabel, staffIdLabel, itemIdLabel, quantityLabel;
	JComboBox<String> memberIdCombobox, staffIdCombobox, itemIdcombobox;
	JSpinner quantitySpinner;
	
	JButton Correct;
	dbConnection db;
	
	String IdP;
	String tempItemId;
	
	public CorrectTransaction(dbConnection db) {
		super("Transaction Correction", false, false, false);
		// TODO Auto-generated constructor stub
		this.db = db;
		
		//north section
		north = new JPanel();
		
		headerLabel = new JLabel("Transaction Correction");
		headerLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		
		north.add(headerLabel);
		
		//Center section
		initCenter();
		
		//south section
		south = new JPanel();
		Correct = new JButton("Correct");
		Correct.setEnabled(false);
		Correct.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (itemIdcombobox.getSelectedItem().toString().equals("None")) {
					db.updateheader(memberIdCombobox.getSelectedItem().toString(), staffIdCombobox.getSelectedItem().toString(), IdP);
				} else {
					db.updateheader(memberIdCombobox.getSelectedItem().toString(), staffIdCombobox.getSelectedItem().toString(), IdP);
					db.updateDetail(itemIdcombobox.getSelectedItem().toString(), Integer.valueOf(quantitySpinner.getValue().toString()), IdP, tempItemId);
				}
				
				refresh();
			}
		});
		south.add(Correct);
		
		
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		setVisible(true);
	}

	private void initCenter() {
		// TODO Auto-generated method stub
		headerTransactionLabel = new JLabel("Header Transaction");
		headerTransactionLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		
		Vector<String> Column = new Vector<String>();
		Column.add("Purchase Id");
		Column.add("Purchase Date");
		Column.add("Member Id");
		Column.add("Staff Id");	
		
		Vector<Vector<Object>> records = new Vector<Vector<Object>>();
		
		ResultSet rs = db.getTransactionHeader();
		
		try {
			while (rs.next()) {
				Vector<Object> record = new Vector<Object>();
				record.add(rs.getObject(1));
				record.add(rs.getObject(2));
				record.add(rs.getObject(3));
				record.add(rs.getObject(4));
				
				records.add(record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hdtm = new DefaultTableModel(records, Column);
		
		headerTable = new JTable(hdtm) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		
		hsp = new JScrollPane(headerTable);
		
		Vector<String> dColumn = new Vector<String>();
		dColumn.add("PurchaseId");
		dColumn.add("Item ID");
		dColumn.add("Quantity");
		
		Vector<Vector<Object>> drecords = new Vector<Vector<Object>>(); 
		
		ddtm = new DefaultTableModel(drecords, dColumn);
		
		detailTable = new JTable(ddtm) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		dsp = new JScrollPane(detailTable);
		
//		JLabel memberIdLabel, staffIdLabel, itemIdLabel, quantityLabel;
		memberIdLabel = new JLabel("Member Id");
		staffIdLabel = new JLabel("Staff Id");
		itemIdLabel = new JLabel("Item id");
		quantityLabel = new JLabel("Quantity ");
		
//		JComboBox<String> memberIdCombobox, staffIdCombobox, itemIdcombobox;

		Vector<String> memberComb = new Vector<String>();
		memberComb.add("None");
		ResultSet rs1 = db.getmember();
		try {
			while (rs1.next()) {
				memberComb.add(rs1.getObject(1).toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		memberIdCombobox = new JComboBox<String>(memberComb);
		
		Vector<String> staffComb = new Vector<String>();
		staffComb.add("None");
		ResultSet rs2 = db.getstaffId();
		try {
			while (rs2.next()) {
				staffComb.add(rs2.getObject(1).toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		staffIdCombobox = new JComboBox<String>(staffComb);
		
		
		Vector<String> itemComb = new Vector<String>();
		itemComb.add("None");
		ResultSet rs3 = db.getbicycledata();
		try {
			while (rs3.next()) {
				itemComb.add(rs3.getObject(1).toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		itemIdcombobox = new JComboBox<String>(itemComb);
		
		quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		
		JPanel bungkusform = new JPanel(new GridLayout(4,2,0,5));
		
		bungkusform.add(memberIdLabel);
		bungkusform.add(memberIdCombobox);
		bungkusform.add(staffIdLabel);
		bungkusform.add(staffIdCombobox);
		bungkusform.add(itemIdLabel);
		bungkusform.add(itemIdcombobox);
		bungkusform.add(quantityLabel);
		bungkusform.add(quantitySpinner);
		
		center =  new JPanel(new GridLayout(3,1,0,5));
		center.setBorder(new EmptyBorder(0,10,0,10));
		center.add(hsp);
		center.add(dsp);
		center.add(bungkusform);
		
		
		headerTable.addMouseListener(new MouseListener() {
			
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
				int row = headerTable.getSelectedRow();
				String id = hdtm.getValueAt(row, 0).toString();
				IdP = id;
				while (ddtm.getRowCount() != 0) {
					ddtm.removeRow(0);
				}
				
				ResultSet detail = db.getdetailpurch(id);
				
				try {
					while (detail.next()) {
						Vector<Object> drecord = new Vector<Object>();
						drecord.add(detail.getObject(1).toString());
						drecord.add(detail.getObject(2).toString());
						drecord.add(detail.getObject(3).toString());
						ddtm.addRow(drecord);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (hdtm.getValueAt(row, 2).toString().equals("")) {
					memberIdCombobox.setSelectedIndex(0);
				} else {
					memberIdCombobox.setSelectedItem(hdtm.getValueAt(row, 2));
				}
				
				staffIdCombobox.setSelectedItem(hdtm.getValueAt(row, 3));
				itemIdcombobox.setSelectedIndex(0);
				quantitySpinner.setValue(0);
				
				Correct.setEnabled(true);
			}
		});
		
		detailTable.addMouseListener(new MouseListener() {
			
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
				int row = detailTable.getSelectedRow();
				String itemId = ddtm.getValueAt(row, 1).toString();
				tempItemId = itemId;
				int quantity = Integer.valueOf(ddtm.getValueAt(row, 2).toString());
				
				itemIdcombobox.setSelectedItem(itemId);
				quantitySpinner.setValue(quantity);
				
				Correct.setEnabled(true);
			}
		});
	}
	
	private void refresh() {
		// TODO Auto-generated method stub
		this.remove(center);
		initCenter();
		this.add(center, BorderLayout.CENTER);
		this.repaint();
		this.revalidate();
	}
	
}
