package net.board.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.report.db.REPORTCOUNT;
import net.users.db.UserDAO;
import net.word.db.WORD;

public class BoardDAO {
   private DataSource ds;
   private Connection conn;
   private PreparedStatement pstmt;
   private ResultSet rs;
   UserDAO userdao = new UserDAO();

   public BoardDAO() {
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
   }

   // �����Խ��� �� ��ȸ�� ����====================================
   public void setReadCountUpdate(int board_key) {
      try {
         conn = ds.getConnection();
         String sql = "UPDATE BOARD " + "SET BOARD_READCOUNT=BOARD_READCOUNT+1 " + "WHERE BOARD_KEY=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, board_key);
         pstmt.executeUpdate();
      } catch (Exception ex) {
         System.out.println("setReadCountUpdate() ���� : " + ex);
         ex.printStackTrace();
      } finally {
    	  close();
      }
   }

   // �����Խ��� �� �߰�====================================
   public boolean boardInsert(int user_key, BoardBean boarddata) {
      boolean result = true;
      try {
         conn = ds.getConnection();
         String sql = "INSERT INTO BOARD "
               + "(BOARD_KEY, USER_KEY, BOARD_TITLE, BOARD_CONTENT, BOARD_GIF, BOARD_DATE) "
               + "VALUES((SELECT NVL(MAX(BOARD_KEY),0)+1 FROM BOARD),?,?,?,?,SYSDATE)";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, user_key);
         pstmt.setString(2, boarddata.getBOARD_TITLE());
         pstmt.setString(3, boarddata.getBOARD_CONTENT());
         pstmt.setString(4, boarddata.getBOARD_GIF());
         int result2 = pstmt.executeUpdate();
         if (result2 == 1) {
            result = true;
         }
      } catch (Exception ex) {
         System.out.println("boardInsert() ����: " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return result;
   }

   // �����Խ��� �� ����====================================
   public boolean boardDelete(int board_key) {
      boolean result = true;
      try {
         conn = ds.getConnection();
         String sql = "DELETE BOARD " + "WHERE BOARD_KEY=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, board_key);
         int result2 = pstmt.executeUpdate();
         if (result2 == 1) {
            result = true;
         }
      } catch (Exception ex) {
         System.out.println("boardDelete() ����: " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return result;
   }

   // �����Խ��� �� ����====================================
   public boolean boardModify(BoardBean boarddata) {
      boolean result = true;
      try {
         conn = ds.getConnection();
         String sql = "UPDATE BOARD " + "SET BOARD_TITLE=?, BOARD_CONTENT=?, BOARD_GIF=? " + "WHERE BOARD_KEY=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, boarddata.getBOARD_TITLE());
         pstmt.setString(2, boarddata.getBOARD_CONTENT());
         pstmt.setString(3, boarddata.getBOARD_GIF());
         pstmt.setInt(4, boarddata.getBOARD_KEY());
         int result2 = pstmt.executeUpdate();
         if (result2 == 1) {
            result = true;
         }
      } catch (Exception ex) {
         System.out.println("boardModify() ����: " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return result;
   }

   // �����Խ��� �� ��====================================
   public int getListCount() {
      int result = 0;
      try {
         conn = ds.getConnection();
         String sql = "SELECT COUNT(*)" + " FROM BOARD";
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            result = Integer.parseInt(rs.getString(1));
         }
      } catch (Exception ex) {
         System.out.println("getListCount() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return result;
   }

   // �����Խ��� '����' �� ��====================================
   public int getMyListCount(int user_key) {
      int result = 0;
      try {
         conn = ds.getConnection();
         String sql = "SELECT COUNT(*)" + "FROM BOARD " + "WHERE USER_KEY=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, user_key);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            result = Integer.parseInt(rs.getString(1));
         }
      } catch (Exception ex) {
         System.out.println("getMyListCount() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return result;
   }

   // �����Խ��� ��ü �� ��������====================================
   public List<BoardBean> getBoardList(int page, int limit) {
      List<BoardBean> boardbean = new ArrayList<BoardBean>();
      try {
         conn = ds.getConnection();
         String sql = "SELECT A.*, RNUM FROM (SELECT  ROWNUM RNUM, BOARD_KEY, USER_KEY, BOARD_TITLE, TO_CHAR(BOARD_DATE,'YYYY-MM-DD HH24:Mi:SS') BOARD_DATE, BOARD_READCOUNT, NVL(COMMENTCOUNT,0) REPLYCOUNT, NVL(LIKECOUNT,0) LIKECOUNT FROM (SELECT * FROM (SELECT * FROM BOARD LEFT JOIN (SELECT BOARD_KEY, COUNT(*) LIKECOUNT FROM COUNT GROUP BY BOARD_KEY) C USING (BOARD_KEY) LEFT JOIN (SELECT BOARD_KEY, COUNT(*) COMMENTCOUNT FROM REPLY GROUP BY BOARD_KEY) C USING (BOARD_KEY)) ORDER BY BOARD_DATE DESC)) A WHERE RNUM>=? AND RNUM<=?";
         int startrow = (page - 1) * limit + 1;
         int endrow = startrow + limit - 1;
         System.out.println("startrow =  " + startrow + "endrow = " + endrow);
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, startrow);
         pstmt.setInt(2, endrow);
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
            System.out.println("rs.next = ���� ");
            BoardBean bb = new BoardBean();
            bb.setBOARD_KEY(rs.getInt("BOARD_KEY"));
            bb.setUSER_KEY(rs.getInt("USER_KEY"));
            bb.setUSER_NAME(userdao.getUserName(rs.getInt("USER_KEY"), 0));
            bb.setBOARD_TITLE(rs.getString("BOARD_TITLE"));
            // ��¥ �����̸� �ð�ǥ�� �ƴϸ� ��¥ ǥ��
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            String javadate = format.format(time).toString().substring(0, 10);
            String sqldate = rs.getString("BOARD_DATE").substring(0, 10);
            int javahour=Integer.parseInt(format.format(time).toString().substring(11, 13));
            int sqlhour=Integer.parseInt(rs.getString("board_date").substring(11, 13));
            int javaminute = Integer.parseInt(format.format(time).toString().substring(14, 16));
            int sqlminute = Integer.parseInt(rs.getString("board_date").substring(14, 16));
            String date = "";
            if (javadate.equals(sqldate)) {
               if(javahour==sqlhour) {
                  int imsi = javaminute - sqlminute;
                  date = Integer.toString(imsi) + "����";
               }else if(javahour-sqlhour==1){
                  int imsi = javaminute - sqlminute;
                  if(imsi<=0) {
                     imsi = 60+imsi;
                     date = Integer.toString(imsi) + "����";
                  }else {
                     
                     date = rs.getString("BOARD_DATE").substring(11, 16);
                  }
               }
            }else {
               date = sqldate;
            }
            bb.setBOARD_DATE(date);
            bb.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
            bb.setBOARD_REPLYCOUNT(rs.getInt("REPLYCOUNT"));
            bb.setBOARD_LIKECOUNT(rs.getInt("LIKECOUNT"));
            boardbean.add(bb);
         }
      } catch (Exception ex) {
         System.out.println("getBoardList() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return boardbean;
   }

   // �����Խ��� '����' ��ü �� ��������====================================
   public List<BoardBean> getMyBoardList(int page, int limit, int user_key) {
      List<BoardBean> boardbean = new ArrayList<BoardBean>();
      try {
         conn = ds.getConnection();
         String sql = "SELECT A.*, RNUM FROM (SELECT ROWNUM RNUM, BOARD_KEY, USER_KEY, BOARD_TITLE, TO_CHAR(BOARD_DATE,'YYYY-MM-DD HH24:Mi:SS') BOARD_DATE, BOARD_READCOUNT, NVL(COMMENTCOUNT,0) REPLYCOUNT, NVL(LIKECOUNT,0) LIKECOUNT FROM (SELECT * FROM (SELECT * FROM BOARD LEFT JOIN (SELECT BOARD_KEY, COUNT(*) LIKECOUNT FROM COUNT GROUP BY BOARD_KEY) C USING (BOARD_KEY) LEFT JOIN (SELECT BOARD_KEY, COUNT(*) COMMENTCOUNT FROM REPLY GROUP BY BOARD_KEY) C USING (BOARD_KEY)) ORDER BY BOARD_DATE DESC)) A WHERE RNUM>=? AND RNUM<=? AND USER_KEY=?";
         int startrow = (page - 1) * limit + 1;
         int endrow = startrow + limit - 1;
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, startrow);
         pstmt.setInt(2, endrow);
         pstmt.setInt(3, user_key);
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
            BoardBean bb = new BoardBean();
            bb.setBOARD_KEY(rs.getInt("BOARD_KEY"));
            bb.setUSER_KEY(rs.getInt("USER_KEY"));
            bb.setUSER_NAME(userdao.getUserName(rs.getInt("USER_KEY"), 0));
            bb.setBOARD_TITLE(rs.getString("BOARD_TITLE"));
         // ��¥ �����̸� �ð�ǥ�� �ƴϸ� ��¥ ǥ��
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            String javadate = format.format(time).toString().substring(0, 10);
            String sqldate = rs.getString("BOARD_DATE").substring(0, 10);
            int javahour=Integer.parseInt(format.format(time).toString().substring(11, 13));
            int sqlhour=Integer.parseInt(rs.getString("board_date").substring(11, 13));
            int javaminute = Integer.parseInt(format.format(time).toString().substring(14, 16));
            int sqlminute = Integer.parseInt(rs.getString("board_date").substring(14, 16));
            String date = "";
            if (javadate.equals(sqldate)) {
               if(javahour==sqlhour) {
                  int imsi = javaminute - sqlminute;
                  date = Integer.toString(imsi) + "����";
               }else if(javahour-sqlhour==1){
                  int imsi = javaminute - sqlminute;
                  if(imsi<=0) {
                     imsi = 60+imsi;
                     date = Integer.toString(imsi) + "����";
                  }else {
                     
                     date = rs.getString("BOARD_DATE").substring(11, 16);
                  }
               }
            }else {
               date = sqldate;
            }
            bb.setBOARD_DATE(date);
            bb.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
            bb.setBOARD_REPLYCOUNT(rs.getInt("REPLYCOUNT"));
            bb.setBOARD_LIKECOUNT(rs.getInt("LIKECOUNT"));
            boardbean.add(bb);
         }
      } catch (Exception ex) {
         System.out.println("getmyBoardList() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return boardbean;
   }

   // �����Խ��� �� ����====================================
   public BoardBean getDetail(int board_key, int user_key) {
      BoardBean bb = new BoardBean();
      try {
         conn = ds.getConnection();
         String sql = "SELECT BOARD_KEY, BOARD_TITLE, USER_KEY, BOARD_DATE, BOARD_CONTENT, BOARD_GIF "
               + "FROM BOARD " + "WHERE BOARD_KEY=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, board_key);
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
            bb.setUSER_NAME(userdao.getUserName(rs.getInt("USER_KEY"), 0));
            bb.setBOARD_KEY(rs.getInt("BOARD_KEY"));
            bb.setBOARD_TITLE(rs.getString("BOARD_TITLE"));
            bb.setUSER_KEY(rs.getInt("USER_KEY"));
            bb.setBOARD_DATE(rs.getString("BOARD_DATE"));
            bb.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
            bb.setBOARD_GIF(rs.getString("BOARD_GIF"));
            System.out.println("userkey" + user_key);
            if (user_key != 0) {
               boolean imsi = userdao.isReportBoard(rs.getInt("BOARD_KEY"), user_key);
               bb.setREPORT(imsi);
               System.out.println(imsi);
            } else {
               bb.setREPORT(false);
            }
         }
      } catch (Exception ex) {
         System.out.println("getDetail() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return bb;
   }

   // �����Խ��� �˻� ����Ű->�������� �� ��=========================================
   public int getSearchUserKey(String value) {
      int result = 0;
      try {
         conn = ds.getConnection();
         String sql = "select count(user_key) from user where user_name like ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%" + value + "%");
      } catch (Exception ex) {
         System.out.println("getSearchListCount() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return result;
   }

   // �����Խ��� �˻� �� ��=========================================
   public int getSearchListCount(String field, String value) {
      int result = 0;
      try {
         conn = ds.getConnection();
         String sql = "";
         // ����+�������� �˻��� �� ����
         if (field.equals("title+content")) {
            sql = "SELECT COUNT(*) " + "FROM BOARD " + "WHERE BOARD_TITLE LIKE ? OR BOARD_CONTENT LIKE ?";
            // �� �� �˻� ����
         } else if (field.equals("user_name")) {
            sql = "select count(*) from board where user_key in(select user_key from users where user_name like ?)";
         } else {
            sql = "SELECT COUNT(*) FROM BOARD WHERE " + field + " LIKE ?";
         }
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%" + value + "%");
         // ����+�������� �˻��� ��
         if (field.equals("title+content"))
            pstmt.setString(2, "%" + value + "%");
         rs = pstmt.executeQuery();
         if (rs.next()) {
            result = Integer.parseInt(rs.getString(1));
         }
      } catch (Exception ex) {
         System.out.println("getSearchListCount() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return result;
   }

   // �����Խ��� �˻� ��=========================================
   public List<BoardBean> getSearchBoardList(String field, String value, int page, int limit) {
      List<BoardBean> boardbean = new ArrayList<BoardBean>();
      try {
         conn = ds.getConnection();
         String sql = "";
         // ����+�������� �˻��� �� ����
         if (field.equals("title+content")) {
            sql = "SELECT A.*, RNUM FROM (SELECT  ROWNUM RNUM, BOARD_KEY, USER_KEY, BOARD_TITLE, TO_CHAR(BOARD_DATE,'YYYY-MM-DD HH24:Mi:SS') BOARD_DATE, BOARD_READCOUNT, NVL(COMMENTCOUNT,0) REPLYCOUNT, NVL(LIKECOUNT,0) LIKECOUNT, BOARD_CONTENT FROM (SELECT * FROM (SELECT * FROM BOARD LEFT JOIN (SELECT BOARD_KEY, COUNT(*) LIKECOUNT FROM COUNT GROUP BY BOARD_KEY) C USING (BOARD_KEY) LEFT JOIN (SELECT BOARD_KEY, COUNT(*) COMMENTCOUNT FROM REPLY GROUP BY BOARD_KEY) C USING (BOARD_KEY)) WHERE BOARD_TITLE LIKE ? OR BOARD_CONTENT LIKE ? ORDER BY BOARD_DATE DESC)) A WHERE RNUM>=? AND RNUM<=?";
         } else if (field.equals("user_name")) {
            sql = "SELECT A.*, RNUM FROM (SELECT  ROWNUM RNUM, BOARD_KEY, USER_KEY, BOARD_TITLE, TO_CHAR(BOARD_DATE,'YYYY-MM-DD HH24:Mi:SS') BOARD_DATE, BOARD_READCOUNT, NVL(COMMENTCOUNT,0) REPLYCOUNT, NVL(LIKECOUNT,0) LIKECOUNT, BOARD_CONTENT FROM (SELECT * FROM (SELECT * FROM BOARD LEFT JOIN (SELECT BOARD_KEY, COUNT(*) LIKECOUNT FROM COUNT GROUP BY BOARD_KEY) USING (BOARD_KEY) LEFT JOIN (SELECT BOARD_KEY, COUNT(*) COMMENTCOUNT FROM REPLY GROUP BY BOARD_KEY) USING (BOARD_KEY) LEFT JOIN (SELECT USER_KEY , USER_NAME FROM USERS) USING (USER_KEY)) WHERE USER_NAME like ? ORDER BY BOARD_DATE DESC)) A WHERE RNUM>=? AND RNUM<=?";
         } else {
            sql = "SELECT A.*, RNUM FROM (SELECT  ROWNUM RNUM, BOARD_KEY, USER_KEY, BOARD_TITLE, TO_CHAR(BOARD_DATE,'YYYY-MM-DD HH24:Mi:SS') BOARD_DATE, BOARD_READCOUNT, NVL(COMMENTCOUNT,0) REPLYCOUNT, NVL(LIKECOUNT,0) LIKECOUNT, BOARD_CONTENT FROM (SELECT * FROM (SELECT * FROM BOARD LEFT JOIN (SELECT BOARD_KEY, COUNT(*) LIKECOUNT FROM COUNT GROUP BY BOARD_KEY) C USING (BOARD_KEY) LEFT JOIN (SELECT BOARD_KEY, COUNT(*) COMMENTCOUNT FROM REPLY GROUP BY BOARD_KEY) C USING (BOARD_KEY)) WHERE "
                  + field + " like ? ORDER BY BOARD_DATE DESC)) A WHERE RNUM>=? AND RNUM<=?";
         }
         int startrow = (page - 1) * limit + 1;
         int endrow = startrow + limit - 1;
         pstmt = conn.prepareStatement(sql);
         if (field.equals("title+content")) {
            pstmt.setString(1, "%" + value + "%");
            pstmt.setString(2, "%" + value + "%");
            pstmt.setInt(3, startrow);
            pstmt.setInt(4, endrow);
         } else {
            pstmt.setString(1, "%" + value + "%");
            pstmt.setInt(2, startrow);
            pstmt.setInt(3, endrow);
         }
         rs = pstmt.executeQuery();
         while (rs.next()) {
            BoardBean bb = new BoardBean();
            bb.setBOARD_KEY(rs.getInt("BOARD_KEY"));
            bb.setUSER_KEY(rs.getInt("USER_KEY"));
            bb.setUSER_NAME(userdao.getUserName(rs.getInt("USER_KEY"), 0));
         // ��¥ �����̸� �ð�ǥ�� �ƴϸ� ��¥ ǥ��
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            String javadate = format.format(time).toString().substring(0, 10);
            String sqldate = rs.getString("BOARD_DATE").substring(0, 10);
            int javahour=Integer.parseInt(format.format(time).toString().substring(11, 13));
            int sqlhour=Integer.parseInt(rs.getString("board_date").substring(11, 13));
            int javaminute = Integer.parseInt(format.format(time).toString().substring(14, 16));
            int sqlminute = Integer.parseInt(rs.getString("board_date").substring(14, 16));
            String date = "";
            if (javadate.equals(sqldate)) {
               if(javahour==sqlhour) {
                  int imsi = javaminute - sqlminute;
                  date = Integer.toString(imsi) + "����";
               }else if(javahour-sqlhour==1){
                  int imsi = javaminute - sqlminute;
                  if(imsi<=0) {
                     imsi = 60+imsi;
                     date = Integer.toString(imsi) + "����";
                  }else {
                     
                     date = rs.getString("BOARD_DATE").substring(11, 16);
                  }
               }
            }else {
               date = sqldate;
            }
            bb.setBOARD_DATE(date);
            bb.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
            bb.setBOARD_REPLYCOUNT(rs.getInt("REPLYCOUNT"));
            bb.setBOARD_LIKECOUNT(rs.getInt("LIKECOUNT"));
            System.out.println("��ȸ��" + rs.getInt("BOARD_READCOUNT"));
            // �� ���� �κ�===============================
            String title = rs.getString("BOARD_TITLE");
            String title1 = "";
            String title2 = "";
            String title3 = "";
            // ����+���� �˻���
            // ���� �˻�� ���� ��
            if (title.indexOf(value) == -1) {
               bb.setBOARD_TITLE_SEARCH(false);
               title2 = title;
               // ���� �˻�� ���� ��
            } else {
               bb.setBOARD_TITLE_SEARCH(true);
               // ����� �˻�� ��ġ�� ��
               if (title.equals(value)) {
                  title2 = title;
                  // ���� �˻�� ���ԵǾ� ���� ��
               } else {
                  StringBuffer tt = new StringBuffer(title);
                  // ���� �˻��� �ܾ� �յڷ� "/" ����
                  tt.insert(title.indexOf(value), "/");
                  tt.insert((title.indexOf(value) + 1) + value.length(), "/");
                  String splittitle = tt.toString();
                  title1 = splittitle.split("/")[0];
                  title2 = splittitle.split("/")[1];
                  title3 = splittitle.split("/")[2];
               }
            }
            bb.setBOARD_TITLE_BEFORE(title1);
            bb.setBOARD_TITLE(title2);
            bb.setBOARD_TITLE_AFTER(title3);
            // �� ���� �κ�===============================
            String content = rs.getString("BOARD_CONTENT");
            String sc = "";
            String f = "";
            String l = "";
            String content1 = "";
            String content2 = "";
            String content3 = "";
            int ff = 0;
            int ll = 0;
            // ����+���� �˻���
            // ���뿡 �˻�� ���� ��
            if (content.indexOf(value) == -1) {
               bb.setBOARD_CONTENT_SEARCH(false);
               // ������ 30�� �̻��̸� ���� ���� ...���
               if (content.length() > 30) {
                  content2 = content.substring(0, 30) + "...";
                  // ������ 30�� ���ϸ� ��ü ���
               } else {
                  content2 = content.substring(0, content.length());
               }
               // ���뿡 �˻�� ���� ��
            } else {
               bb.setBOARD_CONTENT_SEARCH(true);
               // ����� �˻�� ��ġ�� ��
               if (content.equals(value)) {
                  content2 = content;
                  // ���뿡 �˻�� ���Ե� ��
               } else {
                  // ���뿡�� �˻����� �տ� 15���ڰ� ���� ��
                  if (content.indexOf(value) - 15 <= 0) {
                     ff = 0;
                     // ���뿡�� �˻����� �տ� 15���ڰ� ���� ��
                  } else {
                     ff = content.indexOf(value) - 15;
                     f = "...";
                  }
                  // ���뿡�� �˻����� �ڿ� 15���ڰ� ���� ��
                  if (content.indexOf(value) + value.length() + 15 >= content.length()) {
                     ll = content.length();
                     // ���뿡�� �˻����� �տ� 15���ڰ� ���� ��
                  } else {
                     ll = content.indexOf(value) + value.length() + 15;
                     l = "...";
                  }
                  sc = content.substring(ff, ll);
                  StringBuffer sb = new StringBuffer(sc);
                  // �˻����� �յڿ� "/" ����
                  sb.insert(sc.indexOf(value), "/");
                  sb.insert((sc.indexOf(value) + 1) + value.length(), "/");
                  String split = sb.toString();
                  content1 = f + split.split("/")[0];
                  content2 = split.split("/")[1];
                  content3 = split.split("/")[2] + l;
               }
            }
            bb.setBOARD_CONTENT_BEFORE(content1);
            bb.setBOARD_CONTENT(content2);
            bb.setBOARD_CONTENT_AFTER(content3);
            boardbean.add(bb);
         }
      } catch (Exception ex) {
         System.out.println("getSearchBoardList() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return boardbean;
   }

   // ****************************************
   public int getReportListCount() {
      int result = 0;
      try {
         conn = ds.getConnection();
         String sql = "select count(*) from word left inner join (select count(word_key) counts ,word_key from reportcount group by word_key)  using(word_key)";
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            result = Integer.parseInt(rs.getString(1));
         }
      } catch (Exception ex) {
         System.out.println("getListCount() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return result;
   }

   public List<WORD> getReportWordList(int page, int limit) {
      List<WORD> w = new ArrayList<WORD>();
      try {
         conn = ds.getConnection();
         String sql = "select * from (select b.*,rownum rnum from (select * from word left inner join (select count(word_key) counts ,word_key from reportcount group by word_key)  using(word_key) order by counts desc) b) where rnum between ? and ?";
         int startrow = (page - 1) * limit + 1;
         // �б� ������ row��ȣ(1 11 21 31...
         int endrow = startrow + limit - 1;
         // ���� ������ row��ȣ (10 20 30 40...
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, startrow);
         pstmt.setInt(2, endrow);
         rs = pstmt.executeQuery();
         while (rs.next()) {
            WORD word = new WORD();
            word.setWORD_TITLE(rs.getString("WORD_TITLE"));
            word.setWORD_DATE(rs.getDate("WORD_DATE"));
            word.setUSER_KEY(rs.getInt("USER_KEY"));
            word.setWORD_KEY(rs.getInt("WORD_KEY"));
            word.setREPORTCOUNT(rs.getInt("COUNTS"));
            w.add(word);
         }
      } catch (Exception ex) {
         System.out.println("getSearchBoardList() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return w;
   }
   // ****************************************

   // �����Խ��� �� ����-���ƿ� ��=========================================
   public int getLikeCount(int boardkey) {
      int result = 0;
      try {
         conn = ds.getConnection();
         String sql = "SELECT COUNT(*) " + "FROM COUNT " + "WHERE BOARD_KEY=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, boardkey);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            result = Integer.parseInt(rs.getString(1));
         }
      } catch (Exception ex) {
         System.out.println("getLikeCount() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return result;
   }

   // �����Խ��� �� ����-���� ���ƿ� ��������=========================================
   public boolean isMyLikeCount(int board_key, int user_key) {
      boolean result = false;
      try {
         conn = ds.getConnection();
         String sql = "SELECT COUNT(*) " + "FROM COUNT " + "WHERE BOARD_KEY=? AND USER_KEY=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, board_key);
         pstmt.setInt(2, user_key);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            if (rs.getInt(1) != 0)
               result = true;
            else
               result = false;
         }
      } catch (Exception ex) {
         System.out.println("isMyLikeCount() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return result;
   }

   // �Ű��ϱ� =====================================================================
   public int getReportedListCount() {
      int x = 0;

      try {
         conn = ds.getConnection();
         pstmt = conn.prepareStatement(
               "select count(*) from board left inner join (select count(board_key) counts, board_key from reportcount group by board_key) using (board_key)");
         rs = pstmt.executeQuery();
         if (rs.next()) {
            x = rs.getInt(1);
         }
      } catch (Exception ex) {
         System.out.println("getReportedListCount() ����:" + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return x;
   }

   public List<BoardBean> getReportedList(int page, int limit) {
      List<BoardBean> list = new ArrayList<BoardBean>();

      try {
         conn = ds.getConnection();
         String sql = "select * from (select b.*,rownum rnum from (select * from board left inner join (select count(board_key) counts ,board_key from reportcount group by board_key)  using(board_key) order by counts desc) b) where rnum between ? and ?";
         pstmt = conn.prepareStatement(sql);
         int startrow = (page - 1) * limit + 1;
         int endrow = startrow + limit - 1;

         pstmt.setInt(1, startrow);
         pstmt.setInt(2, endrow);
         rs = pstmt.executeQuery();

         while (rs.next()) {
            BoardBean w = new BoardBean();
            w.setBOARD_KEY(rs.getInt("board_KEY"));
            w.setBOARD_TITLE(rs.getString("board_TITLE"));
            w.setREPORTCOUNT(rs.getInt("COUNTS"));
            w.setUSER_NAME(userdao.getUserName(rs.getInt("USER_KEY"), 0));
            w.setRNUM(rs.getInt("rnum"));
            list.add(w);
         }
      } catch (Exception ex) {
         System.out.println("getReportedList() ����: " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return list;
   }

   public int getReportedListCount(String field, String value) {

      int x = 0;
      try {
         conn = ds.getConnection();
         String sql = "select count(*) from board left inner join (select count(board_key) counts, board_key from reportcount group by board_key) using (board_key) where "
               + field + " like ?";
         System.out.println(sql);
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%" + value + "%");
         rs = pstmt.executeQuery();

         if (rs.next()) {
            x = rs.getInt(1);
         }
      } catch (Exception ex) {
         System.out.println("getReportedListCount() ����" + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return x;
   }

   public List<BoardBean> getReportedList(String field, String value, int page, int limit) {
      List<BoardBean> list = new ArrayList<BoardBean>();
      try {
         conn = ds.getConnection();

         String sql = "select * from (select b.*,rownum rnum from " + " (select * from board left inner join "
               + " (select count(board_key) counts ,board_key " + " from reportcount group by board_key) "
               + " using(board_key) where " + field + " like ? order by counts desc) b) "
               + " where rnum between ? and ?";

         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, "%" + value + "%");
         int startrow = (page - 1) * limit + 1;
         int endrow = startrow + limit - 1;
         System.err.println(startrow+" / "+endrow);

         pstmt.setInt(2, startrow);
         pstmt.setInt(3, endrow);
         rs = pstmt.executeQuery();
         while (rs.next()) {
            BoardBean w = new BoardBean();
            w.setBOARD_KEY(rs.getInt("BOARD_KEY"));
            w.setBOARD_TITLE(rs.getString("BOARD_TITLE"));
            w.setREPORTCOUNT(rs.getInt("COUNTS"));
            w.setUSER_NAME(userdao.getUserName(rs.getInt("USER_KEY"), 0));
            w.setRNUM(rs.getInt(rs.getInt("RNUM")));
            list.add(w);
            System.out.println(w);
         }
      } catch (Exception ex) {
         System.out.println("getReportedList() ����: " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }

      return list;
   }

   public BoardBean report_info(int boardkey) {
      BoardBean w = null;
      try {
         conn = ds.getConnection();
         String sql = "select * from Board where board_key=? ";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, boardkey);
         rs = pstmt.executeQuery();
         if (rs.next()) {
            w = new BoardBean();
            w.setBOARD_KEY(rs.getInt("BOARD_KEY"));
            w.setBOARD_TITLE(rs.getString("BOARD_TITLE"));
            w.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
            w.setBOARD_DATE(rs.getDate("BOARD_DATE").toString());
            w.setBOARD_GIF(rs.getString("BOARD_GIF"));
            w.setUSER_NAME(userdao.getUserName(rs.getInt("USER_KEY"), 0));
         }
         return w;
      } catch (Exception ex) {
         ex.printStackTrace();
         System.out.println("report_info()���� " + ex);
      } finally {
         close();
      }
      return null;
   }

   public List<REPORTCOUNT> getReportDetail(int boardkey) {
      List<REPORTCOUNT> list = new ArrayList<REPORTCOUNT>();

      try {
         conn = ds.getConnection();
         String sql = "select * from reportcount where board_key=? ";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, boardkey);
         rs = pstmt.executeQuery();
         while (rs.next()) {
            REPORTCOUNT r = new REPORTCOUNT();
            r.setBOARD_KEY(rs.getInt("BOARD_KEY"));
            r.setREPORT_DATE(rs.getDate("REPORT_DATE"));
            r.setREPORT_REASON(rs.getString("REPORT_REASON"));
            r.setUSER_KEY(rs.getInt("USER_KEY"));
            r.setUSER_NAME(userdao.getUserName(rs.getInt("USER_KEY"), 0));
            list.add(r);
         }

      } catch (Exception ex) {
         System.out.println("getDetail() ���� : " + ex);
      } finally {
         close();
      }
      return list;
   }

   // =======================================================

   public List<List<BoardRankingBean>> getRanking(int size, int page) {
      List<List<BoardRankingBean>> ranking = new ArrayList<List<BoardRankingBean>>();
      try {
         for (int i = 0; i < page; i++) {
            int start = (i*size)+1;
            int end = start + (size-1);
            ranking.add(getSubRanking(start, end));
         }
      } catch (Exception ex) {
         System.out.println("getDetail() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return ranking;
   }

   public List<BoardRankingBean> getSubRanking(int start, int end) {
      System.out.println("start : " + start + " / end : " + end);

      List<BoardRankingBean> brl = new ArrayList<BoardRankingBean>();
      try {
         conn = ds.getConnection();
         String sql = "SELECT * FROM (SELECT A.*, ROWNUM RNUM FROM (SELECT BOARD_KEY, USER_KEY, BOARD_TITLE, BOARD_CONTENT, TO_CHAR(BOARD_DATE,'YYYY-MM-DD HH24:Mi:SS') BOARD_DATE, NVL(BOARD_READCOUNT,0) BOARD_READCOUNT, NVL(COMMENTCOUNT,0) REPLYCOUNT, NVL(LIKECOUNT,0) LIKECOUNT FROM (SELECT * FROM (SELECT * FROM BOARD LEFT JOIN (SELECT BOARD_KEY, COUNT(*) LIKECOUNT FROM COUNT GROUP BY BOARD_KEY) C USING (BOARD_KEY) LEFT JOIN (SELECT BOARD_KEY, COUNT(*) COMMENTCOUNT FROM REPLY GROUP BY BOARD_KEY) C USING (BOARD_KEY)))ORDER BY LIKECOUNT DESC, BOARD_READCOUNT DESC) A) where rnum >=? and rnum<=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, start);
         pstmt.setInt(2, end);
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
            BoardRankingBean br = new BoardRankingBean();
            br.setRNUM(rs.getInt("RNUM"));
            String title = rs.getString("BOARD_TITLE");
            if (title.length() > 9)
               title = title.substring(0, 9) + "...";
            br.setBOARD_TITLE(title);
            br.setBOARD_KEY(rs.getInt("BOARD_KEY"));
            brl.add(br);
         }
      } catch (Exception ex) {
         System.out.println("getDetail() ���� : " + ex);
         ex.printStackTrace();
      } finally {
         close();
      }
      return brl;
   }
   // =======================================================

}