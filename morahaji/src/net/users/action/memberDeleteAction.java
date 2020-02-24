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
		int userKey = Integer.parseInt(session.getAttribute("userKey").toString());//id�� session���� ������
		String id = session.getAttribute("userId").toString();
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = dao.delete(userKey);
		
		String message = id + "�� ȸ�� ������ �����Ǿ����ϴ�.";
		LogoutAction logout = new LogoutAction();
		logout.execute(request, response);
		
		if(result != 1) {//������ ���� ���� ���
			message = "������ ���� �ʾҽ��ϴ�.";
		}
		out.println("<script>");
		out.println("alert('" + message + "');");
		out.println("</script>");
		return null;
	}

}
