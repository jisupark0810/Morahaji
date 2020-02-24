package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.Action.Action;
import net.Action.ActionForward;
import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO boarddao = new BoardDAO();
		BoardBean boarddata = new BoardBean();
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		boarddata.setBOARD_TITLE(replaceParameter(request.getParameter("BOARD_TITLE")));
		boarddata.setBOARD_CONTENT(replaceParameter(request.getParameter("BOARD_CONTENT")));
		boarddata.setBOARD_GIF(request.getParameter("board_gif"));
		boolean result = boarddao.boardInsert(Integer.parseInt(session.getAttribute("userKey").toString()), boarddata);
		if (result == false) {
			forward.setRedirect(false);
			request.setAttribute("message", "게시판 등록 실패입니다.");
			forward.setPath("error/error.jsp");
			return forward;
		}
		forward.setRedirect(true);
		forward.setPath("BoardList.bo");
		return forward;
	}

	private String replaceParameter(String param) {
		String result = param;
		if (param != null) {
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result.replaceAll("\"", "&quot;");
		}
		return result;
	}
}
