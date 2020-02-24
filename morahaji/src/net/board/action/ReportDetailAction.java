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

		// 글번호 파라미터 값을 num변수에 저장합니다.
		int num = Integer.parseInt(request.getParameter("num"));

		// 글의 내용을 DAO에서 읽은 후 얻은 결과를 boarddata객체에 저장합니다.
		w = word.getWord(num);
		// boarddata = null;
		// DAO에서 글의 내용을 읽지 못했을 경우 null을 반환합니다.
		ActionForward forward = new ActionForward();
		if (w == null) {
			System.out.println("상세보기 실패");
			forward.setRedirect(false);
			request.setAttribute("message", "게시판 상세보기 실패입니다.");
			forward.setPath("error/error.jsp");
			return forward;
		}
		System.out.println("상세보기 성공");
		request.setAttribute("word", w);
		request.setAttribute("num", num);
		forward.setRedirect(false);

		// 글 내용 보기 페이지로 이동하기 위해 경로를 설정합니다.
		forward.setPath("board/reportDetail.jsp");
		return forward;
	}

}
