package net.users.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.UserDAO;

public class updateProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		request.setCharacterEncoding("utf-8");		
		HttpSession session = request.getSession();
		int userKey = Integer.parseInt(session.getAttribute("userKey").toString());
		String userName = request.getParameter("nameChange");		
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		UserDAO dao = new UserDAO();
		
		int result = dao.updateName(userKey, userName);
		out.println("<script>");
		
		//삽입이 된 경우
		if (result == 1) {
            session.setAttribute("userName", userName); 
		} else {
			System.out.println("회원 정보 수정에 실패했습니다.");
		}
		out.println("</script>");
		out.close();
		
		return null;
	}

}
