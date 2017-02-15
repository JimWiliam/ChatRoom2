package tree;

import javax.swing.ImageIcon;

public class UserInfo {

	protected ImageIcon userImage;

	protected String userName;

	protected String userSignature;
	
	public UserInfo(ImageIcon userImage, String userName, String userSignature) {
		this.userImage = userImage;
		this.userName = userName;
		this.userSignature = userSignature;
	}

	public void setUserImage(ImageIcon userImage) {
		this.userImage = userImage;
	}

	public ImageIcon getUserImage() {
		return userImage;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setuserSignature(String userSignature) {
		this.userSignature = userSignature;
	}

	public String getUserSignature() {
		return userSignature;
	}

	
}
