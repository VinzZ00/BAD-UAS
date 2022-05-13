
public class Member {

	private String memberId, memberName, memberPhoneNumber, memberAddress;
	private int royaltyPoint;
	
	public Member(String memberId, String memberName, String memberPhoneNumber, String memberAddress,
			int royaltyPoint) {
		super();
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberPhoneNumber = memberPhoneNumber;
		this.memberAddress = memberAddress;
		this.royaltyPoint = royaltyPoint;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPhoneNumber() {
		return memberPhoneNumber;
	}

	public void setMemberPhoneNumber(String memberPhoneNumber) {
		this.memberPhoneNumber = memberPhoneNumber;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public int getRoyaltyPoint() {
		return royaltyPoint;
	}

	public void setRoyaltyPoint(int royaltyPoint) {
		this.royaltyPoint = royaltyPoint;
	}
	
}
