package net.users.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.UserDAO;

public class updateAgeProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		int userKey = Integer.parseInt(session.getAttribute("userKey").toString());
		String newAge = request.getParameter("ageChange");		
		
		System.out.println(newAge+"��");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		UserDAO dao = new UserDAO();
		int result = dao.updateAge(userKey, newAge);
		//������ �� ���
		if (result == 1) {
            session.setAttribute("userAge", newAge); 
		} else {
			System.out.println("ȸ�� ���� ������ �����߽��ϴ�.");
		}
		out.println("</script>");
		out.close();
		return null;
	}

}
