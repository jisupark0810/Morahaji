package net.activity.action;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.activity.db.CountDAO;

public class AddCountProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CountDAO dao = new CountDAO();
		HttpSession session = request.getSession();

//		int userKey = Integer.parseInt(session.getAttribute("userKey").toString());
		String userKey = null;
		if (session.getAttribute("userKey") != null)
			userKey = session.getAttribute("userKey").toString();
		String rowPostKey = request.getParameter("postKey");
		String postKey = rowPostKey.contains("_") ? rowPostKey.split("_")[1] : rowPostKey;
		String postType = request.getParameter("postType");
		String countType = request.getParameter("countType");

		HashMap<String, String> addCountSource = new HashMap<String, String>();
		addCountSource.put("userKey", userKey);
		addCountSource.put("postKey", postKey);
		addCountSource.put("postType", postType);
		addCountSource.put("countType", countType);

		int result = dao.addCount(addCountSource);
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
