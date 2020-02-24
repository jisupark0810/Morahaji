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
		// 좋아요, 싫어요, 북마크 삭제
		// 해시태그-단어 삭제
		// 단어 삭제

		int wordKey = Integer.parseInt(request.getParameter("wordKey").toString());
		int result = 0;
		String msg = "";

		WordDAO wordDAO = new WordDAO();
		HashTagDAO hashtagDAO = new HashTagDAO();
		CountDAO countDAO = new CountDAO();

		request.setCharacterEncoding("UTF-8");

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

		System.out.println(msg);
		ActionForward forward = new ActionForward();

		forward.setRedirect(true);
		forward.setPath("main.index");
		return forward;
	}
}
