package net.activity.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CountDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;

	public CountDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.err.println("CountDAO DB 연결 실패 : " + ex);
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

	public int getCount(int wordKey, String countType, String postType) {
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(
					"select count(*) from count where word_key = ? AND count_type = ? AND post_type = ?");
			pstmt.setInt(1, wordKey);
			pstmt.setString(2, countType);
			pstmt.setString(3, postType);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean isLike(Connection con, int wordKey, int userKey, String postType) {
		try {
			pstmt = con.prepareStatement(
					"select * from count where user_key = ? AND count_type = 'like' AND post_type = ? AND word_key = ?");
			pstmt.setInt(1, userKey);
			pstmt.setString(2, postType);
			pstmt.setInt(3, wordKey);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public boolean isHate(Connection con, int wordKey, int userKey, String postType) {
		try {
			pstmt = con.prepareStatement(
					"select * from count where user_key = ? AND count_type = 'hate' AND post_type = ? AND word_key = ?");
			pstmt.setInt(1, userKey);
			pstmt.setString(2, postType);
			pstmt.setInt(3, wordKey);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public boolean isBookmark(Connection con, int wordKey, int userKey, String postType) {
		try {
			pstmt = con.prepareStatement(
					"select * from count where user_key = ? AND count_type = 'bookmark' AND post_type = ? AND word_key = ?");
			pstmt.setInt(1, userKey);
			pstmt.setString(2, postType);
			pstmt.setInt(3, wordKey);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public boolean isReport(Connection con, int wordKey, int userKey) {
		try {
			pstmt = con.prepareStatement("select * from reportcount where user_key = ? AND word_key = ?");
			pstmt.setInt(1, userKey);
			pstmt.setInt(2, wordKey);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public int addCount(HashMap<String, String> addCountSource) {
		try {
			con = ds.getConnection();

			String sql = "";
			if (addCountSource.get("postType").equals("word"))
				sql = "INSERT INTO COUNT VALUES(?, ?, ?, SYSDATE, null, ?)";
			if (addCountSource.get("postType").equals("board"))
				sql = "INSERT INTO COUNT VALUES(?, ?, ?, SYSDATE, ?, null)";
			pstmt = con.prepareStatement(sql);
			if (addCountSource.get("userKey") == null)
				pstmt.setNull(1, Types.INTEGER);
			else
				pstmt.setInt(1, Integer.parseInt(addCountSource.get("userKey")));
			pstmt.setString(2, addCountSource.get("countType"));
			pstmt.setString(3, addCountSource.get("postType"));
			pstmt.setInt(4, Integer.parseInt(addCountSource.get("postKey")));
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}

	public int removeCount(HashMap<String, String> removeCountSource) {
		try {
			con = ds.getConnection();

			String sql = "";
			if (removeCountSource.get("postType").equals("word"))
				sql = "DELETE FROM COUNT WHERE USER_KEY = ? AND WORD_KEY = ? AND COUNT_TYPE = ?";
			if (removeCountSource.get("postType").equals("board"))
				sql = "DELETE FROM COUNT WHERE USER_KEY = ? AND BOARD_KEY = ? AND COUNT_TYPE = ?";
			pstmt = con.prepareStatement(sql);
			if (removeCountSource.get("userKey") == null)
				pstmt.setNull(1, Types.INTEGER);
			else
				pstmt.setInt(1, Integer.parseInt(removeCountSource.get("userKey")));
			pstmt.setInt(2, Integer.parseInt(removeCountSource.get("postKey")));
			pstmt.setString(3, removeCountSource.get("countType"));
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}

	public int addReport(HashMap<String, String> addReportSource) {
		try {
			con = ds.getConnection();

			String sql = "";
			if (addReportSource.get("postType").equals("word"))
				sql = "INSERT INTO REPORTCOUNT VALUES(?, SYSDATE, ?, null, ?)";
			if (addReportSource.get("postType").equals("board"))
				sql = "INSERT INTO REPORTCOUNT VALUES(?, SYSDATE, ?, ?, null)";
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, Integer.parseInt(addReportSource.get("userKey")));
			pstmt.setString(2, addReportSource.get("reason"));
			pstmt.setInt(3, Integer.parseInt(addReportSource.get("postKey")));
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}

	public int removeReport(HashMap<String, String> removeCountSource) {
		try {
			con = ds.getConnection();

			String sql = "";
			if (removeCountSource.get("postType").equals("word"))
				sql = "DELETE FROM REPORTCOUNT WHERE USER_KEY = ? AND WORD_KEY = ?";
			if (removeCountSource.get("postType").equals("board"))
				sql = "DELETE FROM REPORTCOUNT WHERE USER_KEY = ? AND BOARD_KEY = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(removeCountSource.get("userKey")));
			pstmt.setInt(2, Integer.parseInt(removeCountSource.get("postKey")));
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}

	public boolean isCount(int wordKey) {
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select * from count where word_key = ?");
			pstmt.setInt(1, wordKey);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public boolean isReport(int wordKey) {
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select * from reportcount where word_key = ?");
			pstmt.setInt(1, wordKey);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public int deleteCountAllWord(int wordkey) {
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("DELETE FROM COUNT WHERE WORD_KEY = ?");
			pstmt.setInt(1, wordkey);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			return 0;
		}

		finally {
			close();
		} // finally end
		return 1;
	}

	public int deleteReportAllWord(int wordkey) {
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("DELETE FROM REPORTCOUNT WHERE WORD_KEY = ?");
			pstmt.setInt(1, wordkey);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			return 0;
		}

		finally {
			close();
		} // finally end
		return 1;
	}

	public boolean isCountBoard(int boardKey) {
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select * from count where board_key = ?");
			pstmt.setInt(1, boardKey);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public boolean isReportBoard(int BoardKey) {
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select * from reportcount where Board_key = ?");
			pstmt.setInt(1, BoardKey);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public int deleteCountAllBoard(int boardkey) {
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("DELETE FROM COUNT WHERE BOARD_KEY = ?");
			pstmt.setInt(1, boardkey);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}

}
