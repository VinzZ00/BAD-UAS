import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Cashier extends JInternalFrame {

	User user;
	dbConnection db;
	JTable cart;
	DefaultTableModel cdtm;
	JScrollPane cartpane;
	JLabel headerLabel, itemLabel, priceLabel, quantityLabel, dateLabel, pictureLabel;
	JLabel buyQuantLabel;
	JLabel totalPrice;
	JSpinner quantSpinner;
	JComboBox<String> itemComboBox, memberComboBox;
	JButton select, intocart, checkout;
	JPanel north, center, south;
	String RealId;
	int frekuensipurchase;
	
	public Cashier(User user, dbConnection db, String sessionId) {
		super("Cashier" , false, false, false);
		// TODO Auto-generated constructor stub
		this.db = db;
		this.user = user;
		
		//headerSection
		headerLabel	= new JLabel(String.format("Hello %s", user.getStaffName()));
		headerLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		
		north = new JPanel();
		north.add(headerLabel);
		
		
		//south
		south = new JPanel(new GridLayout(1,5));
		JLabel frekuensi = new JLabel(String.valueOf(frekuensipurchase));
		totalPrice = new JLabel("Rp. 0");
		JLabel pembayaran = new JLabel("amount");
		JTextField bayar = new JTextField();
		checkout = new JButton("CheckOut");
		checkout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int totalbayar = 0;
				if (!bayar.getText().isEmpty()) {
					// TODO Auto-generated method stub
					if (Integer.valueOf(bayar.getText()) >= Integer.valueOf(totalPrice.getText().substring(4))) {

						if (memberComboBox.getSelectedItem().toString().equals("None")) {
							int pembayaran = Integer.valueOf(bayar.getText());
							int total = Integer.valueOf(totalPrice.getText().substring(4));
							int kembalian = pembayaran - total;
							JOptionPane.showMessageDialog(null, "Kembalian anda adalah " + (kembalian), "Congrats",
									JOptionPane.INFORMATION_MESSAGE);
							frekuensipurchase += 1;
							frekuensi.setText(String.valueOf(frekuensipurchase));
							String PurchaseId = db.getpurcId(); 
							System.out.println(PurchaseId);
							String[] purcHead = {PurchaseId,dateLabel.getText(),"", user.getStaffId()};
							db.createpurcHeader(purcHead);
							for (int i = 0; i < cdtm.getRowCount(); i++) {
								String[] pdetail = {PurchaseId, cdtm.getValueAt(i, 0).toString(), cdtm.getValueAt(i, 1).toString()};
								String[] update = {cdtm.getValueAt(i, 0).toString(), String.valueOf((Integer.valueOf(quantityLabel.getText()) - Integer.valueOf(quantSpinner.getValue().toString())))};
								db.createpurcDetail(pdetail);
								db.updateStock(update);
								
								ResultSet rs = db.searchbyIdofbicycle(cdtm.getValueAt(i, 0).toString());
								
								try {
									if (rs.next()) {
										int hargaItem = Integer.valueOf(rs.getObject(3).toString());
										int quantity = Integer.valueOf(cdtm.getValueAt(i, 1).toString().toString());
										totalbayar += hargaItem * quantity;
										
									}
								} catch (NumberFormatException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							}
							int price = db.getinitprice(sessionId) + totalbayar;
							db.updateSession(sessionId, frekuensipurchase, price);
						} else {
							int pembayaran = Integer.valueOf(bayar.getText());
							int total = Integer.valueOf(totalPrice.getText().substring(4));
							int kembalian = pembayaran - total;
							JOptionPane.showMessageDialog(null, "Kembalian anda adalah " + (kembalian), "Congrats",
									JOptionPane.INFORMATION_MESSAGE);
							int point = total / 20000;
							String idMember = memberComboBox.getSelectedItem().toString();
							db.updatememberRoyalty(point, idMember);
							frekuensipurchase += 1;
							frekuensi.setText(String.valueOf(frekuensipurchase));
							String PurchaseId = db.getpurcId();
							String memberId = memberComboBox.getSelectedItem().toString();
							String[] purcHead = {PurchaseId,dateLabel.getText(),memberId, user.getStaffId()};
							db.createpurcHeader(purcHead);
							for (int i = 0; i < cdtm.getRowCount(); i++) {
								String[] pdetail = {PurchaseId, cdtm.getValueAt(i, 0).toString(), cdtm.getValueAt(i, 1).toString()};
								String[] update = {cdtm.getValueAt(i, 0).toString(), String.valueOf((Integer.valueOf(quantityLabel.getText()) - Integer.valueOf(quantSpinner.getValue().toString())))};
								db.createpurcDetail(pdetail);
								db.updateStock(update);
								
								ResultSet rs = db.searchbyIdofbicycle(cdtm.getValueAt(i, 0).toString());
								
								try {
									if (rs.next()) {
										int hargaItem = Integer.valueOf(rs.getObject(3).toString());
										int quantity = Integer.valueOf(cdtm.getValueAt(i, 1).toString().toString());
										totalbayar += hargaItem * quantity;
										
									}
								} catch (NumberFormatException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							int price = db.getinitprice(sessionId) + totalbayar;
							db.updateSession(sessionId, frekuensipurchase, price);
						}
					} 
				} else {
					JOptionPane.showMessageDialog(null, "Please fill all the form first", "Error", JOptionPane.ERROR_MESSAGE);
				}
				while (cdtm.getRowCount() != 0) {
					cdtm.removeRow(0);
				}
				
				itemComboBox.setSelectedIndex(0);
				itemLabel.setText("");
				priceLabel.setText("");
				quantityLabel.setText("");
				memberComboBox.setSelectedIndex(0);
				quantSpinner.setValue(0);
				totalPrice.setText("Rp. 0");
				bayar.setText("");
				pictureLabel.setIcon(null);
			}
		});
		south.add(frekuensi);
		south.add(totalPrice);
		south.add(pembayaran);
		south.add(bayar);
		south.add(checkout);
		
		//center
		initCenter();
		
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		setVisible(true);
		
	}
	
	private void initCenter() {
		// TODO Auto-generated method stub
		Vector<String> dataComb = new Vector<String>();
		dataComb.add("None");
		ResultSet rs = db.getbicycledata();
		try {
			while (rs.next()) {
				dataComb.add(rs.getObject(1).toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet initgambar = db.getbicycledata();
		try {
			while (initgambar.next()) {
				String Id = initgambar.getObject(1).toString();
				byte[] b = db.searchbyIdbicycle(Id);
				File image = new File(String.format("%s.png", Id));
				FileOutputStream fos; 
				try {
					fos= new FileOutputStream(image);
					fos.write(b);
					fos.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		itemLabel = new JLabel();
		itemLabel.setBorder(new LineBorder(Color.black, 1, true));
		priceLabel = new JLabel();
		priceLabel.setBorder(new LineBorder(Color.black, 1, true));
		quantityLabel = new JLabel();
		quantityLabel.setBorder(new LineBorder(Color.black, 1, true));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		LocalDateTime now = LocalDateTime.now();  
		dateLabel = new JLabel(dtf.format(now));
		dateLabel.setBorder(new LineBorder(Color.black, 1, true));
		pictureLabel = new JLabel();
		JScrollPane picture = new JScrollPane(pictureLabel);
		itemComboBox = new JComboBox<>(dataComb);
		select = new JButton("Select");
		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!itemComboBox.getSelectedItem().equals("None")) {
					// TODO Auto-generated method stub
					RealId = itemComboBox.getSelectedItem().toString();
					ResultSet rsb = db.searchbyIdofbicycle(RealId);
					try {
						if (rsb.next()) {
							itemLabel.setText(rsb.getObject(2).toString());
							priceLabel.setText(rsb.getObject(3).toString());
							quantityLabel.setText(rsb.getObject(4).toString());
							ImageIcon icon = new ImageIcon(String.format("%s.png", rsb.getObject(1).toString()));
							pictureLabel.setIcon(icon);
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				} else {
					itemLabel.setText("");
					priceLabel.setText("");
					quantityLabel.setText("");
					pictureLabel.setIcon(null);
				}
			}
		});
		Vector<String>dataMemb = new Vector<>();
		dataMemb.add("None");
		ResultSet memrs = db.getmember();
		try {
			while(memrs.next()) {
				dataMemb.add(memrs.getObject(1).toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		memberComboBox = new JComboBox<String>(dataMemb);
		
		center = new JPanel(new GridLayout(2,1));
		JPanel bungkus = new JPanel(new GridLayout(1,2,10,0));
		JPanel form = new JPanel(new GridLayout(7,1));
		
		JPanel combobox = new JPanel();
		combobox.add(itemComboBox);
		combobox.add(select);
		
		buyQuantLabel  = new JLabel("Quantity");
		quantSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		
		intocart = new JButton("Into Cart");
		intocart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!itemLabel.getText().equals("") && Integer.valueOf(quantSpinner.getValue().toString())> 0) {
					if (uniqueitem(RealId) == -1) {
						Vector<Object> record = new Vector<Object>();
						record.add(RealId);
						record.add(quantSpinner.getValue());
						cdtm.addRow(record);
					} else {
						int finquant = ((Integer.valueOf(cart.getValueAt(uniqueitem(RealId), 1).toString()) + Integer.valueOf(quantSpinner.getValue().toString())));
						cdtm.setValueAt(finquant, uniqueitem(RealId), 1);
					}
				}
				
				int price = 0;
				
				for (int i = 0; i < cdtm.getRowCount(); i++) {
					int temp = 0;
					
					ResultSet rs = db.searchbyIdofbicycle(cdtm.getValueAt(i, 0).toString());
					try {
						if (rs.next()) {
							temp = Integer.valueOf(rs.getObject(3).toString()) * Integer.valueOf(cdtm.getValueAt(i, 1).toString());
						}
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					price += temp;
				}
				
				totalPrice.setText(String.format("Rp. %d", price));
			}
		});
		JPanel barisakhirform = new JPanel(new GridLayout(1,2,1,0));
		JPanel quantity = new JPanel(new GridLayout(1,2,1,0));
		quantity.add(buyQuantLabel);
		quantity.add(quantSpinner);
		barisakhirform.add(quantity);
		barisakhirform.add(intocart);
		
		Vector<String> Column = new Vector<String>();
		Column.add("ItemId");
		Column.add("Quantity");
		
		Vector<Vector<Object>> record = new Vector<Vector<Object>>();
		
		cdtm = new DefaultTableModel(record, Column);
		cart = new JTable(cdtm) {
		@Override
		public boolean isCellEditable(int row, int column) {
			// TODO Auto-generated method stub
			return false;
		}
		};
		cartpane = new JScrollPane(cart);
		
		
		form.add(combobox);
		form.add(itemLabel);
		form.add(priceLabel);
		form.add(quantityLabel);
		form.add(dateLabel);
		form.add(memberComboBox);
		form.add(barisakhirform);
		
		bungkus.add(form);
		bungkus.add(picture);
		
		center.add(bungkus);
		center.add(cartpane);
		
		
	}
	
	private int uniqueitem(String Id) {
		// TODO Auto-generated method stub
		int valid = -1;
		
		for (int i = 0; i < cart.getRowCount(); i++) {
			if (cart.getValueAt(i, 0).toString().equals(Id)) {
				valid = i;
				break;
			}
		}
	
	return valid;
	}

}
