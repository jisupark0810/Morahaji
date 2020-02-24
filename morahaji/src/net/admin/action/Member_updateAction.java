package net.admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.UserDAO;

public class Member_updateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("update");
		request.setCharacterEncoding("utf-8");
		UserDAO udao = new UserDAO();

		String id = request.getParameter("id");
		int result = udao.update(id);

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String message = id + "의 상태가 일반회원으로 변경되었습니다.";

		if (result != 1) {
			message = "일반회원으로 변경하지 못했습니다.";
		}
		out.println("<script>");
		out.println("alert('" + message + "');");
		out.println("location.href='member_list.admin';");
		out.println("</script>");
		out.close();

		return null;
	}

}
