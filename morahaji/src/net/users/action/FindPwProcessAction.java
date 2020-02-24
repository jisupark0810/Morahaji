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

		if (result == 1) {// ���̵� ��й�ȣ ��ġ
			System.out.println("���̵�/��й�ȣ ��ġ");
			String tempPw = makeTempPw();
			result = dao.setPw(tempPw, id, email); // �ӽ� ��й�ȣ�� ���� DB�� ����
			if (result == 1) { // �ӽ� ��й�ȣ ������Ʈ ����
				// �ӽ� ��й�ȣ�� ������
				response.getWriter().append(Integer.toString(result));
				response.getWriter().append("/"+tempPw);
				return null;

			} else { // �ӽ� ��й�ȣ ������Ʈ ����
				out.print("error");
				out.close();
				return null;
			}

		} else { // ���� �ȸ���
			if (result == 0) {
				System.out.println("���̵� / �̸��� ����");
				response.getWriter().append(Integer.toString(result));
				return null;
			} else if (result == -1) {
				System.out.println("�̸��� ����ġ");
				response.getWriter().append(Integer.toString(result));
				return null;
			} else if (result == -2) {
				System.out.println("���̵� ����ġ");
				response.getWriter().append(Integer.toString(result));
				return null;
			} else if (result == -3) {
				System.out.println("�̸��ϰ� ���̵� ���� ����ġ");
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
				'v', 'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')' };// �迭�� ����

		String ranPw = "";

		for (int i = 0; i < 10; i++) {
			int selectRandomPw = (int) (Math.random() * (pwCollection.length));// Math.rondom()�� 0.0�̻� 1.0�̸��� ������ ������
																				// �ش�.
			ranPw += pwCollection[selectRandomPw];
		}
		return ranPw;
	}

}
