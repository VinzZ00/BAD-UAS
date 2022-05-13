import java.io.File;
import java.io.FileInputStream;

public class bicycle {

	private String bicycleId, bicycleName;
	private int bicyclePrice, bicycleQuantity;
	private FileInputStream bicyclePicture;
	private File bicyclePictureFile;
	
	

	public bicycle(String bicycleId, String bicycleName, int bicyclePrice, int bicycleQuantity,
			FileInputStream bicyclePicture, File bicyclePictureFile) {
		super();
		this.bicycleId = bicycleId;
		this.bicycleName = bicycleName;
		this.bicyclePrice = bicyclePrice;
		this.bicycleQuantity = bicycleQuantity;
		this.bicyclePicture = bicyclePicture;
		this.bicyclePictureFile = bicyclePictureFile;
	}
	
	public bicycle(String bicycleId, String bicycleName, int bicyclePrice, int bicycleQuantity) {
		super();
		this.bicycleId = bicycleId;
		this.bicycleName = bicycleName;
		this.bicyclePrice = bicyclePrice;
		this.bicycleQuantity = bicycleQuantity;
	}

	public String getBicycleId() {
		return bicycleId;
	}

	public void setBicycleId(String bicycleId) {
		this.bicycleId = bicycleId;
	}

	public String getBicycleName() {
		return bicycleName;
	}

	public void setBicycleName(String bicycleName) {
		this.bicycleName = bicycleName;
	}

	public int getBicyclePrice() {
		return bicyclePrice;
	}

	public void setBicyclePrice(int bicyclePrice) {
		this.bicyclePrice = bicyclePrice;
	}

	public int getBicycleQuantity() {
		return bicycleQuantity;
	}

	public void setBicycleQuantity(int bicycleQuantity) {
		this.bicycleQuantity = bicycleQuantity;
	}

	public FileInputStream getBicyclePicture() {
		return bicyclePicture;
	}

	public void setBicyclePicture(FileInputStream bicyclePicture) {
		this.bicyclePicture = bicyclePicture;
	}

	public File getBicyclePictureFile() {
		return bicyclePictureFile;
	}

	public void setBicyclePictureFile(File bicyclePictureFile) {
		this.bicyclePictureFile = bicyclePictureFile;
	}
	
	
	

}
