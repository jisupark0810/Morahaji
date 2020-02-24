package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.board.db.BoardDAO;
import net.reply.db.ReplyDAO;

public class DeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String type = request.getParameter("typevalue");
		int reply_re_ref = -1;
		int reply_re_seq = -1;
		int reply_re_lev = -1;
		if (type.equals("reply")) {
			reply_re_ref = Integer.parseInt(request.getParameter("re_ref").toString());
			reply_re_seq = Integer.parseInt(request.getParameter("re_seq").toString());
			reply_re_lev = Integer.parseInt(request.getParameter("re_lev").toString());
		}
		int board_key = Integer.parseInt(request.getParameter("modalboardkey").toString());
		BoardDAO boarddao = new BoardDAO();
		ReplyDAO replydao = new ReplyDAO();
		boolean result = false;
		if (type.equals("reply")) {
			result = replydao.replydelete(reply_re_ref, reply_re_seq, reply_re_lev);
		} else {
			result = boarddao.boardDelete(board_key);
		}
		ActionForward forward = new ActionForward();
		if (result == false) {
			System.out.println("게시판 삭제 실패");
			forward.setRedirect(false);
			request.setAttribute("message", "게시판 삭제 실패입니다");
			forward.setPath("error/error.jsp");
			return forward;
		}
		if (type.equals("board")) {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("location.href='BoardList.bo'");
			out.println("</script>");
			out.close();
		} else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("location.href='BoardDetailAction.bo?num="+board_key+"'");
			out.println("</script>");
			out.close();
		}
		return null;
	}

}
