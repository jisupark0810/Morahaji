package net.users.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.UserDAO;

public class updatePassProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		request.setCharacterEncoding("utf-8");		
		HttpSession session = request.getSession();
		int userKey = Integer.parseInt(session.getAttribute("userKey").toString());
		String userPass = request.getParameter("userPass");		
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		UserDAO dao = new UserDAO();
		
		int result = dao.updatePass(userKey, userPass);
		out.println("<script>");
		
		//삽입이 된 경우
		if (result == 1) {
            session.setAttribute("userPass", userPass); 
		} else {
			System.out.println("비밀번호 변경에 실패했습니다.");
		}
		out.println("</script>");
		out.close();

		return null;
	}

}
