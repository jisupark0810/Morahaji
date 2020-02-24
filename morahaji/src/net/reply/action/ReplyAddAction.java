package net.reply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.reply.db.Reply;
import net.reply.db.ReplyDAO;

public class ReplyAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ReplyDAO dao = new ReplyDAO();
		Reply rp = new Reply();
		// ajax이용시 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");
		rp.setUSER_KEY(Integer.parseInt(request.getParameter("user_key")));
		rp.setREPLY_CONTENT(request.getParameter("reply_content"));
		rp.setBOARD_KEY(Integer.parseInt(request.getParameter("board_key")));
		try {
			int ok = dao.replyInsert(rp);
			response.getWriter().print(ok);
		} catch (Exception e) {
			System.out.println("댓글 등록 실패");
			ActionForward forward = new ActionForward();
			forward.setPath("error/error.jsp");
			request.setAttribute("message", "댓글 등록 실패입니다.");
			forward.setRedirect(false);
			return forward;
		}
		return null;
	}

}
