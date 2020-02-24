package net.activity.action;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.activity.db.CountDAO;

public class RemoveCountProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CountDAO dao = new CountDAO();
		HttpSession session = request.getSession();

//		int userKey = Integer.parseInt(session.getAttribute("userKey").toString());
		String userKey = null;
		if (session.getAttribute("userKey") != null)
			userKey = session.getAttribute("userKey").toString();
		String rowPostKey = request.getParameter("postKey");
		String postKey = rowPostKey.split("_")[1];
		String postType = request.getParameter("postType");
		String countType = request.getParameter("countType");

		HashMap<String, String> removeCountSource = new HashMap<String, String>();
		removeCountSource.put("userKey", userKey);
		removeCountSource.put("postKey", postKey);
		removeCountSource.put("postType", postType);
		removeCountSource.put("countType", countType);

		int result = dao.removeCount(removeCountSource);

		if (result != 1) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("error");
			out.close();
			return null;
		}
		return null;
	}

}
