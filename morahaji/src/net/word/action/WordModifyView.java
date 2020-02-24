package net.word.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.word.db.WORD;
import net.word.db.WordDAO;

public class WordModifyView implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();

		WordDAO dao = new WordDAO();
		WORD word = new WORD();

		int wordKey = Integer.parseInt(request.getParameter("wordKey"));

		word = dao.getDetailForModify(wordKey);

		System.out.println("수정 페이지 이동 성공");
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("word", word);
		forward.setRedirect(false);
		forward.setPath("./word.register/index_modify.jsp");
		return forward;
	}

}
