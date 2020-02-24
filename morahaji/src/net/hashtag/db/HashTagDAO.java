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
			System.err.println("HashTagDAO DB ���� ���� : " + ex);
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

	public boolean isHashtag(String hashtag) throws SQLException { // �ܾ� ��� [return : �ؽ��±� ����]
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("SELECT * FROM HASHTAG WHERE HASH_TITLE = ? ");
			pstmt.setString(1, hashtag);
			rs = pstmt.executeQuery();

			if (rs.next()) { // �ؽ��±װ� ����
				return true;
			} else { // �ؽ��±װ� ���� �߰��ؾ� ��
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

	public boolean isHashtagWord(int wordKey) throws SQLException { // �ܾ �ؽ��±� �ִ��� Ȯ��
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("SELECT * FROM HASHTAG_POST WHERE WORD_KEY = ? ");
			pstmt.setInt(1, wordKey);
			rs = pstmt.executeQuery();

			if (rs.next()) { // �ؽ��±װ� ����
				return true;
			} else { // �ؽ��±װ� ���� �߰��ؾ� ��
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

	public int getNextKey() { // �������� �� key ���� ���� [return : table _KEY]
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

	public int insertHashtag(String hashtag) { // �ؽ��±� �߰� [return : hashtagKey]
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

	public int insertHashtagWord(String hashtag, int wordKey) { // �ؽ��±װ� �� �� �߰� - �ܾ�
		try {
			con = ds.getConnection();

			System.out.println("HAASH�Է�" + hashtag + "/" + wordKey);
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

	public int deleteHashtagWord(int wordKey) { // �ؽ��±װ� �� �� ���� - �ܾ�
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
