package net.reply.db;

public class Reply {
	private int REPLY_KEY;
	private int USER_KEY;
	private String REPLY_CONTENT;
	private String REPLY_DATE;
	private int BOARD_KEY;
	private int REPLY_RE_REF;
	private int REPLY_RE_LEV;
	private int REPLY_RE_SEQ;

	public int getREPLY_KEY() {
		return REPLY_KEY;
	}
	public void setREPLY_KEY(int rEPLY_KEY) {
		REPLY_KEY = rEPLY_KEY;
	}
	public int getUSER_KEY() {
		return USER_KEY;
	}
	public void setUSER_KEY(int uSER_KEY) {
		USER_KEY = uSER_KEY;
	}
	public String getREPLY_CONTENT() {
		return REPLY_CONTENT;
	}
	public void setREPLY_CONTENT(String rEPLY_CONTENT) {
		REPLY_CONTENT = rEPLY_CONTENT;
	}
	public String getREPLY_DATE() {
		return REPLY_DATE;
	}
	public void setREPLY_DATE(String rEPLY_DATE) {
		REPLY_DATE = rEPLY_DATE;
	}
	public int getBOARD_KEY() {
		return BOARD_KEY;
	}
	public void setBOARD_KEY(int bOARD_KEY) {
		BOARD_KEY = bOARD_KEY;
	}
	public int getREPLY_RE_REF() {
		return REPLY_RE_REF;
	}
	public void setREPLY_RE_REF(int rEPLY_RE_REF) {
		REPLY_RE_REF = rEPLY_RE_REF;
	}
	public int getREPLY_RE_LEV() {
		return REPLY_RE_LEV;
	}
	public void setREPLY_RE_LEV(int rEPLY_RE_LEV) {
		REPLY_RE_LEV = rEPLY_RE_LEV;
	}
	public int getREPLY_RE_SEQ() {
		return REPLY_RE_SEQ;
	}
	public void setREPLY_RE_SEQ(int rEPLY_RE_SEQ) {
		REPLY_RE_SEQ = rEPLY_RE_SEQ;
	}

	

}
