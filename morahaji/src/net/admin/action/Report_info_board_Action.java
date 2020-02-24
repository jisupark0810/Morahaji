package net.admin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.board.db.BoardBean;
import net.board.db.BoardDAO;
import net.report.db.REPORTCOUNT;

public class Report_info_board_Action implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		BoardDAO bdao = new BoardDAO();
		BoardBean w = new BoardBean();
		ActionForward forward = new ActionForward();
		
		int boardkey=Integer.parseInt(request.getParameter("boardkey"));
		w=bdao.report_info(boardkey);
		
		List<REPORTCOUNT> list = null;
		
		list = bdao.getReportDetail(boardkey);

		if(w==null) {
			System.out.println("신고 상세보기 실패");
			forward.setRedirect(false);
			request.setAttribute("message", "신고 상세보기 실패입니다.");
			forward.setPath("error/error.jsp");
			return forward;
		}
		System.out.println("신고 상세보기 성공");

		request.setAttribute("boardinfo", w);
		request.setAttribute("detailinfo", list);
		
		forward.setRedirect(false);
		forward.setPath("admin/report_info_board.jsp");
		
		return forward;
	}

}
