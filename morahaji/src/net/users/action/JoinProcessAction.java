package net.users.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.USERS;
import net.users.db.UserDAO;

public class JoinProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDAO dao = new UserDAO();
		USERS u = new USERS();
		
		request.setCharacterEncoding("UTF-8");

		u.setUSER_ID(request.getParameter("userId"));
		u.setUSER_PASSWORD(request.getParameter("userPw"));
		u.setUSER_NAME(request.getParameter("userName"));
		u.setUSER_EMAIL(request.getParameter("userEmail"));
		u.setUSER_AGERANGE(request.getParameter("userAgeRange"));

		System.out.println("user id = " + u.getUSER_ID());
		int result = dao.insert(u);
		if (result != 1) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("error");
			out.close();
			return null;
		} else {
			response.getWriter().append(Integer.toString(result));

			ActionForward forward = new ActionForward();

			forward.setRedirect(true);
			forward.setPath("login.net");
			return forward;
		}
	}
}
