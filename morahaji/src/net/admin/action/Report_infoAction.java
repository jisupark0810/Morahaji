package net.admin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.report.db.REPORTCOUNT;
import net.word.db.WORD;
import net.word.db.WordDAO;

public class Report_infoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		WordDAO wdao = new WordDAO();
		WORD w = new WORD();
		ActionForward forward = new ActionForward();
		
		int wordkey=Integer.parseInt(request.getParameter("wordkey"));
		w=wdao.report_info(wordkey);
		
		List<REPORTCOUNT> list = null;
		
		list = wdao.getDetail(wordkey);
		
		System.out.println(list);
		
		
		if(w==null) {
			System.out.println("신고 상세보기 실패");
			forward.setRedirect(false);
			request.setAttribute("message", "신고 상세보기 실패입니다.");
			forward.setPath("error/error.jsp");
			return forward;
		}
		System.out.println("신고 상세보기 성공");

		request.setAttribute("wordinfo", w);
		request.setAttribute("detailinfo", list);
		
		forward.setRedirect(false);
		forward.setPath("admin/report_info.jsp");
		
		return forward;
	}

}
