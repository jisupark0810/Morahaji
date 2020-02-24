package net.reply.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.users.db.UserDAO;

public class ReplyDAO {
	private DataSource ds;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public ReplyDAO() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/OracleDB");
		} catch (NamingException e) {
			e.printStackTrace();
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
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// close end
	public int getListCount(int board_key) {
		int x = 0;
		try {
			conn = ds.getConnection();
			String sql = "select count(*) from reply where board_key=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_key);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = Integer.parseInt(rs.getString(1));
			}
		} catch (Exception ex) {
			System.out.println("getListCount() ���� : " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return x;
	}

	public JsonArray getReplyList(int BOARD_KEY) {
		UserDAO userDAO = new  UserDAO(); 
		String sql = "select reply_key, user_key, reply_content, TO_CHAR(reply_date,'YYYY-MM-DD HH24:Mi:SS') reply_date, board_key, reply_re_ref, reply_re_lev, reply_re_seq from reply where board_key = ? order by REPLY_RE_REF asc, REPLY_RE_SEQ asc";
		JsonArray array = new JsonArray();
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, BOARD_KEY);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int num = rs.getInt("reply_re_lev");
				System.out.println("�̰�"+num);
				JsonObject object = new JsonObject();
				object.addProperty("reply_key", rs.getInt("reply_key"));
				object.addProperty("user_key", rs.getString("user_key"));
				object.addProperty("reply_content", rs.getString("reply_content"));
				object.addProperty("user_name", userDAO.getUserName(rs.getInt("user_key"), 0));//����� �� ����� ��
				String origin_user_name = "";
				if(num != 0) {	//���� - ���۾����� �̸��� ������
					origin_user_name = userDAO.getUserName(rs.getInt("reply_re_ref") , 1);
				}
				object.addProperty("origin_user_name", origin_user_name);
				//��¥ �����̸� �ð�ǥ�� �ƴϸ� ��¥ ǥ��
				SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
				Date time = new Date();
				String javadate = format.format(time).toString().substring(0,10);
				String sqldate = rs.getString("reply_date").substring(0, 10);
				String date = "";
				if(javadate.equals(sqldate)) {
					date = rs.getString("reply_date").substring(11, 16);
					if(Integer.parseInt(format.format(time).toString().substring(11,13))-Integer.parseInt(rs.getString("reply_date").substring(11, 13))==0) {
						int imsi = Integer.parseInt(format.format(time).toString().substring(14,16))-Integer.parseInt(rs.getString("reply_date").substring(14, 16));
						date = Integer.toString(imsi)+"����";
					}
				}
				else {
					date =sqldate;
				}
				object.addProperty("reply_date", date);
				object.addProperty("board_key", rs.getInt("board_key"));
				object.addProperty("reply_re_ref", rs.getInt("reply_re_ref"));
				object.addProperty("reply_re_lev", rs.getInt("reply_re_lev"));
				object.addProperty("reply_re_seq", rs.getInt("reply_re_seq"));
				array.add(object);
			}
		} catch (Exception ex) {
			System.out.println("getReplyList() ���� : " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return array;
	}

	public int replyInsert(Reply rp) throws Exception {
		int result = 0;
		try {
			conn = ds.getConnection();
			String sql = "insert into reply values((select nvl(max(reply_key),0)+1 from reply), ?, ?, ?, sysdate, (select nvl(max(reply_key),0)+1 from reply), 0, 0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rp.getUSER_KEY());
			pstmt.setInt(2, rp.getBOARD_KEY());
			pstmt.setString(3, rp.getREPLY_CONTENT());
			result = pstmt.executeUpdate();
			if (result == 1)
				System.out.println("������� �ۼ� �Ϸ�");
		} catch (Exception ex) {
			System.out.println("replyInsert() ���� : " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	public int replyReplyInsert(Reply rp) throws Exception {
		// board ���̺��� board_num �ʵ��� �ִ밪�� ���ؿͼ� ���� ����� ��
		// �� ��ȣ�� ���������� �����ϱ� �����Դϴ�.
		// ���� DB�� ������ �� �ٽ� �����ֱ����� board_num �ʵ��� ���� �����մϴ�.
		String reply_max_sql = "select max(reply_key) from reply";
		String sql = "";
		int success = 0;
		int reply_key = 0;
		/*
		 * �亯�� �� ���� �� �׷� ��ȣ�Դϴ�. �亯�� �ް� �Ǹ� �亯 ���� �� ��ȣ�� ���� ���ñ� ��ȣ�� ���� ó���Ǹ鼭 ���� �׷쿡 ���ϰ�
		 * �˴ϴ�. �� ��Ͽ��� ������ �� �ϳ��� �׷����� ������ ��µ˴ϴ�.
		 */
		int re_ref = rp.getREPLY_RE_REF();
		System.out.println("re_Ref=" + re_ref);
		/*
		 * ����� ���̸� �ǹ��մϴ�. ������ ���� ����� ��µ� �� �� �� �鿩���� ó���� �ǰ� ��ۿ� ���� ����� �鿩���Ⱑ �� �� ó���ǰ�
		 * �մϴ�. ������ ��쿡�� �� ���� 0�̰� ������ ����� 1, ����� ����� 2�� �˴ϴ�.
		 */
		int re_lev = rp.getREPLY_RE_LEV();

		// ���� ���� �� �߿��� �ش� ���� ��µǴ� �����Դϴ�.
		int re_seq = rp.getREPLY_RE_SEQ();
		System.out.println("re_Ref" + re_ref);
		System.out.println("re_seq" + re_seq);
		try {
			conn = ds.getConnection();
			// Ʈ������� �̿��ϱ� ���ؼ� setAutoCommit�� false�� �����մϴ�.
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(reply_max_sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				reply_key = rs.getInt(1) + 1;
			System.out.println(reply_key);
			// BOARD_RE_REF, BOARD_RE,SEQ ���� Ȯ���Ͽ� ���� �ۿ�
			// �ٸ� ����� ������
			// �ٸ� ��۵��� BOARD_RE_SEQ���� 1�� ������ŵ�ϴ�.
			// ���� ���� �ٸ� ��ۺ��� �տ� ��µǰ� �ϱ� ���ؼ��Դϴ�.
			sql = "UPDATE REPLY SET REPLY_RE_SEQ = REPLY_RE_SEQ + 1 WHERE REPLY_RE_REF = ? AND REPLY_RE_SEQ > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, re_ref);
			pstmt.setInt(2, re_seq);
			int result1 = pstmt.executeUpdate();
			// ����� �亯 ���� BOARD_RE_LEV, BOARD_RE_SEQ ���� ���� �ۺ��� 1��
			// ������ŵ�ϴ�.
			re_seq = re_seq + 1;
			re_lev = re_lev + 1;
			sql = "insert into reply values(?,?,?,?,sysdate,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply_key);
			pstmt.setInt(2, rp.getUSER_KEY());
			pstmt.setInt(3, rp.getBOARD_KEY());
			pstmt.setString(4, rp.getREPLY_CONTENT());
			pstmt.setInt(5, re_ref);
			pstmt.setInt(6, re_lev);
			pstmt.setInt(7, re_seq);
			int result2 = pstmt.executeUpdate();
			if (result1 >= 0 && result2 == 1) {
				conn.commit(); // commit�մϴ�.
				conn.setAutoCommit(true);// �ٽ� true�� �����մϴ�.
				success = 1;
			} else {
				conn.rollback();
				System.out.println("rollback()");
			}
		} catch (SQLException ex) {
			System.out.println("replyReplyInsert() ���� : " + ex);
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		} finally {
			close();
		}
		return success;
	}// boardReply() �޼��� end

	public int replyUpdate(Reply rp) throws Exception {
		int result = 0;
		try {
			conn = ds.getConnection();
			String sql = "update reply set reply_content=? where reply_key=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, rp.getREPLY_CONTENT());
			pstmt.setInt(2, rp.getREPLY_KEY());
			result = pstmt.executeUpdate();
			if (result == 1)
				System.out.println("������ ���� �Ϸ�");
		} catch (Exception ex) {
			System.out.println("replyUpdate() ���� : " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return result;

	}

	public boolean replydelete(int re_ref, int re_seq, int re_lev) {
		boolean result = false;
		String sql = "delete reply "
				+ "where reply_re_ref=? and reply_re_lev>=? and reply_re_seq>=? and reply_re_seq<(nvl((select min(reply_re_seq) "
				+ "from reply "
				+ "where reply_re_lev=? and reply_re_seq>? and reply_re_ref=?), (select max(reply_re_seq)+1 "
				+ "from reply " + "where reply_re_ref=?)))";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, re_ref);
			pstmt.setInt(2, re_lev);
			pstmt.setInt(3, re_seq);
			pstmt.setInt(4, re_lev);
			pstmt.setInt(5, re_seq);
			pstmt.setInt(6, re_ref);
			pstmt.setInt(7, re_ref);
			int result1 = pstmt.executeUpdate();
			if (result1 > 0) {
				result = true;
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} finally {
			close();
		}
		return result;
	}

}
