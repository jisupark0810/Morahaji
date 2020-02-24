package net.activity.action;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.activity.db.CountDAO;

public class AddReportProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CountDAO dao = new CountDAO();
		HttpSession session = request.getSession();

//		int userKey = Integer.parseInt(session.getAttribute("userKey").toString());

		String userKey = null;
		if (session.getAttribute("userKey") != null)
			userKey = session.getAttribute("userKey").toString();
		String rowPostKey = null;
		if (request.getParameter("postKey") != null)
			rowPostKey = request.getParameter("postKey");
		String postKey = null;
		if (request.getParameter("postType").equals("board")) {
			postKey = request.getParameter("boardKey");
		} else {
			postKey = rowPostKey.split("_")[1];
		}
		String postType = request.getParameter("postType");
		String reason = request.getParameter("reason");

		HashMap<String, String> addReportSource = new HashMap<String, String>();
		addReportSource.put("userKey", userKey);
		addReportSource.put("postKey", postKey);
		addReportSource.put("postType", postType);
		addReportSource.put("reason", reason);

		int result = dao.addReport(addReportSource);
		if (result != 1) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>" + "alert('오류가 발생했습니다. 잠시후 다시 시도해주세요. :`( ');" + "history.back()" + "</script>");
			out.close();
			return null;
		}
		return null;
	}

}
