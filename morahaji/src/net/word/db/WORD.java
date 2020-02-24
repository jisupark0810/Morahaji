package net.word.db;

import java.sql.Date;
import java.util.ArrayList;

public class WORD {
	private int WORD_KEY;
	private int USER_KEY;
	private String WORD_TITLE;
	private String WORD_CONTENT;
	private String WORD_EXSENTENCE;
	private Date WORD_DATE;
	private String WORD_GIF;
	private int RNUM;

public int getRNUM() {
		return RNUM;
	}

	public void setRNUM(int rNUM) {
		RNUM = rNUM;
	}

	// DB에  없는 값들
	private String WRITER_NAME;
	private ArrayList<String> WORD_HASHTAG;
	private String WORD_HASHTAGS;
	private int LIKECOUNT;
	private int HATECOUNT;
	private int REPORTCOUNT;
	private boolean LIKE;
	private boolean HATE;
	private boolean BOOKMARK;
	private boolean REPORT;
	private String WORD_DATE_CHAR;
	private String USER_NAME;

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public String getWORD_DATE_CHAR() {
		return WORD_DATE_CHAR;
	}

	public void setWORD_DATE_CHAR(String wORD_DATE_CHAR) {
		WORD_DATE_CHAR = wORD_DATE_CHAR;
	}

	public String getWRITER_NAME() {
		return WRITER_NAME;
	}

	public void setWRITER_NAME(String wRITER_NAME) {
		WRITER_NAME = wRITER_NAME;
	}

	public int getWORD_KEY() {
		return WORD_KEY;
	}

	public void setWORD_KEY(int wORD_KEY) {
		WORD_KEY = wORD_KEY;
	}

	public int getUSER_KEY() {
		return USER_KEY;
	}

	public void setUSER_KEY(int uSER_KEY) {
		USER_KEY = uSER_KEY;
	}

	public String getWORD_TITLE() {
		return WORD_TITLE;
	}

	public void setWORD_TITLE(String wORD_TITLE) {
		WORD_TITLE = wORD_TITLE;
	}

	public String getWORD_CONTENT() {
		return WORD_CONTENT;
	}

	public void setWORD_CONTENT(String wORD_CONTENT) {
		WORD_CONTENT = wORD_CONTENT;
	}

	public String getWORD_EXSENTENCE() {
		return WORD_EXSENTENCE;
	}

	public void setWORD_EXSENTENCE(String wORD_EXSENTENCE) {
		WORD_EXSENTENCE = wORD_EXSENTENCE;
	}

	public Date getWORD_DATE() {
		return WORD_DATE;
	}

	public void setWORD_DATE(Date wORD_DATE) {
		WORD_DATE = wORD_DATE;
	}

	public String getWORD_GIF() {
		return WORD_GIF;
	}

	public void setWORD_GIF(String wORD_GIF) {
		WORD_GIF = wORD_GIF;
	}

	public ArrayList<String> getWORD_HASHTAG() {
		return WORD_HASHTAG;
	}

	public void setWORD_HASHTAG(ArrayList<String> wORD_HASHTAG) {
		WORD_HASHTAG = wORD_HASHTAG;
	}

	public int getLIKECOUNT() {
		return LIKECOUNT;
	}

	public void setLIKECOUNT(int lIKECOUNT) {
		LIKECOUNT = lIKECOUNT;
	}

	public int getHATECOUNT() {
		return HATECOUNT;
	}

	public void setHATECOUNT(int hATECOUNT) {
		HATECOUNT = hATECOUNT;
	}

	public boolean isLIKE() {
		return LIKE;
	}

	public void setLIKE(boolean lIKE) {
		LIKE = lIKE;
	}

	public boolean isHATE() {
		return HATE;
	}

	public void setHATE(boolean hATE) {
		HATE = hATE;
	}

	public boolean isBOOKMARK() {
		return BOOKMARK;
	}

	public void setBOOKMARK(boolean bOOKMARK) {
		BOOKMARK = bOOKMARK;
	}

	public boolean isREPORT() {
		return REPORT;
	}

	public void setREPORT(boolean rEPORT) {
		REPORT = rEPORT;
	}

	public String getWORD_HASHTAGS() {
		return WORD_HASHTAGS;
	}

	public void setWORD_HASHTAGS(String wORD_HASHTAGS) {
		WORD_HASHTAGS = wORD_HASHTAGS;
	}

	public int getREPORTCOUNT() {
		return REPORTCOUNT;
	}

	public void setREPORTCOUNT(int rEPORTCOUNT) {
		REPORTCOUNT = rEPORTCOUNT;
	}

	@Override
	public String toString() {
		return "WORD [WORD_KEY=" + WORD_KEY + ", USER_KEY=" + USER_KEY + ", WORD_TITLE=" + WORD_TITLE
				+ ", WORD_CONTENT=" + WORD_CONTENT + ", WORD_EXSENTENCE=" + WORD_EXSENTENCE + ", WORD_DATE=" + WORD_DATE
				+ ", WORD_GIF=" + WORD_GIF + ", WORD_HASHTAG=" + WORD_HASHTAG + ", WORD_HASHTAGS=" + WORD_HASHTAGS
				+ ", LIKECOUNT=" + LIKECOUNT + ", HATECOUNT=" + HATECOUNT + ", REPORTCOUNT=" + REPORTCOUNT + ", LIKE="
				+ LIKE + ", HATE=" + HATE + ", BOOKMARK=" + BOOKMARK + ", REPORT=" + REPORT + "]";
	}

}
