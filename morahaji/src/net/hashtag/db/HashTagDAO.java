package net.hashtag.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class HashTagDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;

	public HashTagDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.err.println("HashTagDAO DB 연결 실패 : " + ex);
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

	public boolean isHashtag(String hashtag) throws SQLException { // 단어 등록 [return : 해시태그 유무]
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("SELECT * FROM HASHTAG WHERE HASH_TITLE = ? ");
			pstmt.setString(1, hashtag);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 해시태그가 있음
				return true;
			} else { // 해시태그가 없음 추가해야 함
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return false;
	}// insert end

	public boolean isHashtagWord(int wordKey) throws SQLException { // 단어에 해시태그 있는지 확인
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("SELECT * FROM HASHTAG_POST WHERE WORD_KEY = ? ");
			pstmt.setInt(1, wordKey);
			rs = pstmt.executeQuery();

			if (rs.next()) { // 해시태그가 있음
				return true;
			} else { // 해시태그가 없음 추가해야 함
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return false;
	}// insert end

	public int getNextKey() { // 다음으로 들어갈 key 값을 구함 [return : table _KEY]
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("select nvl(max(HASH_KEY),0) from HASHTAG");
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

	public int insertHashtag(String hashtag) { // 해시태그 추가 [return : hashtagKey]
		int hashtagKey = getNextKey();

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("INSERT INTO HASHTAG VALUES(?, ?)");
			pstmt.setInt(1, hashtagKey);
			pstmt.setString(2, hashtag);
			result = pstmt.executeUpdate();

			if (result == 1)
				return hashtagKey;
			else
				return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return -1;
	}

	public int insertHashtagWord(String hashtag, int wordKey) { // 해시태그가 들어간 곳 추가 - 단어
		try {
			con = ds.getConnection();

			System.out.println("HAASH입력" + hashtag + "/" + wordKey);
			pstmt = con.prepareStatement(
					"INSERT INTO HASHTAG_POST(HASH_KEY, WORD_KEY) VALUES((select HASH_KEY from hashtag where HASH_TITLE = ? ), ?)");
			pstmt.setString(1, hashtag);
			pstmt.setInt(2, wordKey);
			result = pstmt.executeUpdate();
			if (result == 1)
				return 1;
			else
				return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return -1;
	}

	public int deleteHashtagWord(int wordKey) { // 해시태그가 들어간 곳 삭제 - 단어
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("DELETE FROM HASHTAG_POST WHERE WORD_KEY= ? ");
			pstmt.setInt(1, wordKey);
			result = pstmt.executeUpdate();
			if (result > 0)
				return 1;
			else
				return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return -1;
	}

	public ArrayList<String> getHashtagList(int wordKey) {
		ArrayList<String> hashList = new ArrayList<String>();
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("select * from hashtag natural join hashtag_post where word_key = ? ");
			pstmt.setInt(1, wordKey);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				hashList.add(rs.getString("HASH_TITLE"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return hashList;
	}

	public List<Integer> getWordList(String tag) {
		ArrayList<Integer> wordKeyList = new ArrayList<Integer>();
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(
					"select WORD_KEY from hashtag_post where hash_key = (SELECT HASH_KEY FROM HASHTAG WHERE HASH_TITLE = ? ) GROUP BY WORD_KEY ");
			pstmt.setString(1, tag);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				wordKeyList.add(rs.getInt(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return wordKeyList;
	}

}
