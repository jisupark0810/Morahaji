package net.word.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.activity.db.CountDAO;
import net.hashtag.db.HashTagDAO;
import net.word.db.WordDAO;

public class WordDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ���ƿ�, �Ⱦ��, �ϸ�ũ ����
		// �ؽ��±�-�ܾ� ����
		// �ܾ� ����

		int wordKey = Integer.parseInt(request.getParameter("wordKey").toString());
		int result = 0;
		String msg = "";

		WordDAO wordDAO = new WordDAO();
		HashTagDAO hashtagDAO = new HashTagDAO();
		CountDAO countDAO = new CountDAO();

		request.setCharacterEncoding("UTF-8");

		if (countDAO.isCount(wordKey)) { // ���ƿ�, �Ⱦ��, �ϸ�ũ �� �ϳ��� �ִ� ���
			result = countDAO.deleteCountAllWord(wordKey);
			System.out.println("1");
			msg += result != 1 ? "\n���ƿ�, �Ⱦ��, �ϸ�ũ ���� ���� ���� �߻�" : "";
		}

		if (countDAO.isReport(wordKey)) { // �Ű� �� �� �ִ� ���
			result = countDAO.deleteReportAllWord(wordKey);
			System.out.println("2");
			msg += result != 1 ? "\n�Ű� ���� ���� ���� �߻�" : "";
		}

		if (hashtagDAO.isHashtagWord(wordKey)) { // �ؽ��±� ���� ���
			System.out.println("3");
			result = hashtagDAO.deleteHashtagWord(wordKey);
			System.out.println("4");
			msg += result != 1 ? "\n�ؽ��±� ���� ���� ���� �߻�" : "";
		}

		result = wordDAO.deleteWord(wordKey);
		System.out.println("5");
		msg += result != 1 ? "\n�ܾ� ���� ���� ���� �߻�" : "";

		System.out.println(msg);
		ActionForward forward = new ActionForward();

		forward.setRedirect(true);
		forward.setPath("main.index");
		return forward;
	}
}
