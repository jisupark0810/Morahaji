package net.users.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.UserDAO;

public class FindPwProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();

		UserDAO dao = new UserDAO();

		String id = request.getParameter("userId");
		String email = request.getParameter("userEmail");
		System.out.println("id : " + id + " / Email : " + email);
		int result = dao.findPw(id, email);

		PrintWriter out = response.getWriter();
		response.setContentType("text/html; charset=utf-8");

		if (result == 1) {// 아이디 비밀번호 일치
			System.out.println("아이디/비밀번호 일치");
			String tempPw = makeTempPw();
			result = dao.setPw(tempPw, id, email); // 임시 비밀번호를 만들어서 DB에 넣음
			if (result == 1) { // 임시 비밀번호 업데이트 성공
				// 임시 비밀번호를 보여줌
				response.getWriter().append(Integer.toString(result));
				response.getWriter().append("/"+tempPw);
				return null;

			} else { // 임시 비밀번호 업데이트 실패
				out.print("error");
				out.close();
				return null;
			}

		} else { // 뭔가 안맞음
			if (result == 0) {
				System.out.println("아이디 / 이메일 없음");
				response.getWriter().append(Integer.toString(result));
				return null;
			} else if (result == -1) {
				System.out.println("이메일 불일치");
				response.getWriter().append(Integer.toString(result));
				return null;
			} else if (result == -2) {
				System.out.println("아이디 불일치");
				response.getWriter().append(Integer.toString(result));
				return null;
			} else if (result == -3) {
				System.out.println("이메일과 아이디가 서로 불일치");
				response.getWriter().append(Integer.toString(result));
				return null;
			}
		}
		return forward;
	}

	public String makeTempPw() {
		char pwCollection[] = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E',
				'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
				'v', 'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')' };// 배열에 선언

		String ranPw = "";

		for (int i = 0; i < 10; i++) {
			int selectRandomPw = (int) (Math.random() * (pwCollection.length));// Math.rondom()은 0.0이상 1.0미만의 난수를 생성해
																				// 준다.
			ranPw += pwCollection[selectRandomPw];
		}
		return ranPw;
	}

}
