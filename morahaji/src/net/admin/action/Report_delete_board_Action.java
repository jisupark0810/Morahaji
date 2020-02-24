package net.admin.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.activity.db.CountDAO;
import net.board.db.BoardDAO;

public class Report_delete_board_Action implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		// 좋아요, 싫어요, 북마크 삭제
		// 해시태그-단어 삭제
		// 단어 삭제

		int boardKey = Integer.parseInt(request.getParameter("boardkey").toString());

		BoardDAO boardDAO = new BoardDAO();
		CountDAO countDAO = new CountDAO();

		if (countDAO.isCountBoard(boardKey)) { // 좋아요, 싫어요, 북마크 중 하나라도 있는 경우
			countDAO.deleteCountAllBoard(boardKey);
		}

		if (countDAO.isReportBoard(boardKey)) { // 신고가 된 적 있는 경우
			countDAO.deleteReportAllWord(boardKey);
		}
		
		boardDAO.boardDelete(boardKey);

		out.println("<script>");
		out.println("location.href='report_list_board.admin';");
		out.println("</script>");

		return null;
	}

}
