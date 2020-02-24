package net.admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.activity.db.CountDAO;
import net.hashtag.db.HashTagDAO;
import net.word.db.WordDAO;

public class Report_deleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		// ���ƿ�, �Ⱦ��, �ϸ�ũ ����
		// �ؽ��±�-�ܾ� ����
		// �ܾ� ����

		int wordKey = Integer.parseInt(request.getParameter("wordkey").toString());
		int result = 0;
		String msg = "";
		System.out.println("Delete wordkey : "+wordKey);

		WordDAO wordDAO = new WordDAO();
		HashTagDAO hashtagDAO = new HashTagDAO();
		CountDAO countDAO = new CountDAO();

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
		
		System.out.println("msg : " + msg);
		
		if(msg.length() < 1)
			msg = "���� �Ϸ�";
		
		System.out.println("msg : " + msg);

		out.println("<script>");
		out.print("alert(\"" + msg + "\");");
		out.println("location.href='report_list.admin';");
		out.println("</script>");

		return null;
	}

}
