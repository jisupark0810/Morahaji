package net.admin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.USERS;
import net.users.db.UserDAO;

public class Member_infoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		UserDAO udao = new UserDAO();
		USERS u = new USERS();
		ActionForward forward = new ActionForward();

		String id = (String) request.getParameter("id");
		u = udao.member_info(id);

		if (u == null) {
			System.out.println("(����)�󼼺��� ����");
			forward.setRedirect(false);
			request.setAttribute("message", "ȸ�� �󼼺��� �����Դϴ�.");
			forward.setPath("error/error.jsp");
		}
		System.out.println("(����)�󼼺��� ����");

		request.setAttribute("memberinfo", u);
		forward.setRedirect(false);
		forward.setPath("admin/member_info.jsp");
		return forward;
	}

}
