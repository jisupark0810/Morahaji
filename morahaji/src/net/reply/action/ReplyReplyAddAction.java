package net.reply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.reply.db.Reply;
import net.reply.db.ReplyDAO;

public class ReplyReplyAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ReplyDAO dao = new ReplyDAO();
		Reply rp = new Reply();
		// ajax�̿�� �ѱ� ���� ����
		request.setCharacterEncoding("UTF-8");
		rp.setUSER_KEY(Integer.parseInt(request.getParameter("user_key").toString()));
		rp.setREPLY_CONTENT(request.getParameter("reply_content"));
		rp.setBOARD_KEY(Integer.parseInt(request.getParameter("board_key")));
		rp.setREPLY_RE_REF(Integer.parseInt(request.getParameter("reply_re_ref")));
		rp.setREPLY_RE_SEQ(Integer.parseInt(request.getParameter("reply_re_seq")));
		rp.setREPLY_RE_LEV(Integer.parseInt(request.getParameter("reply_re_lev")));
		try {
			int success = dao.replyReplyInsert(rp);
			response.getWriter().print(success);
		} catch (Exception e) {
			System.out.println("��� ��� ����");
			ActionForward forward = new ActionForward();
			forward.setPath("error/error.jsp");
			request.setAttribute("message", "��� ��� �����Դϴ�.");
			forward.setRedirect(false);
			return forward;
		}
		return null;
	}

}
