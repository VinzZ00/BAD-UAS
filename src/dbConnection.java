import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import java.sql.Blob;

public class dbConnection {

	Connection connect;
	ResultSet rs;
	PreparedStatement ps;
	
	
	public dbConnection() {
		// TODO Auto-generated constructor stub
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/uasbad", "root", "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ResultSet getuserdata()  {
		try {
			ps = connect.prepareStatement("select * from staff");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public String getLatestMemberId() {
		String ID = null;
		try {
			ps = connect.prepareStatement("Select * from member");
			rs = ps.executeQuery();
					
			if (!rs.next()) {
				ID = "ME001";
			} else {
				rs.last();
				String temp = rs.getObject(1).toString();
				temp = temp.substring(2,5);
				int number = Integer.valueOf(temp)+1;
				ID = "ME" + String.format("%03d",number);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ID;
	}

	
	public void insertMember(Member member) {
		try {
			ps = connect.prepareStatement("INSERT INTO `member`(`memberId`, `memberName`, `memberPhoneNumber`, `memberAddress`, `royaltyPoint`) VALUES (?,?,?,?,?)");
			ps.setString(1, member.getMemberId());
			ps.setString(2, member.getMemberName());
			ps.setString(3, member.getMemberPhoneNumber());
			ps.setString(4, member.getMemberAddress());
			ps.setInt(5, member.getRoyaltyPoint());
			ps.execute();
			JOptionPane.showMessageDialog(null, "The member has been inserted Successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet getbicycledata() {
		try {
			ps = connect.prepareStatement("Select * from bicycle");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public byte[] searchbyIdbicycle(String Id) {
		try {
			ps = connect.prepareStatement("SELECT * FROM `bicycle` WHERE bicycleId = ? ");
			ps.setString(1, Id);
			rs = ps.executeQuery(); 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return getbicycleblob(rs);
		
	}
	public ResultSet searchbyIdofbicycle(String Id) {
		try {
			ps = connect.prepareStatement("SELECT * FROM `bicycle` WHERE bicycleId = ? ");
			ps.setString(1, Id);
			rs = ps.executeQuery(); 
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rs;
		
	}
	
	public byte[] getbicycleblob(ResultSet rs) {
		byte[] b = null;
		Blob blob = null;
		try {
			while (rs.next()) {
				blob = rs.getBlob(5);
				b = blob.getBytes(1,(int)blob.length());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	public String getlatestbicycleId() {
		String Id = null;
		try {
			ps = connect.prepareStatement("Select * From bicycle");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if (rs.next()) {
				rs.last();
				int temp = Integer.valueOf(rs.getObject(1).toString().substring(2)) + 1;
				Id = String.format("BI%03d", temp);
			} else {
				Id = "BI001";
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Id;
	}
	
	public void insertbicycle(bicycle b) {
		try {
			ps = connect.prepareStatement("INSERT INTO `bicycle`(`bicycleId`, `bicycleName`, `bicyclePrice`, `bicyclequantity`, `picture`) VALUES (?,?,?,?,?)");
			ps.setString(1, b.getBicycleId());
			ps.setString(2, b.getBicycleName());
			ps.setInt(3, b.getBicyclePrice());
			ps.setInt(4, b.getBicycleQuantity());
			ps.setBinaryStream(5, b.getBicyclePicture(), b.getBicyclePictureFile().length());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "Success adding new bicycle");
	}
	
	public void updatebicyclewithoutpict(bicycle b) {
		try {
			ps = connect.prepareStatement("UPDATE `bicycle` SET `bicycleName`=?,`bicyclePrice`=?,`bicyclequantity`= ? WHERE bicycleId = ?");
			ps.setString(1, b.getBicycleName());
			ps.setInt(2, b.getBicyclePrice());
			ps.setInt(3, b.getBicycleQuantity());
			ps.setString(4, b.getBicycleId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updatebicycle(bicycle b) {
		try {
			ps = connect.prepareStatement("UPDATE `bicycle` SET `bicycleName`=?,`bicyclePrice`=?,`bicyclequantity`=?,`picture`=? WHERE bicycleId = ?");
			ps.setString(1, b.getBicycleName());
			ps.setInt(2, b.getBicyclePrice());
			ps.setInt(3, b.getBicycleQuantity());
			ps.setBinaryStream(4, b.getBicyclePicture(), b.getBicyclePictureFile().length());
			ps.setString(5, b.getBicycleId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public ResultSet getmember() {
		try {
			ps = connect.prepareStatement("Select * from member");
			rs = ps.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public void updatememberRoyalty(int royaltiPoint, String Id) {
		int royaltypointawal = 0;
		try {
			ps = connect.prepareStatement("SELECT `royaltyPoint` FROM `member` WHERE memberId = ?");
			ps.setString(1, Id);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				royaltypointawal = Integer.valueOf(rs.getObject(1).toString());
			}
			
			int finalroyalti = royaltypointawal + royaltiPoint;
			
			ps = connect.prepareStatement("UPDATE `member` SET `royaltyPoint`=? WHERE memberId = ?");
			ps.setInt(1, finalroyalti);
			ps.setString(2, Id);
			ps.execute();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getpurcId() {
		String ID = null;
		try {
			ps = connect.prepareStatement("Select * from purchaseheader");
			rs = ps.executeQuery();
			
			if (rs.next()) {
				rs.last();
				int temp = Integer.valueOf(rs.getObject(1).toString().substring(2))+1;
				ID = String.format("PU%03d", temp);
				
			} else {
				ID = "PU001";
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ID;
	}
	
	public void createpurcHeader(String[] header) {
		try {
			ps = connect.prepareStatement("INSERT INTO `purchaseheader`(`purchaseId`, `purchasDate`, `memberId`, `staffId`) VALUES (?,?,?,?)");
			ps.setString(1, header[0]);
			ps.setString(2, header[1]);
			ps.setString(3, header[2]);
			ps.setString(4, header[3]);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createpurcDetail(String[] detail) {
		try {
			ps = connect.prepareStatement("INSERT INTO `purchasedetail`(`purchaseId`, `itemId`, `quantity`) VALUES (?,?,?)");
			ps.setString(1, detail[0]);
			ps.setString(2, detail[1]);
			ps.setString(3, detail[2]);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateStock(String[] update) {
		try {
			ps = connect.prepareStatement("UPDATE `bicycle` SET `bicyclequantity`=? WHERE bicycleId = ?");
			ps.setString(1, update[1]);
			ps.setString(2, update[0]);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet getTransactionHeader() {
		
		try {
			ps = connect.prepareStatement("Select * from purchaseheader");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public ResultSet getstaffId() {
		try {
			ps = connect.prepareStatement("Select * from staff");
			rs = ps.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rs;
	}
	
	public ResultSet getdetailpurch(String Id) {
		try {
			ps = connect.prepareStatement("SELECT * FROM `purchasedetail` WHERE purchaseId = ?");
			ps.setString(1, Id);
			rs = ps.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rs;
	}
	
	public void updateheader(String memberId, String Staffid, String PurchaseId) {
		try {
			ps = connect.prepareStatement("UPDATE `purchaseheader` SET `memberId`=?,`staffId`=? WHERE purchaseId = ?");
			ps.setString(1, memberId);
			ps.setString(2, Staffid);
			ps.setString(3, PurchaseId);
			
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateDetail(String ItemId, int Quantity, String purchaseId, String lateItemId) {
		try {
			ps = connect.prepareStatement("UPDATE `purchasedetail` SET `itemId`=?,`quantity`=? WHERE purchaseId = ? and itemId = ?");
			ps.setString(1, ItemId);
			ps.setInt(2, Quantity);
			ps.setString(3, purchaseId);
			ps.setString(4, lateItemId);
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public String getLatestSessionID () {
		String Id = null;
		try {
			ps = connect.prepareStatement("SELECT * FROM `session`");
			rs = ps.executeQuery();
			
			if (rs.next()) {
				rs.last();
				int Temp = Integer.valueOf(rs.getObject(1).toString().substring(2)) + 1;
				Id = String.format("SE%03d", Temp);
			} else {
				Id = "SE001";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return Id;
	}
	
	public void initSession(String sessionId,String staffID) {
		try {
			ps = connect.prepareStatement("INSERT INTO `session`(`SessionId`, `StaffId`, `totalTransactionCount`, `totalCash`) VALUES (?,?,?,?)");
			ps.setString(1, sessionId);
			ps.setString(2, staffID);
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public Integer getinitprice(String sessionId) {
		int price = 0;
		try {
			ps = connect.prepareStatement("SELECT `totalCash` FROM `session` WHERE SessionId = ?");
			rs = ps.executeQuery();
			
			if (rs.next()) {
				rs.last();
				price = Integer.valueOf(rs.getObject(1).toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return price;
	}
	
	public void updateSession(String SessionId, int totaltransaction, int totalcash) {
		try {
			ps = connect.prepareStatement("UPDATE `session` SET `totalTransactionCount`=?,`totalCash`=? WHERE sessionId = ?");
			ps.setInt(1, totaltransaction);
			ps.setInt(2, totalcash);
			ps.setString(3, SessionId);
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public ResultSet getsession() {
		try {
			ps = connect.prepareStatement("SELECT * FROM `session`");
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet searchsession(String id) {
		try {
			ps = connect.prepareStatement("SELECT * FROM `session` where SessionId = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}
