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
			System.out.println("getListCount() 에러 : " + ex);
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
				System.out.println("이거"+num);
				JsonObject object = new JsonObject();
				object.addProperty("reply_key", rs.getInt("reply_key"));
				object.addProperty("user_key", rs.getString("user_key"));
				object.addProperty("reply_content", rs.getString("reply_content"));
				object.addProperty("user_name", userDAO.getUserName(rs.getInt("user_key"), 0));//댓글을 쓴 사람의 이
				String origin_user_name = "";
				if(num != 0) {	//대댓글 - 원글쓴이의 이름을 가져옴
					origin_user_name = userDAO.getUserName(rs.getInt("reply_re_ref") , 1);
				}
				object.addProperty("origin_user_name", origin_user_name);
				//날짜 오늘이면 시간표시 아니면 날짜 표시
				SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
				Date time = new Date();
				String javadate = format.format(time).toString().substring(0,10);
				String sqldate = rs.getString("reply_date").substring(0, 10);
				String date = "";
				if(javadate.equals(sqldate)) {
					date = rs.getString("reply_date").substring(11, 16);
					if(Integer.parseInt(format.format(time).toString().substring(11,13))-Integer.parseInt(rs.getString("reply_date").substring(11, 13))==0) {
						int imsi = Integer.parseInt(format.format(time).toString().substring(14,16))-Integer.parseInt(rs.getString("reply_date").substring(14, 16));
						date = Integer.toString(imsi)+"분전";
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
			System.out.println("getReplyList() 에러 : " + ex);
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
				System.out.println("원문댓글 작성 완료");
		} catch (Exception ex) {
			System.out.println("replyInsert() 에러 : " + ex);
			ex.printStackTrace();
		} finally {
			close();
		}
		return result;
	}

	public int replyReplyInsert(Reply rp) throws Exception {
		// board 테이블의 board_num 필드의 최대값을 구해와서 글을 등록할 때
		// 글 번호를 순차적으로 지정하기 위함입니다.
		// 또한 DB에 저장한 후 다시 보여주기위해 board_num 필드의 값을 리턴합니다.
		String reply_max_sql = "select max(reply_key) from reply";
		String sql = "";
		int success = 0;
		int reply_key = 0;
		/*
		 * 답변을 할 원문 글 그룹 번호입니다. 답변을 달게 되면 답변 글은 이 번호와 같은 관련글 번호를 갖게 처리되면서 같은 그룹에 속하게
		 * 됩니다. 글 목록에서 보여줄 때 하나의 그룹으로 묶여서 출력됩니다.
		 */
		int re_ref = rp.getREPLY_RE_REF();
		System.out.println("re_Ref=" + re_ref);
		/*
		 * 답글의 깊이를 의미합니다. 원문에 대한 답글이 출력될 때 한 번 들여쓰기 처리가 되고 답글에 대한 답글은 들여쓰기가 두 번 처리되게
		 * 합니다. 원문인 경우에는 이 값이 0이고 원문의 답글은 1, 답글의 답글은 2가 됩니다.
		 */
		int re_lev = rp.getREPLY_RE_LEV();

		// 같은 관련 글 중에서 해당 글이 출력되는 순서입니다.
		int re_seq = rp.getREPLY_RE_SEQ();
		System.out.println("re_Ref" + re_ref);
		System.out.println("re_seq" + re_seq);
		try {
			conn = ds.getConnection();
			// 트랜잭션을 이용하기 위해서 setAutoCommit을 false로 설정합니다.
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(reply_max_sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				reply_key = rs.getInt(1) + 1;
			System.out.println(reply_key);
			// BOARD_RE_REF, BOARD_RE,SEQ 값을 확인하여 원문 글에
			// 다른 답글이 있으면
			// 다른 답글들의 BOARD_RE_SEQ값을 1씩 증가시킵니다.
			// 현재 글을 다른 답글보다 앞에 출력되게 하기 위해서입니다.
			sql = "UPDATE REPLY SET REPLY_RE_SEQ = REPLY_RE_SEQ + 1 WHERE REPLY_RE_REF = ? AND REPLY_RE_SEQ > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, re_ref);
			pstmt.setInt(2, re_seq);
			int result1 = pstmt.executeUpdate();
			// 등록할 답변 글의 BOARD_RE_LEV, BOARD_RE_SEQ 값을 원문 글보다 1씩
			// 증가시킵니다.
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
				conn.commit(); // commit합니다.
				conn.setAutoCommit(true);// 다시 true로 설정합니다.
				success = 1;
			} else {
				conn.rollback();
				System.out.println("rollback()");
			}
		} catch (SQLException ex) {
			System.out.println("replyReplyInsert() 에러 : " + ex);
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
	}// boardReply() 메서드 end

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
				System.out.println("데이터 수정 완료");
		} catch (Exception ex) {
			System.out.println("replyUpdate() 에러 : " + ex);
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
