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
		// 좋아요, 싫어요, 북마크 삭제
		// 해시태그-단어 삭제
		// 단어 삭제

		int wordKey = Integer.parseInt(request.getParameter("wordkey").toString());
		int result = 0;
		String msg = "";
		System.out.println("Delete wordkey : "+wordKey);

		WordDAO wordDAO = new WordDAO();
		HashTagDAO hashtagDAO = new HashTagDAO();
		CountDAO countDAO = new CountDAO();

		if (countDAO.isCount(wordKey)) { // 좋아요, 싫어요, 북마크 중 하나라도 있는 경우
			result = countDAO.deleteCountAllWord(wordKey);
			System.out.println("1");
			msg += result != 1 ? "\n좋아요, 싫어요, 북마크 삭제 도중 오류 발생" : "";
		}

		if (countDAO.isReport(wordKey)) { // 신고가 된 적 있는 경우
			result = countDAO.deleteReportAllWord(wordKey);
			System.out.println("2");
			msg += result != 1 ? "\n신고 삭제 도중 오류 발생" : "";
		}

		if (hashtagDAO.isHashtagWord(wordKey)) { // 해시태그 있을 경우
			System.out.println("3");
			result = hashtagDAO.deleteHashtagWord(wordKey);
			System.out.println("4");
			msg += result != 1 ? "\n해시태그 삭제 도중 오류 발생" : "";
		}

		result = wordDAO.deleteWord(wordKey);
		System.out.println("5");
		msg += result != 1 ? "\n단어 삭제 도중 오류 발생" : "";
		
		System.out.println("msg : " + msg);
		
		if(msg.length() < 1)
			msg = "삭제 완료";
		
		System.out.println("msg : " + msg);

		out.println("<script>");
		out.print("alert(\"" + msg + "\");");
		out.println("location.href='report_list.admin';");
		out.println("</script>");

		return null;
	}

}
