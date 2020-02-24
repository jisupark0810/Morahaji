package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardModifyView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		BoardDAO boarddao = new BoardDAO();
		BoardBean boarddata = new BoardBean();
		HttpSession session = request.getSession();
		int num = Integer.parseInt(request.getParameter("num"));
		int user_key = 0;
		if (session.getAttribute("userKey") == null) {
		} else {
			user_key = Integer.parseInt(session.getAttribute("userKey").toString());
		}
		boarddata = boarddao.getDetail(num, user_key);
		if (boarddata == null) {
			System.out.println("(수정)상세보기 실패");
			forward.setRedirect(false);
			request.setAttribute("message", "게시판 상세보기 실패입니다.");
			forward.setPath("error/error.jsp");
			return forward;
		}
		request.setAttribute("boarddata", boarddata);
		forward.setRedirect(false);
		forward.setPath("board/qna_board_modify.jsp");
		return forward;
	}

}