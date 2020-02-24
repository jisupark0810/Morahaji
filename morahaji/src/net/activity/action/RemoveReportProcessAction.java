package net.activity.action;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.activity.db.CountDAO;

public class RemoveReportProcessAction implements Action {

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
			postKey = request.getParameter("postKey");
		} else {
			postKey = rowPostKey.split("_")[1];
		}
		String postType = request.getParameter("postType");
		HashMap<String, String> removeReportSource = new HashMap<String, String>();
		removeReportSource.put("userKey", userKey);
		removeReportSource.put("postKey", postKey);
		removeReportSource.put("postType", postType);

		int result = dao.removeReport(removeReportSource);

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
