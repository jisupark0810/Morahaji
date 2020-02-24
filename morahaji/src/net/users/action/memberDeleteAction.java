package net.users.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.UserDAO;

public class memberDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		UserDAO dao = new UserDAO();
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		int userKey = Integer.parseInt(session.getAttribute("userKey").toString());//id를 session에서 가져옴
		String id = session.getAttribute("userId").toString();
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = dao.delete(userKey);
		
		String message = id + "님 회원 정보가 삭제되었습니다.";
		LogoutAction logout = new LogoutAction();
		logout.execute(request, response);
		
		if(result != 1) {//삭제가 되지 않은 경우
			message = "삭제가 되지 않았습니다.";
		}
		out.println("<script>");
		out.println("alert('" + message + "');");
		out.println("</script>");
		return null;
	}

}
