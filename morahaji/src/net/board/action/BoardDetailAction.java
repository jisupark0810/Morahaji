package net.board.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.board.db.BoardBean;
import net.board.db.BoardDAO;
import net.board.db.BoardRankingBean;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BoardDAO boarddao = new BoardDAO();
		BoardBean boarddata = new BoardBean();
		HttpSession session = request.getSession();
		List<List<BoardRankingBean>> ranking = new ArrayList<List<BoardRankingBean>>();
		int num = Integer.parseInt(request.getParameter("num"));
		boarddao.setReadCountUpdate(num);
		int likecount = boarddao.getLikeCount(num);
		boolean like = false;
		int user_key = 0;
		if (session.getAttribute("userKey") == null) {
		} else {
			like = boarddao.isMyLikeCount(num, Integer.parseInt(session.getAttribute("userKey").toString()));
			user_key = Integer.parseInt(session.getAttribute("userKey").toString());
		}
		boarddata = boarddao.getDetail(num, user_key);
		int rankingsize = 5;
		int rankingpage = 3;
		ranking = boarddao.getRanking(rankingsize, rankingpage);
		ActionForward forward = new ActionForward();
		if (boarddata == null) {
			System.out.println("상세보기 실패");
			forward.setRedirect(false);
			request.setAttribute("message", "게시판 상세보기 실패입니다.");
			forward.setPath("error/error.jsp");
			return forward;
		}
		request.setAttribute("loginUserkey", session.getAttribute("userKey"));
		request.setAttribute("boarddata", boarddata);
		request.setAttribute("boardkey", num);
		request.setAttribute("ranking", ranking);
		request.setAttribute("likeornot", like);
		request.setAttribute("likecount", likecount);

		forward.setRedirect(false);
		forward.setPath("board/qna_board_view.jsp");
		return forward;
	}

}