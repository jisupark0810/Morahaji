package net.users.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.UserDAO;

public class LoginProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDAO dao = new UserDAO();

		String id = request.getParameter("userId");
		String pw = request.getParameter("userPw");
		System.out.println("id : " + id + " / pw : " + pw);
//		int result = dao.isID(id, pw);
		HashMap<String, String> result = dao.isID(id, pw);

		HttpSession session = request.getSession();

		if (result.get("result").equals("1")) {// 아이디 비밀번호 일치
			System.out.println("아이디/비밀번호 일치");
			if (result.get("result").equals("-2")) { // 탈퇴한 회원
				System.out.println("탈퇴한 회원");
				response.getWriter().append(result.get("result"));
				return null;
			}

			session.setAttribute("userId", result.get("userId"));
			session.setAttribute("userEmail", result.get("userEmail"));
			session.setAttribute("userName", result.get("userName"));
			session.setAttribute("userKey", result.get("userKey"));
			
			response.setHeader("Set-Cookie", "key=value; HttpOnly; SameSite=none; Secure;");

			response.getWriter().append(result.get("result"));
			return null;
		} else {
			response.setContentType("text/html; charset=utf-8");

			if (result.get("result").equals("0")) { // 비밀번호 불일치
				System.out.println("비밀번호 불일치");
				response.getWriter().append(result.get("result"));
				return null;
			} else {
				System.out.println("아이디 없음");
				response.getWriter().append(result.get("result"));
				return null;
			}
		}
	}
}
