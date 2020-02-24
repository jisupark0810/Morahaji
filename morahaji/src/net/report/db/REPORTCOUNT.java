package net.report.db;

import java.sql.Date;

public class REPORTCOUNT {
	private int USER_KEY;
	private Date REPORT_DATE;
	private String REPORT_REASON;
	private int BOARD_KEY;
	private int WORD_KEY;
	private String USER_NAME;
	
	
	
	public String getUSER_NAME() {
		return USER_NAME;
	}
	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}
	private String USER_ID;	//신고한 사람의 ID
	
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public int getUSER_KEY() {
		return USER_KEY;
	}
	public void setUSER_KEY(int uSER_KEY) {
		USER_KEY = uSER_KEY;
	}
	public Date getREPORT_DATE() {
		return REPORT_DATE;
	}
	public void setREPORT_DATE(Date rEPORT_DATE) {
		REPORT_DATE = rEPORT_DATE;
	}
	public String getREPORT_REASON() {
		return REPORT_REASON;
	}
	public void setREPORT_REASON(String rEPORT_REASON) {
		REPORT_REASON = rEPORT_REASON;
	}
	public int getBOARD_KEY() {
		return BOARD_KEY;
	}
	public void setBOARD_KEY(int bOARD_KEY) {
		BOARD_KEY = bOARD_KEY;
	}
	public int getWORD_KEY() {
		return WORD_KEY;
	}
	public void setWORD_KEY(int wORD_KEY) {
		WORD_KEY = wORD_KEY;
	}
	
	
	
}
