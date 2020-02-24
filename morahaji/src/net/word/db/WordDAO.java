package net.word.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.activity.db.CountDAO;
import net.hashtag.db.HashTagDAO;
import net.report.db.REPORTCOUNT;
import net.users.db.UserDAO;

public class WordDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;

	public WordDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.err.println("WordDAO DB 연결 실패 : " + ex);
		}
	}

	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// close end

	public int getNextKey() { // 다음으로 들어갈 key 값을 구함 [return : table _KEY]
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("select nvl(max(WORD_KEY),0) from WORD");
			rs = pstmt.executeQuery();
			if (rs.next())
				return (rs.getInt(1) + 1);
			else
				return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int insert(WORD w) throws SQLException { // 단어 등록
		UserDAO userDAO = new UserDAO();
		int wordKey = getNextKey();

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("INSERT INTO WORD VALUES(?, ?, ?, ?, ?, sysdate, ?)");
			pstmt.setInt(1, wordKey);
			pstmt.setInt(2, w.getUSER_KEY());
			pstmt.setString(3, w.getWORD_TITLE());
			pstmt.setString(4, w.getWORD_CONTENT());
			pstmt.setString(5, w.getWORD_EXSENTENCE());
			pstmt.setString(6, w.getWORD_GIF());
			result = pstmt.executeUpdate();

			if (result == 1) {
				userDAO.updateLastAct(w.getUSER_KEY());
				return wordKey;
			} else
				return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return -1;
	}// insert end

	public int update(WORD w) throws SQLException { // 단어 등록
		UserDAO userDAO = new UserDAO();
		int wordKey = w.getWORD_KEY();

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(
					"UPDATE WORD SET WORD_TITLE = ?, WORD_CONTENT = ?, WORD_EXSENTENCE = ?, WORD_GIF = ? WHERE WORD_KEY = ?");
			pstmt.setString(1, w.getWORD_TITLE());
			pstmt.setString(2, w.getWORD_CONTENT());
			pstmt.setString(3, w.getWORD_EXSENTENCE());
			pstmt.setString(4, w.getWORD_GIF());
			pstmt.setInt(5, wordKey);
			result = pstmt.executeUpdate();

			if (result == 1) {
				userDAO.updateLastAct(w.getUSER_KEY());
				return wordKey;
			} else
				return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return -1;
	}// insert end

	public int getListCount() {
		int x = 0;
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("SELECT count(*) from word");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
				System.out.println("게시글 수 : " + x);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return x;
	}

	public int getListCount(String keyword) {
		int x = 0;
		try {
			con = ds.getConnection();

			String sql = "SELECT count(*) from word ";
			if (keyword != null)
				sql = "SELECT count(*) from word WHERE (word_title LIKE '%" + keyword + "%' OR word_content LIKE '%"
						+ keyword + "%' OR word_exSentence LIKE '%" + keyword + "%') ";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
				System.out.println("게시글 수 : " + x);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return x;
	}

	public List<WORD> getWordList(int page, int limit, int userKey, String keyword) {
		List<WORD> wordList = new ArrayList<WORD>();
		HashTagDAO hashtagDAO = new HashTagDAO();
		CountDAO countDAO = new CountDAO();

		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		try {
			con = ds.getConnection();

			int startrow = (page - 1) * limit + 1;
			// 읽기 시작할 row 번호
			int endrow = startrow + limit - 1;
			// 읽을 마지막 row 번호

			if (userKey != 0) { // 로그인 한 사용자가 있는 상태
				String sql = "SELECT * FROM (select rownum rnum, w.* from (select * from WORDLIST join USERS using(USER_KEY)  order by word_date DESC) w) WHERE rnum>=? and rnum<=?";
				if (keyword != null)
					sql = "SELECT * FROM (select rownum rnum, w.* from (select * from WORDLIST join USERS using(USER_KEY) WHERE (word_title LIKE '%"
							+ keyword + "%' OR word_content LIKE '%" + keyword + "%' OR word_exSentence LIKE '%"
							+ keyword + "%') order by word_date DESC) w) WHERE rnum>=? and rnum<=?";
				System.out.println("sql : " + sql);
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, startrow);
				pstmt.setInt(2, endrow);
				rs = pstmt.executeQuery();

				System.out.println("--------------startrow : " + startrow + " / endrow : " + endrow);

				// join 문으로 바꾸기 11/27/19 이소희
				while (rs.next()) {
					WORD w = new WORD();
					w.setWORD_KEY(rs.getInt("WORD_KEY"));
					w.setUSER_KEY(rs.getInt("USER_KEY"));
					w.setWRITER_NAME(rs.getString("USER_NAME"));
					w.setWORD_TITLE(rs.getString("WORD_TITLE"));
					w.setWORD_CONTENT(rs.getString("WORD_CONTENT"));
					w.setWORD_EXSENTENCE(rs.getString("WORD_EXSENTENCE"));
					w.setWORD_DATE(rs.getDate("WORD_DATE"));
					w.setWORD_GIF(rs.getString("WORD_GIF"));

					w.setWORD_HASHTAG(hashtagDAO.getHashtagList(rs.getInt("WORD_KEY")));
					w.setLIKECOUNT(rs.getInt("LIKECOUNT"));
					w.setHATECOUNT(rs.getInt("HATECOUNT"));

					w.setLIKE(countDAO.isLike(con, rs.getInt("WORD_KEY"), userKey, "word"));
					w.setHATE(countDAO.isHate(con, rs.getInt("WORD_KEY"), userKey, "word"));
					w.setBOOKMARK(countDAO.isBookmark(con, rs.getInt("WORD_KEY"), userKey, "word"));
					w.setREPORT(countDAO.isReport(con, rs.getInt("WORD_KEY"), userKey));
					System.out.println(w);
					wordList.add(w);
				}
			} else {
				String sql = "SELECT * FROM (select rownum rnum, w.* from (select * from WORDLIST join USERS using(USER_KEY) order by word_date DESC) w) WHERE rnum>=? and rnum<=?";
				if (keyword != null)
					sql = "SELECT * FROM (select rownum rnum, w.* from (select * from WORDLIST join USERS using(USER_KEY) WHERE (word_title LIKE '%"
							+ keyword + "%' OR word_content LIKE '%" + keyword + "%' OR word_exSentence LIKE '%"
							+ keyword + "%') order by word_date DESC) w) WHERE rnum>=? and rnum<=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, startrow);
				pstmt.setInt(2, endrow);
				rs = pstmt.executeQuery();

				// join 문으로 바꾸기 11/27/19 이소희
				while (rs.next()) {
					WORD w = new WORD();
					w.setWORD_KEY(rs.getInt("WORD_KEY"));
					w.setUSER_KEY(rs.getInt("USER_KEY"));
					w.setWRITER_NAME(rs.getString("USER_NAME"));
					w.setWORD_TITLE(rs.getString("WORD_TITLE"));
					w.setWORD_CONTENT(rs.getString("WORD_CONTENT"));
					w.setWORD_EXSENTENCE(rs.getString("WORD_EXSENTENCE"));
					w.setWORD_DATE(rs.getDate("WORD_DATE"));
					w.setWORD_GIF(rs.getString("WORD_GIF"));

					w.setWORD_HASHTAG(hashtagDAO.getHashtagList(rs.getInt("WORD_KEY")));
					w.setLIKECOUNT(rs.getInt("LIKECOUNT"));
					w.setHATECOUNT(rs.getInt("HATECOUNT"));

					System.out.println(w);
					wordList.add(w);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end return
		return wordList;
	}

	public List<WORD> getWordList(int page, int limit, int userKey, List<Integer> hash_wordKeyList) {
		List<WORD> wordList = new ArrayList<WORD>();
		HashTagDAO hashtagDAO = new HashTagDAO();
		CountDAO countDAO = new CountDAO();

		if (con != null) {
			try {
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		try {
			con = ds.getConnection();

			int startrow = (page - 1) * limit + 1;
			// 읽기 시작할 row 번호
			int endrow = startrow + limit - 1;
			// 읽을 마지막 row 번호

			String wordKeys = "";
			for (int i = 0; i < hash_wordKeyList.size(); i++) {
				wordKeys += hash_wordKeyList.get(i) + ",";
			}
			wordKeys = wordKeys.substring(0, wordKeys.length() - 1);
			if (userKey != 0) { // 로그인 한 사용자가 있는 상태
				String sql = "SELECT * " + "FROM (select rownum rnum, w.* " + "from (select * "
						+ "from WORDLIST join USERS using(USER_KEY) WHERE word_key IN (" + wordKeys
						+ ") order by word_date DESC) w) " + "WHERE rnum>=? and rnum<=?";
				System.out.println(sql);
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, startrow);
				pstmt.setInt(2, endrow);
				rs = pstmt.executeQuery();

				System.out.println("--------------startrow : " + startrow + " / endrow : " + endrow);

				// join 문으로 바꾸기 11/27/19 이소희
				while (rs.next()) {
					WORD w = new WORD();
					w.setWORD_KEY(rs.getInt("WORD_KEY"));
					w.setUSER_KEY(rs.getInt("USER_KEY"));
					w.setWRITER_NAME(rs.getString("USER_NAME"));
					w.setWORD_TITLE(rs.getString("WORD_TITLE"));
					w.setWORD_CONTENT(rs.getString("WORD_CONTENT"));
					w.setWORD_EXSENTENCE(rs.getString("WORD_EXSENTENCE"));
					w.setWORD_DATE(rs.getDate("WORD_DATE"));
					w.setWORD_GIF(rs.getString("WORD_GIF"));

					w.setWORD_HASHTAG(hashtagDAO.getHashtagList(rs.getInt("WORD_KEY")));
					w.setLIKECOUNT(rs.getInt("LIKECOUNT"));
					w.setHATECOUNT(rs.getInt("HATECOUNT"));

					w.setLIKE(countDAO.isLike(con, rs.getInt("WORD_KEY"), userKey, "word"));
					w.setHATE(countDAO.isHate(con, rs.getInt("WORD_KEY"), userKey, "word"));
					w.setBOOKMARK(countDAO.isBookmark(con, rs.getInt("WORD_KEY"), userKey, "word"));
					w.setREPORT(countDAO.isReport(con, rs.getInt("WORD_KEY"), userKey));
					System.out.println(w);
					wordList.add(w);
				}
			} else {
				String sql = "SELECT * " + "FROM (select rownum rnum, w.* " + "from (select * "
						+ "from WORDLIST join USERS using(USER_KEY) WHERE word_key IN (" + wordKeys
						+ ") order by word_date DESC) w) WHERE rnum>=? and rnum<=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, startrow);
				pstmt.setInt(2, endrow);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					WORD w = new WORD();
					w.setWORD_KEY(rs.getInt("WORD_KEY"));
					w.setUSER_KEY(rs.getInt("USER_KEY"));
					w.setWRITER_NAME(rs.getString("USER_NAME"));
					w.setWORD_TITLE(rs.getString("WORD_TITLE"));
					w.setWORD_CONTENT(rs.getString("WORD_CONTENT"));
					w.setWORD_EXSENTENCE(rs.getString("WORD_EXSENTENCE"));
					w.setWORD_DATE(rs.getDate("WORD_DATE"));
					w.setWORD_GIF(rs.getString("WORD_GIF"));

					w.setWORD_HASHTAG(hashtagDAO.getHashtagList(rs.getInt("WORD_KEY")));
					w.setLIKECOUNT(rs.getInt("LIKECOUNT"));
					w.setHATECOUNT(rs.getInt("HATECOUNT"));

					System.out.println(w);
					wordList.add(w);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end return
		return wordList;
	}

	/** 박지수_getBmkListCount, getBMKList, getmywordListCount, getmywordList 시작 **/

	// 북마크 몇개인지 구함
	public int getBmkListCount(int userkey) {
		int count = 0;
		try {
			con = ds.getConnection();
			String sql = "select count(*) from WORDLIST " + "where word_key in "
					+ "(select word_key from count WHERE count_type = 'bookmark' and USER_KEY= ?) ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userkey);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}

	// 북마크 리스트 가져옴
	public List<WORD> getBMKList(int page, int limit, int userkey) {
		List<WORD> list = new ArrayList<WORD>();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		UserDAO name = new UserDAO();
		CountDAO countDAO = new CountDAO();
		HashTagDAO hashtagDAO = new HashTagDAO();
		try {
			con = ds.getConnection();
			String sql = "select * from " + "(select rownum rnum, a.* from "
					+ "(select w.* from   (select * from WORDLIST order by word_date DESC) w) a  "
					+ "where word_key in (select word_key from count WHERE count_type = 'bookmark' and USER_KEY= ?)) "
					+ "where rnum>= ? and rnum<=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userkey);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				WORD w = new WORD();
				w.setWORD_KEY(rs.getInt("WORD_KEY"));
				w.setUSER_KEY(rs.getInt("USER_KEY"));
				w.setWORD_TITLE(rs.getString("WORD_TITLE"));
				w.setWORD_CONTENT(rs.getString("WORD_CONTENT"));
				w.setWORD_EXSENTENCE(rs.getString("WORD_EXSENTENCE"));
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				w.setWORD_DATE_CHAR(dateFormat.format(rs.getDate("WORD_DATE")));
				w.setWORD_GIF(rs.getString("WORD_GIF"));
				ArrayList<String> hashtaglist = hashtagDAO.getHashtagList(rs.getInt("WORD_KEY"));
				String writername = name.getUserName((rs.getInt("USER_KEY")), 0);
				w.setWRITER_NAME(writername);
				w.setWORD_HASHTAG(hashtaglist);
				w.setLIKECOUNT(rs.getInt("LIKECOUNT"));
				w.setHATECOUNT(rs.getInt("HATECOUNT"));

				w.setLIKE(countDAO.isLike(con, rs.getInt("WORD_KEY"), userkey, "word"));
				w.setHATE(countDAO.isHate(con, rs.getInt("WORD_KEY"), userkey, "word"));

				list.add(w);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;

	}

	// 나의 단어 몇개인지 구함
	public int getmywordListCount(int userkey) {
		int count = 0;
		try {
			con = ds.getConnection();
			String sql = "select count(*) from " + "WORDLIST " + "WHERE USER_KEY=? ";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userkey);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return count;
	}

	// 나의단어 리스트 가져옴
	public List<WORD> getmywordList(int page, int limit, int userkey) {
		List<WORD> list = new ArrayList<WORD>();

		HashTagDAO hashtagDAO = new HashTagDAO();
		int startrow = (page - 1) * limit + 1;
		int endrow = startrow + limit - 1;
		try {
			con = ds.getConnection();
			String sql = "select * from (select rownum rnum, w.* "
					+ "from   (select * from WORDLIST WHERE USER_KEY=? order by word_date DESC) w) "
					+ "where rnum>=? and rnum<=? ";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userkey);
			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				WORD w = new WORD();
				w.setWORD_KEY(rs.getInt("WORD_KEY"));
				w.setUSER_KEY(rs.getInt("USER_KEY"));
				w.setWORD_TITLE(rs.getString("WORD_TITLE"));
				w.setWORD_CONTENT(rs.getString("WORD_CONTENT"));
				w.setWORD_EXSENTENCE(rs.getString("WORD_EXSENTENCE"));
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				w.setWORD_DATE_CHAR(dateFormat.format(rs.getDate("WORD_DATE")));
				w.setWORD_GIF(rs.getString("WORD_GIF"));
				ArrayList<String> hashtaglist = hashtagDAO.getHashtagList(rs.getInt("WORD_KEY"));
				w.setWORD_HASHTAG(hashtaglist);
				w.setLIKECOUNT(rs.getInt("LIKECOUNT"));
				w.setHATECOUNT(rs.getInt("HATECOUNT"));
				list.add(w);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	/** 박지수_getBmkListCount, getBMKList, getmywordListCount, getmywordList 끝 **/
	/** 지은- 어드민 워드 신고하기 시작 **/
	public int getReportedListCount() {
		int x = 0;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(
					"select count(*) from word left inner join (select count(word_key) counts, word_key from reportcount group by word_key) using (word_key)");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			System.out.println("getReportedListCount() 에러:" + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return x;
	}

	public List<WORD> getReportedList(int page, int limit) {
		List<WORD> list = new ArrayList<WORD>();
		UserDAO userdao = new UserDAO();

		try {
			con = ds.getConnection();
			String sql = "select * from (select b.*,rownum rnum from (select * from word left inner join (select count(word_key) counts ,word_key from reportcount group by word_key)  using(word_key) order by counts desc) b) where rnum between ? and ?";
			pstmt = con.prepareStatement(sql);
			int startrow = (page - 1) * limit + 1;
			int endrow = startrow + limit - 1;

			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				WORD w = new WORD();
				w.setWORD_KEY(rs.getInt("WORD_KEY"));
				w.setWORD_TITLE(rs.getString("WORD_TITLE"));
				w.setREPORTCOUNT(rs.getInt("COUNTS"));
				w.setUSER_NAME(userdao.getUserName(rs.getInt("USER_KEY"), 0));
				w.setRNUM(rs.getInt("rnum"));
				list.add(w);
			}
		} catch (Exception ex) {
			System.out.println("getReportedList() 에러: " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return list;
	}

	public int getReportedListCount(String field, String value) {

		int x = 0;
		try {
			con = ds.getConnection();
			String sql = "select count(*) from word left inner join (select count(word_key) counts, word_key from reportcount group by word_key) using (word_key) where "
					+ field + " like ?";
			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + value + "%");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			System.out.println("getReportedListCount() 에러" + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return x;
	}

	public List<WORD> getReportedList(String field, String value, int page, int limit) {
		List<WORD> list = new ArrayList<WORD>();
		UserDAO userdao = new UserDAO();

		try {
			con = ds.getConnection();

			String sql = "select * from (select b.*,rownum rnum from " + " (select * from word left inner join "
					+ " (select count(word_key) counts ,word_key " + " from reportcount group by word_key) "
					+ " using(word_key) where " + field + " like ? order by counts desc) b) "
					+ " where rnum between ? and ?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + value + "%");
			int startrow = (page - 1) * limit + 1;
			int endrow = startrow + limit - 1;

			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				WORD w = new WORD();
				w.setWORD_KEY(rs.getInt("WORD_KEY"));
				w.setWORD_TITLE(rs.getString("WORD_TITLE"));
				w.setREPORTCOUNT(rs.getInt("COUNTS"));
				w.setUSER_NAME(userdao.getUserName(rs.getInt("USER_KEY"), 0));
				w.setRNUM(rs.getInt("rnum"));
				list.add(w);
				System.out.println(w);
			}
		} catch (Exception ex) {
			System.out.println("getReportedList() 에러: " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}

		return list;
	}

	public WORD report_info(int wordkey) {
		WORD w = null;
		try {
			con = ds.getConnection();
			String sql = "select * from word where word_key=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, wordkey);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				w = new WORD();
				w.setWORD_KEY(rs.getInt("WORD_KEY"));
				w.setWORD_TITLE(rs.getString("WORD_TITLE"));
				w.setWORD_CONTENT(rs.getString("WORD_CONTENT"));
				w.setWORD_EXSENTENCE(rs.getString("WORD_EXSENTENCE"));
				w.setWORD_DATE(rs.getDate("WORD_DATE"));
				w.setWORD_GIF(rs.getString("WORD_GIF"));
			}
			return w;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("report_info()에러 " + ex);
		} finally {
			close();
		}
		return null;
	}

	public List<REPORTCOUNT> getDetail(int wordkey) {
		List<REPORTCOUNT> list = new ArrayList<REPORTCOUNT>();
		UserDAO userdao = new UserDAO();

		try {
			con = ds.getConnection();
			String sql = "select * from reportcount" + "        left join users using(user_key) where word_key=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, wordkey);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				REPORTCOUNT r = new REPORTCOUNT();
				r.setBOARD_KEY(rs.getInt("WORD_KEY"));
				r.setREPORT_DATE(rs.getDate("REPORT_DATE"));
				r.setREPORT_REASON(rs.getString("REPORT_REASON"));
				r.setUSER_KEY(rs.getInt("USER_KEY"));
				r.setWORD_KEY(rs.getInt("WORD_KEY"));
				r.setUSER_ID(rs.getString("USER_ID"));
				r.setUSER_NAME(userdao.getUserName(rs.getInt("USER_KEY"), 0));
				list.add(r);
				System.out.println("list에붙이는 r" + r);

			}

		} catch (Exception ex) {
			System.out.println("getDetail() 에러 : " + ex);
		} finally {
			close();
		}
		return list;
	}

	/** 지은- 어드민 워드 신고하기 끝 **/
	public WORD getDetailForModify(int wordKey) {
		WORD word = new WORD();

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("select * from WORD where WORD_KEY = ? ");
			pstmt.setInt(1, wordKey);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				word.setWORD_KEY(wordKey);
				word.setUSER_KEY(rs.getInt("USER_KEY"));
				word.setWORD_TITLE(rs.getString("WORD_TITLE"));
				word.setWORD_CONTENT(rs.getString("WORD_CONTENT"));
				word.setWORD_EXSENTENCE(rs.getString("WORD_EXSENTENCE"));
				word.setWORD_DATE(rs.getDate("WORD_DATE"));
				word.setWORD_GIF(rs.getString("WORD_GIF"));
				HashTagDAO hashtagDAO = new HashTagDAO();
				ArrayList<String> hashtags = hashtagDAO.getHashtagList(rs.getInt("WORD_KEY"));
				String hashtag = "";
				for (int i = 0; i < hashtags.size(); i++) {
					hashtag += "#" + hashtags.get(i) + " ";
				}
				word.setWORD_HASHTAGS(hashtag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end return
		return word;
	}

	public int deleteWord(int wordKey) {
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("DELETE FROM WORD WHERE WORD_KEY = ? ");
			pstmt.setInt(1, wordKey);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}

	// 신고글 삭제용 디테일 시작===========================================
	public WORD getWord(int wordkey) {
		HashTagDAO hashtagDAO = new HashTagDAO();
		WORD w = new WORD();
		try {
			con = ds.getConnection();

			String sql = "SELECT * FROM WORD where word_key=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, wordkey);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				w.setWORD_KEY(rs.getInt("WORD_KEY"));
				w.setUSER_KEY(rs.getInt("USER_KEY"));
				w.setWORD_TITLE(rs.getString("WORD_TITLE"));
				w.setWORD_CONTENT(rs.getString("WORD_CONTENT"));
				w.setWORD_EXSENTENCE(rs.getString("WORD_EXSENTENCE"));
				w.setWORD_DATE(rs.getDate("WORD_DATE"));
				w.setWORD_GIF(rs.getString("WORD_GIF"));

				w.setWORD_HASHTAG(hashtagDAO.getHashtagList(rs.getInt("WORD_KEY")));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end return
		return w;
	}
	// 신고글 삭제용 디테일 끝===========================================

	public List<String> getTitles(String title) {
      List<String> titles = new ArrayList<String>();
      try {
         con = ds.getConnection();
         String sql = "SELECT WORD_TITLE from WORD WHERE  UPPER(WORD_TITLE) LIKE UPPER(?)";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, "%"+title+"%");
         rs = pstmt.executeQuery();
         while (rs.next()) {
            titles.add(rs.getString(1));
            System.err.println(rs.getString(1));
         }
      } catch (Exception ex) {
         System.out.println("getMyListCount() 에러 : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return titles;
   }
	// =======================================================

	public List<List<WORD>> getRanking(int size, int page) {
		List<List<WORD>> ranking = new ArrayList<List<WORD>>();
		try {
			for (int i = 0; i < page; i++) {
				int start = (i * size) + 1;
				int end = start + (size - 1);
				ranking.add(getSubRanking(start, end));
			}
		} catch (Exception ex) {
			System.out.println("getDetail() 에러 : " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return ranking;
	}

	public List<WORD> getSubRanking(int start, int end) {
		System.out.println("start : " + start + " / end : " + end);

		List<WORD> list = new ArrayList<WORD>();
		try {
			con = ds.getConnection();
			String sql = "SELECT * FROM (SELECT A.*, ROWNUM RNUM FROM ((SELECT * FROM WORDLIST ORDER BY NVL(LIKECOUNT,0) DESC ) A)) where rnum >=? and rnum<=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				WORD word = new WORD();
				word.setRNUM(rs.getInt("RNUM"));
				String title = rs.getString("WORD_TITLE");
				if (title.length() > 9)
					title = title.substring(0, 9) + "...";
				word.setWORD_TITLE(title);
				word.setWORD_KEY(rs.getInt("WORD_KEY"));
				list.add(word);
			}
		} catch (Exception ex) {
			System.out.println("getDetail() 에러 : " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	// =======================================================
}
