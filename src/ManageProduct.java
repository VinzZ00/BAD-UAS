import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

public class ManageProduct extends JInternalFrame implements ActionListener{

	dbConnection db;
	
	JPanel north, center, south;
	JTable bicycleTable;
	DefaultTableModel bdtm;
	JScrollPane bicycleTScrollPane;
	JLabel headerLabel, bicycleIdLabel, bicycleNameLabel, bicyclePriceLabel, bicycleQuantityLabel, bicyclePictureLabel;
	JTextField bicycleIdField, bicycleNameField, bicyclePriceField, bicycleQuantityField;
	JFileChooser pictureFileChoose;
	JButton insert, Update;
	String picturepath;
	JLabel bicyclepictureChoosedLabel;
	
	public ManageProduct(dbConnection db) {
		super("Manage Product" , false, false, false);
		this.db = db;
		
		//header Section
		headerLabel = new JLabel("Product Management");
		headerLabel.setFont(new Font("SansSerif" , Font.BOLD, 25));
		
		north = new JPanel();
		north.add(headerLabel);
		
		//centre Section
		initcentre();
		
		//south Section
		insert = new JButton("Insert");
		insert.addActionListener(this);
		Update = new JButton("Update");
		Update.addActionListener(this);
		
		south = new JPanel();
		south.add(insert);
		south.add(Update);
		
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		setVisible(true);
	}
	
	private void initcentre() {
		// TODO Auto-generated method stub
		center = new JPanel(new GridLayout(2,1,0,10));
		Vector<String> column = new Vector<String>();
		
		column.add("bicycleId");
		column.add("bicycleName");
		column.add("bicyclePrice");
		column.add("bicycleQuantity");
		column.add("bicyclePicture");
		
		ResultSet rs = db.getbicycledata();
		Vector<Vector<Object>> data = new Vector<>();
		
		try {
			while (rs.next()) {
				Vector<Object> record = new Vector<>();
				record.add(rs.getObject(1));
				record.add(rs.getObject(2));
				record.add(rs.getObject(3));
				record.add(rs.getObject(4));
				record.add("Click Here to see the picture");
				
				data.add(record);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bdtm = new DefaultTableModel(data, column);
		bicycleTable = new JTable(bdtm) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		bicycleTable.getTableHeader().setReorderingAllowed(false);
		
		bicycleTScrollPane = new JScrollPane(bicycleTable);
		
		bicycleIdLabel = new JLabel("bicycleId");
		bicycleIdField = new JTextField(db.getlatestbicycleId());
		bicycleIdField.setEditable(false);
		bicycleNameLabel = new JLabel("bicycle Name");
		bicycleNameField = new JTextField();
		bicyclePriceLabel = new JLabel("bicycle Price");
		bicyclePriceField = new JTextField();
		bicycleQuantityLabel = new JLabel("bicycle Quantity");
		bicycleQuantityField = new JTextField();
		bicyclePictureLabel = new JLabel("Choose picture");
		JButton choose = new JButton("Choose");
		bicyclepictureChoosedLabel = new JLabel("No file choosed yet");
		JPanel wrap = new JPanel(new GridLayout(1,2));
		wrap.add(choose);
		wrap.add(bicyclepictureChoosedLabel);
		pictureFileChoose = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		FileNameExtensionFilter fnef = new FileNameExtensionFilter("IMAGES", "png", "jpg", "jpeg");
		pictureFileChoose.setFileFilter(fnef);
		pictureFileChoose.removeChoosableFileFilter(pictureFileChoose.getAcceptAllFileFilter());
		choose.addActionListener(new ActionListener() {
			
				
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int r = pictureFileChoose.showSaveDialog(null);
				if (r == JFileChooser.APPROVE_OPTION) {
					bicyclepictureChoosedLabel.setText(pictureFileChoose.getSelectedFile().getAbsolutePath());
					picturepath = pictureFileChoose.getSelectedFile().getAbsolutePath();
				}
			}
		});
		
		JPanel formbungkus = new JPanel(new GridLayout(5,2,0,10));
		formbungkus.add(bicycleIdLabel);
		formbungkus.add(bicycleIdField);
		formbungkus.add(bicycleNameLabel);
		formbungkus.add(bicycleNameField);
		formbungkus.add(bicyclePriceLabel);
		formbungkus.add(bicyclePriceField);
		formbungkus.add(bicycleQuantityLabel);
		formbungkus.add(bicycleQuantityField);
		formbungkus.add(bicyclePictureLabel);
		formbungkus.add(wrap);
		
		bicycleTable.addMouseListener(new MouseListener() {
			
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
				int column = bicycleTable.getSelectedColumn();
				int row = bicycleTable.getSelectedRow();
				if (column == 4) {
						String bicycleId = (String) bdtm.getValueAt(row, 0);
						byte[] b = db.searchbyIdbicycle(bicycleId);
						File image = new File(String.format("%s.png", bicycleId));
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
						ImageIcon icon = new ImageIcon(String.format("%s.png", bicycleId));
						JOptionPane.showMessageDialog(null, icon, "picture", JOptionPane.PLAIN_MESSAGE);
						
						column = -1;
				} else {
					insert.setEnabled(false);
					Update.setEnabled(true);
					bicycleIdField.setText(bdtm.getValueAt(row, 0).toString());
					bicycleNameField.setText(bdtm.getValueAt(row, 1).toString());
					bicyclePriceField.setText(bdtm.getValueAt(row, 2).toString());
					bicycleQuantityField.setText(bdtm.getValueAt(row, 3).toString());
					bicyclepictureChoosedLabel.setText("Let me be if you don't wanna change me");
				}
			}
		});
		
		center.add(bicycleTScrollPane);
		center.add(formbungkus);
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == insert) {
			if (bicycleNameField.getText().isEmpty() || bicyclePriceField.getText().isEmpty() || bicycleQuantityField.getText().isEmpty() || bicyclepictureChoosedLabel.getText().equals("No file choosed yet")) {
				JOptionPane.showMessageDialog(this, "Please fill all the form needed first", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				bicycle b = null;
				try {
					b = new bicycle(bicycleIdField.getText(),bicycleNameField.getText(), Integer.valueOf(bicyclePriceField.getText()), Integer.valueOf(bicycleQuantityField.getText()), new FileInputStream(new File(bicyclepictureChoosedLabel.getText())), new File(bicyclepictureChoosedLabel.getText()));
				} catch (NumberFormatException | FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				db.insertbicycle(b);
			}
		} else if (e.getSource() == Update) {
			bicycle b = null;
			
			if (bicyclepictureChoosedLabel.getText().equals("Let me be if you don't wanna change me")) {
				b = new bicycle(bicycleIdField.getText(),bicycleNameField.getText(), Integer.valueOf(bicyclePriceField.getText()), Integer.valueOf(bicycleQuantityField.getText()));
				
				db.updatebicyclewithoutpict(b);
				refresh();
				insert.setEnabled(true);
				Update.setEnabled(false);
			} else {
				try {
					b = new bicycle(bicycleIdField.getText(),bicycleNameField.getText(), Integer.valueOf(bicyclePriceField.getText()), Integer.valueOf(bicycleQuantityField.getText()), new FileInputStream(new File(bicyclepictureChoosedLabel.getText())), new File(bicyclepictureChoosedLabel.getText()));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				db.updatebicycle(b);
				refresh();
				insert.setEnabled(true);
				Update.setEnabled(false);
			}
		}
	}
	
	public void refresh() {
		this.remove(center);
		initcentre();
		this.add(center, BorderLayout.CENTER);
		this.repaint();
		this.revalidate();
	}



}