package net.users.db;

public class USERS {
	private int USER_KEY;
	private String USER_ID;
	private String USER_NAME;
	private String USER_EMAIL;
	private String USER_PASSWORD;
	private String USER_AGERANGE;
	private String USER_PROFILEPHOTO;
	private int USER_STATUS;
	private int RNUM;
	
	public int getRNUM() {
		return RNUM;
	}

	public void setRNUM(int rNUM) {
		RNUM = rNUM;
	}

	public int getUSER_KEY() {
		return USER_KEY;
	}

	public void setUSER_KEY(int uSER_KEY) {
		USER_KEY = uSER_KEY;
	}

	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public String getUSER_EMAIL() {
		return USER_EMAIL;
	}

	public void setUSER_EMAIL(String uSER_EMAIL) {
		USER_EMAIL = uSER_EMAIL;
	}

	public String getUSER_PASSWORD() {
		return USER_PASSWORD;
	}

	public void setUSER_PASSWORD(String uSER_PASSWORD) {
		USER_PASSWORD = uSER_PASSWORD;
	}

	public String getUSER_AGERANGE() {
		return USER_AGERANGE;
	}

	public void setUSER_AGERANGE(String uSER_AGERANGE) {
		USER_AGERANGE = uSER_AGERANGE;
	}

	public String getUSER_PROFILEPHOTO() {
		return USER_PROFILEPHOTO;
	}

	public void setUSER_PROFILEPHOTO(String uSER_PROFILEPHOTO) {
		USER_PROFILEPHOTO = uSER_PROFILEPHOTO;
	}

	public int getUSER_STATUS() {
		return USER_STATUS;
	}

	public void setUSER_STATUS(int uSER_STATUS) {
		USER_STATUS = uSER_STATUS;
	}

	@Override
	public String toString() {
		return "USERS [USER_KEY=" + USER_KEY + ", USER_ID=" + USER_ID + ", USER_NAME=" + USER_NAME + ", USER_EMAIL="
				+ USER_EMAIL + ", USER_PASSWORD=" + USER_PASSWORD + ", USER_AGERANGE=" + USER_AGERANGE
				+ ", USER_PROFILEPHOTO=" + USER_PROFILEPHOTO + ", USER_STATUS=" + USER_STATUS + "]";
	}
	
	

}
