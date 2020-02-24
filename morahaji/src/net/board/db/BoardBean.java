package net.board.db;

public class BoardBean {
	private int BOARD_KEY;
	private int USER_KEY;
	private String USER_NAME;
	private String BOARD_TITLE_BEFORE;
	private String BOARD_TITLE;
	private String BOARD_TITLE_AFTER;
	private String BOARD_CONTENT;
	private String BOARD_GIF;
	private String BOARD_DATE;
	private String BOARD_CONTENT_BEFORE;
	private String BOARD_CONTENT_AFTER;
	private boolean BOARD_TITLE_SEARCH;
	private boolean BOARD_CONTENT_SEARCH;
	private int BOARD_READCOUNT;
	private int BOARD_REPLYCOUNT;
	private int BOARD_LIKECOUNT;
	private int REPORTCOUNT;
	private boolean REPORT;
	private int RNUM;

	public int getRNUM() {
		return RNUM;
	}

	public void setRNUM(int rNUM) {
		RNUM = rNUM;
	}

	public boolean isREPORT() {
		return REPORT;
	}

	public void setREPORT(boolean rEPORT) {
		REPORT = rEPORT;
	}

	public int getREPORTCOUNT() {
		return REPORTCOUNT;
	}

	public void setREPORTCOUNT(int rEPORTCOUNT) {
		REPORTCOUNT = rEPORTCOUNT;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public int getBOARD_LIKECOUNT() {
		return BOARD_LIKECOUNT;
	}

	public void setBOARD_LIKECOUNT(int bOARD_LIKECOUNT) {
		BOARD_LIKECOUNT = bOARD_LIKECOUNT;
	}

	public int getBOARD_REPLYCOUNT() {
		return BOARD_REPLYCOUNT;
	}

	public void setBOARD_REPLYCOUNT(int bOARD_REPLYCOUNT) {
		BOARD_REPLYCOUNT = bOARD_REPLYCOUNT;
	}

	public int getBOARD_READCOUNT() {
		return BOARD_READCOUNT;
	}

	public void setBOARD_READCOUNT(int bOARD_READCOUNT) {
		BOARD_READCOUNT = bOARD_READCOUNT;
	}

	public String getBOARD_TITLE_BEFORE() {
		return BOARD_TITLE_BEFORE;
	}

	public void setBOARD_TITLE_BEFORE(String bOARD_TITLE_BEFORE) {
		BOARD_TITLE_BEFORE = bOARD_TITLE_BEFORE;
	}

	public String getBOARD_TITLE_AFTER() {
		return BOARD_TITLE_AFTER;
	}

	public void setBOARD_TITLE_AFTER(String bOARD_TITLE_AFTER) {
		BOARD_TITLE_AFTER = bOARD_TITLE_AFTER;
	}

	public boolean isBOARD_CONTENT_SEARCH() {
		return BOARD_CONTENT_SEARCH;
	}

	public void setBOARD_CONTENT_SEARCH(boolean bOARD_CONTENT_SEARCH) {
		BOARD_CONTENT_SEARCH = bOARD_CONTENT_SEARCH;
	}

	public boolean isBOARD_TITLE_SEARCH() {
		return BOARD_TITLE_SEARCH;
	}

	public void setBOARD_TITLE_SEARCH(boolean bOARD_TITLE_SEARCH) {
		BOARD_TITLE_SEARCH = bOARD_TITLE_SEARCH;
	}

	public String getBOARD_CONTENT_BEFORE() {
		return BOARD_CONTENT_BEFORE;
	}

	public void setBOARD_CONTENT_BEFORE(String bOARD_CONTENT_BEFORE) {
		BOARD_CONTENT_BEFORE = bOARD_CONTENT_BEFORE;
	}

	public String getBOARD_CONTENT_AFTER() {
		return BOARD_CONTENT_AFTER;
	}

	public void setBOARD_CONTENT_AFTER(String bOARD_CONTENT_AFTER) {
		BOARD_CONTENT_AFTER = bOARD_CONTENT_AFTER;
	}

	public BoardBean() {

	}

	public int getBOARD_KEY() {
		return BOARD_KEY;
	}

	public void setBOARD_KEY(int bOARD_KEY) {
		BOARD_KEY = bOARD_KEY;
	}

	public int getUSER_KEY() {
		return USER_KEY;
	}

	public void setUSER_KEY(int uSER_KEY) {
		USER_KEY = uSER_KEY;
	}

	public String getBOARD_TITLE() {
		return BOARD_TITLE;
	}

	public void setBOARD_TITLE(String bOARD_TITLE) {
		BOARD_TITLE = bOARD_TITLE;
	}

	public String getBOARD_CONTENT() {
		return BOARD_CONTENT;
	}

	public void setBOARD_CONTENT(String bOARD_CONTENT) {
		BOARD_CONTENT = bOARD_CONTENT;
	}

	public String getBOARD_GIF() {
		return BOARD_GIF;
	}

	public void setBOARD_GIF(String bOARD_GIF) {
		BOARD_GIF = bOARD_GIF;
	}

	public String getBOARD_DATE() {
		return BOARD_DATE;
	}

	public void setBOARD_DATE(String bOARD_DATE) {
		BOARD_DATE = bOARD_DATE;
	}

}