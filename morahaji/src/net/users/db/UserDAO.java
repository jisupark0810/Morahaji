package net.users.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	DataSource ds;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	int result;

	public UserDAO() {
		try {
			Context init = new InitialContext();
			ds = (DataSource) init.lookup("java:comp/env/jdbc/OracleDB");
		} catch (Exception ex) {
			System.err.println("UserDAO DB ���� ���� : " + ex);
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

	public int insert(USERS u) throws SQLException { // ȸ������
		try {

			result = isID(u.getUSER_ID());
			if (result == 1) { // ID�� ���� ���
				return -1;
			} else {
				con = ds.getConnection();

				pstmt = con.prepareStatement(
						"INSERT INTO USERS (USER_KEY, USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_AGERANGE, USER_STATUS) VALUES((select nvl(max(USER_KEY),0) from USERS)+1, ?, ?, ?, ?, ?, '1')");
				pstmt.setString(1, u.getUSER_ID());
				pstmt.setString(2, u.getUSER_NAME());
				pstmt.setString(3, u.getUSER_EMAIL());
				pstmt.setString(4, u.getUSER_PASSWORD());
				pstmt.setString(5, u.getUSER_AGERANGE());
				result = pstmt.executeUpdate();

				insertlastAct(u.getUSER_ID());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}// insert end

	public int insertlastAct(String id) throws SQLException { // ������ Ȱ���� �߰� - ȸ������ ��
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(
					"INSERT INTO lastActivity VALUES((select USER_KEY from USERS where USER_ID = ?), SYSDATE)");
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}// insert end

	public int isID(String id) throws SQLException { // ���̵� �ִ��� Ȯ�� - ȸ������ ��
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("SELECT USER_ID, USER_EMAIL FROM users WHERE USER_ID = ? ");
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 1;
			} else {
				result = -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}// isID end

	public int isEmail(String email) throws SQLException { // �̸��� �ִ��� Ȯ�� - ȸ������ ��
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("SELECT USER_ID, USER_EMAIL FROM users WHERE USER_EMAIL = ? ");
			pstmt.setString(1, email);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 1;
			} else {
				result = -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}// isEmail

	public HashMap<String, String> isID(String id, String pw) throws SQLException { // id, pw Ȯ�� - �α��� ��
		HashMap<String, String> results = new HashMap<String, String>();
		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(
					"SELECT U.USER_ID, U.USER_EMAIL, U.USER_PASSWORD, U.USER_KEY, U.USER_NAME, U.USER_STATUS FROM users U WHERE USER_ID = ? OR USER_EMAIL = ? ");
			pstmt.setString(1, id);
			pstmt.setString(2, id);

			rs = pstmt.executeQuery();

			if (rs.next()) { // id ����
				if (rs.getString(3).equals(pw)) { // id pw ��ġ
					results.put("result", "1");
					results.put("userId", rs.getString(1));
					results.put("userEmail", rs.getString(2));
					results.put("userKey", rs.getInt(4) + "");
					results.put("userName", rs.getString(5));
					results.put("userStatus", rs.getInt(6) + "");
				} else { // pw ����ġ
					results.put("result", "0");
				}
				if (!rs.getBoolean(6)) { // Ż���� ȸ��
					results.put("result", "-2");
				}
			} else { // id ����
				results.put("result", "-1");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return results;
	}// isID end

	public int findPw(String id, String email) throws SQLException { // ��й�ȣ ã��

//		/*
//		  id ��ġ / email ��ġ = 1
//		  id ��ġ / email ����ġ = -1
//		  id ����ġ / email ��ġ = -2
//		  id ���� / email ���� but ���� �ٸ� ���� = -3
//		  id ���� / email ���� = 0
//		 */

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("SELECT USER_ID, USER_EMAIL FROM users WHERE USER_ID = ? OR USER_EMAIL = ? ");
			pstmt.setString(1, id);
			pstmt.setString(2, email);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				String getId = rs.getString(1);
				String getEmail = rs.getString(2);
				System.out.println("row : " + rs.getRow());
				System.out.println("getId : " + getId);

				if (!getId.isEmpty()) { // id�� Email ����

					if (rs.getRow() > 1) { // row ���� 1���� ū ��� = ���� 2�� �̻� ����
											// => id, Email�� ���� ������ ���� �ٸ� ������� ���� ���
						result = -3;
						return result;
					} else { // ID, Email�� ���� �ϰ� ���� 1�� ��
						if (getId.equals(id) && getEmail.equals(email)) {
							// id�� email�� �� �� ��ġ�ϴ� ���
							result = 1;
						} else if (getId.equals(id) && !getEmail.equals(email)) {
							// id�� ��ġ�ϳ� email�� ���� ���� ���
							result = -1;
						} else if (!getId.equals(id) && getEmail.equals(email)) {
							// Email�� ��ġ�ϳ� id�� ���� ���� ���
							result = -2;
						}
					}
				} else { // id, Email �� �� ����
					result = 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}

	public int setPw(String tempPw, String id, String email) throws SQLException { // ���� ������ �ӽ� ��й�ȣ update - ��й�ȣ ã�� ��
		try {
			con = ds.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE USERS ");
			sql.append("SET USER_PASSWORD = ? ");
			sql.append("WHERE USER_ID = ? AND USER_EMAIL = ?");

			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, tempPw);
			pstmt.setString(2, id);
			pstmt.setString(3, email);

			pstmt.executeUpdate();

			con.commit();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}

	public int updateLastAct(int userKey) throws SQLException { // ������ Ȱ���� ������Ʈ - �α��� ��
		try {
			con = ds.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE lastActivity ");
			sql.append("SET LASTACT = SYSDATE ");
			sql.append("WHERE USER_KEY = ? ");

			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, userKey);

			pstmt.executeUpdate();

			con.commit();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}

	public String getLastAct(int userKey) throws SQLException { // ��й�ȣ ã��
		try {
			con = ds.getConnection();
			String lastAct = "";

			pstmt = con.prepareStatement("SELECT LASTACT FROM LASTACTIVITY WHERE USER_KEY = ? ");
			pstmt.setInt(1, userKey);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
				lastAct = dateFormat.format(rs.getDate(1));
				return lastAct;
			} else {
				return "error";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return null;
	}

	public int getCounts(int userKey) {
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("SELECT count(WORD_KEY) FROM WORD WHERE USER_KEY = ? ");
			pstmt.setInt(1, userKey);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			} else {
				result = -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return result;
	}

	public String getProfiles(int userKey) {
		String profilePic = "img/profile.png";
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement("SELECT USER_PROFILEPHOTO FROM USERS WHERE USER_KEY = ? ");
			pstmt.setInt(1, userKey);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString(1) != null && rs.getString(1).length() > 0) {
					profilePic = "boardupload/" + rs.getString(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			close();
		} // finally end
		return profilePic;
	}

	/** ���������� ���� ���� - �ڼ��� */
	public int updateName(int userKey, String userName) {
		int result = 0;
		try {
			con = ds.getConnection();
			String sql = "UPDATE USERS SET USER_NAME=? WHERE USER_KEY=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userName);
			pstmt.setInt(2, userKey);

			result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("������ ���� �Ϸ�Ǿ����ϴ�.");
			}
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
			System.out.println("��� ������Ʈ �����Դϴ�.");
		} finally {
			close();
		}
		return result;
	}// updateName end

	public int updateEmail(int userKey, String userEmail) {
		int result = 0;
		try {
			con = ds.getConnection();
			String sql = "UPDATE USERS SET USER_EMAIL=? WHERE USER_KEY=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userEmail);
			pstmt.setInt(2, userKey);

			result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("������ ���� �Ϸ�Ǿ����ϴ�.");
			}
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
			System.out.println("��� ������Ʈ �����Դϴ�.");
		} finally {
			close();
		}
		return result;
	}// updateEmail end

	public USERS getDetailForUpdate(int userKey) {
		USERS u = new USERS();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select * from USERS where USER_KEY = ? ");
			pstmt.setInt(1, userKey);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				u.setUSER_KEY(userKey);
				u.setUSER_ID(rs.getString("USER_ID"));
				u.setUSER_PASSWORD(rs.getString("USER_PASSWORD"));
				u.setUSER_NAME(rs.getString("USER_NAME"));
				u.setUSER_EMAIL(rs.getString("USER_EMAIL"));
				u.setUSER_AGERANGE(rs.getString("USER_AGERANGE"));
				if (rs.getString("USER_PROFILEPHOTO") != null)
					u.setUSER_PROFILEPHOTO(rs.getString("USER_PROFILEPHOTO"));
				else
					u.setUSER_PROFILEPHOTO("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		System.out.println(u);
		return u;
	}// getDetailForUpdate

	public int updateAge(int userKey, String newAge) {
		int result = 0;
		try {
			con = ds.getConnection();
			String sql = "UPDATE USERS SET USER_AGERANGE=? WHERE USER_KEY=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newAge);
			pstmt.setInt(2, userKey);
			result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("������ ���� �Ϸ�Ǿ����ϴ�.");
			}
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
			System.out.println("��� ������Ʈ �����Դϴ�.");
		} finally {
			close();
		}
		return result;
	}// updateAge end

	// ���� Ż��
	public int delete(int userKey) {
		result = 0;
		try {
			con = ds.getConnection();
			String sql = "UPDATE USERS SET USER_STATUS = '0' WHERE USER_KEY = ?";// ������ ������
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userKey);
			result = pstmt.executeUpdate();

			if (result == 1) {
				System.out.println("ȸ�� Ż�� �Ϸ�Ǿ����ϴ�.");
			}
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
			System.out.println("ȸ�� Ż�� �����Դϴ�.");
		} finally {
			close();
		}
		return result;
	}// delete end

	public int updatePass(int userKey, String newPass) {
		int result = 0;
		try {
			con = ds.getConnection();
			String sql = "UPDATE USERS SET USER_PASSWORD=? WHERE USER_KEY=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newPass);
			pstmt.setInt(2, userKey);
			result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("������ ���� �Ϸ�Ǿ����ϴ�.");
			}
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
			System.out.println("��� ������Ʈ �����Դϴ�.");
		} finally {
			close();
		}
		return result;
	}// updatePass end

	public boolean photoUpdate(int userKey, String filename) {
		String sql = "UPDATE USERS SET USER_PROFILEPHOTO=? WHERE USER_KEY = ?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, filename);
			pstmt.setInt(2, userKey);

			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	/** ���������� ���� �� - �ڼ��� */
	/** ���� ���� ���� **/
	public int getListCount() {
		int x = 0;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select count(*) from users where user_id != 'admin'");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("getListCount() ����: " + ex);
		} finally {
			close();
		}

		return x;
	}

	public List<USERS> getList(int page, int limit) {

		List<USERS> list = new ArrayList<USERS>();

		try {
			con = ds.getConnection();
			String sql = "select *  " + "from (select b.*, rownum rnum " + "		from(select * from users "
					+ "			where user_id != 'admin' " + "			order by user_id)b " + " )"
					+ " where rnum>=? and rnum<=? ";

			pstmt = con.prepareStatement(sql);

			int startrow = (page - 1) * limit + 1;
			int endrow = startrow + limit - 1;

			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				USERS u = new USERS();
				u.setUSER_ID(rs.getString("USER_ID"));
				u.setUSER_PASSWORD(rs.getString("USER_PASSWORD"));
				u.setUSER_NAME(rs.getString("USER_NAME"));
				u.setUSER_EMAIL(rs.getString("USER_EMAIL"));
				u.setUSER_AGERANGE(rs.getString("USER_AGERANGE"));
				u.setUSER_STATUS(rs.getInt("USER_STATUS"));
				u.setRNUM(rs.getInt("rnum"));
				list.add(u);

				System.out.println("==" + u);
			}

		} catch (Exception ex) {
			System.out.println("getBoardList()����: " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}

		return list;
	}

	public int getListCount(String field, String value) {
		int x = 0;
		try {
			con = ds.getConnection();
			String sql = "select count(*) from users " + "where user_id !='admin' " + "and " + field + " like ? ";
			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + value + "%");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception ex) {
			System.out.println("getListCount()����: " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}

		return x;
	}

	public List<USERS> getList(String field, String value, int page, int limit) {
		List<USERS> list = new ArrayList<USERS>();

		try {
			con = ds.getConnection();

			String sql = "select * " + "from (select b.*, rownum rnum " + "from (select * from users "
					+ "  	where user_id !='admin' " + "			and " + field + " like ? "
					+ "				order by user_id) b" + "				)" + "		where rnum between ? and ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + value + "%");
			int startrow = (page - 1) * limit + 1;
			int endrow = startrow + limit - 1;

			pstmt.setInt(2, startrow);
			pstmt.setInt(3, endrow);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				USERS u = new USERS();
				u.setUSER_ID(rs.getString("USER_ID"));
				u.setUSER_PASSWORD(rs.getString("USER_PASSWORD"));
				u.setUSER_NAME(rs.getString("USER_NAME"));
				u.setUSER_EMAIL(rs.getString("USER_EMAIL"));
				u.setUSER_AGERANGE(rs.getString("USER_AGERANGE"));
				u.setUSER_STATUS(Integer.parseInt(rs.getString("USER_STATUS")));
				u.setRNUM(rs.getInt("rnum"));
				list.add(u);
				System.out.println(u);
			}
		} catch (Exception ex) {
			System.out.println("getBoardList()����: " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}

		return list;
	}

	public int delete(String id) {
		int result = 0;
		try {
			con = ds.getConnection();
			String sql = "update users set user_status = 0 where user_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}

	public USERS member_info(String id) {
		USERS u = null;

		try {
			con = ds.getConnection();
			String sql = "select * from users where user_id = ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				u = new USERS();
				u.setUSER_ID(rs.getString("USER_ID"));
				u.setUSER_PASSWORD(rs.getString("USER_PASSWORD"));
				u.setUSER_NAME(rs.getString("USER_NAME"));
				u.setUSER_EMAIL(rs.getString("USER_EMAIL"));
				u.setUSER_AGERANGE(rs.getString("USER_AGERANGE"));
				u.setUSER_STATUS(Integer.parseInt(rs.getString("USER_STATUS")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return u;
	}

	public int update(String id) {
		int result = 0;
		try {
			con = ds.getConnection();
			String sql = "update users set user_status=1 where user_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}

	/** ���� �� **/

	/** ���� **/
	public String getUserName(int key, int type) {
		String result = "";
		try {
			con = ds.getConnection();
			if (type == 0) {// �̸��� ������ ���
				String sql = "SELECT user_name from users where user_key=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, key);
				rs = pstmt.executeQuery();
			} else if (type == 1) {
				String sql = "SELECT user_name from users where user_key=(select user_key from reply where reply_key = ?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, key);
				rs = pstmt.executeQuery();
			}
			if (rs.next()) {
				result = rs.getString(1);
			}
		} catch (Exception ex) {
			System.out.println("getMyListCount() ���� : " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	public boolean isReportBoard(int boardKey, int userKey) {
		try {
			con = ds.getConnection();
			String sql = "select * from reportcount where user_key = ? AND board_key = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userKey);
			pstmt.setInt(2, boardKey);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception ex) {
			System.out.println("getMyListCount() ���� : " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
}
