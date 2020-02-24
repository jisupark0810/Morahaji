package net.users.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.UserDAO;

public class MypageLoadAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();

		UserDAO dao = new UserDAO();
		System.out.println(session.getAttribute("userKey"));
		if (session.getAttribute("userKey") == null) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script> history.back(); </script>");
			out.close();
			return null;
		} else {
			int key = Integer.parseInt(session.getAttribute("userKey").toString());
			System.out.println("key : " + key);
			String lastAct = dao.getLastAct(key);
			int totalWord = dao.getCounts(key);
			String profilePic = dao.getProfiles(key);

			response.getWriter().append(lastAct + "/" + totalWord + "/" + profilePic);
			System.out.println(lastAct + "/" + totalWord + "/" + profilePic);
		}
		return null;
	}
}
