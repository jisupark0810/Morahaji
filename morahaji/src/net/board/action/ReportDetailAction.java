package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.word.db.WORD;
import net.word.db.WordDAO;

public class ReportDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WordDAO word = new WordDAO();
		WORD w = new WORD();

		// �۹�ȣ �Ķ���� ���� num������ �����մϴ�.
		int num = Integer.parseInt(request.getParameter("num"));

		// ���� ������ DAO���� ���� �� ���� ����� boarddata��ü�� �����մϴ�.
		w = word.getWord(num);
		// boarddata = null;
		// DAO���� ���� ������ ���� ������ ��� null�� ��ȯ�մϴ�.
		ActionForward forward = new ActionForward();
		if (w == null) {
			System.out.println("�󼼺��� ����");
			forward.setRedirect(false);
			request.setAttribute("message", "�Խ��� �󼼺��� �����Դϴ�.");
			forward.setPath("error/error.jsp");
			return forward;
		}
		System.out.println("�󼼺��� ����");
		request.setAttribute("word", w);
		request.setAttribute("num", num);
		forward.setRedirect(false);

		// �� ���� ���� �������� �̵��ϱ� ���� ��θ� �����մϴ�.
		forward.setPath("board/reportDetail.jsp");
		return forward;
	}

}
