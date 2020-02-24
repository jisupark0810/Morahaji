package net.main.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.hashtag.db.HashTagDAO;
import net.word.db.WORD;
import net.word.db.WordDAO;

public class IndexLoadAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WordDAO wordDAO = new WordDAO();
		List<WORD> wordList = new ArrayList<WORD>();
		HashTagDAO hashtagDAO = new HashTagDAO();
		List<Integer> hash_wordKeyList = new ArrayList<Integer>();
		HttpSession session = request.getSession();

		int userKey = 0;
		if (session.getAttribute("userKey") != null)
			userKey = Integer.parseInt(session.getAttribute("userKey").toString());

		//ranking
				List<List<WORD>> ranking = new ArrayList<List<WORD>>();
				int rankingsize = 5;
				int rankingpage = 3;
				ranking = wordDAO.getRanking(rankingsize, rankingpage);
				request.setAttribute("ranking", ranking);

				
		String keyword = "";
		System.out.println("keyword : " + request.getParameter("keyword"));
		if (request.getParameter("keyword") != null && request.getParameter("keyword").length() > 0) {
			keyword = request.getParameter("keyword");
			session.setAttribute("keyword", keyword);
		} else {
			keyword = null;
			session.removeAttribute("keyword");
		}

		String tag = "";
		System.out.println("tag : " + request.getParameter("tag"));
		if (request.getParameter("tag") != null && request.getParameter("tag").length() > 0) {
			tag = request.getParameter("tag");
			session.setAttribute("tag", tag);
			hash_wordKeyList = hashtagDAO.getWordList(tag); // 해시태그를 가지고 있는 단어목록들의 키값을 가져옴
		} else {
			tag = null;
			session.removeAttribute("tag");
		}
		int page = 1;
		int limit = 5;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		System.out.println("넘어온 페이지 = " + page);
		System.out.println("넘어온 단어 = " + keyword);

		// 총 리스트 수
		int listcount = 0;
		if (tag != null && tag.length() > 0) {
			listcount = hash_wordKeyList.size();
			wordList = wordDAO.getWordList(page, limit, userKey, hash_wordKeyList);
		} else {
			listcount = wordDAO.getListCount(keyword);
			wordList = wordDAO.getWordList(page, limit, userKey, keyword);
		}
//		/*
//		 총 페이지 수 = DB에 저장된 총 리스트의 수 + 한 페이지에서 보여주는 리스으틔 수 -1) / 한 페이지에서 보여주는 리스트의 수
//		 
//		 Ex)
//		 limit = 10인 경우
//		 DB에 저장 된 총 리스트의 수가 0이면 총 페이지 수는 0 
//		 DB에 저장 된 총 리스트의 수가 (1-10)이면 총 페이지 수는 1 
//		 DB에 저장 된 총 리스트의 수가 (11-20)이면 총 페이지 수는 2 
//		 DB에 저장 된 총 리스트의 수가 (21-30)이면 총 페이지 수는 3 
//		 */

		int maxpage = (listcount + limit - 1) / limit;
		System.out.println("총 페이지 수 = " + maxpage);

//		/*
//		  startpage : 현재 페이지 그룹에서 맨 처음에 표시될 페이지 수를 의미
//		  
//		  보여줄 페이지가 30개일 경우 10개 단위로 끊음 
//		  ex) 페이지 그룹이 아래와 같은 경우
//		  [1][2][3][4][5][6][7][8][9][10]
//		  페이지 그룹의 시작 페이지 [1]는 startpage에
//		  마지막 페이지 [10]는 endpage에 구함
//		  
//		  예로 1~10 페이지의 내용을 나타낼 대는 페이지 그룹은
//		  [1][2][3]...[10]으로 표시되고 
//		  11~20 페이지의 내용을 나타낼 때는 
//		  [11][12][13]...[20]까지 표시됨
//		 */
		int startpage = ((page - 1) / 10) * 10 + 1;
		//
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage) {
			endpage = maxpage;
		}
		
		request.setAttribute("page", page);// 현재 페이지 수
		request.setAttribute("maxpage", maxpage);// 현재 페이지 수

		request.setAttribute("startpage", startpage);// 현재 페이지에 표시할 첫 페이지 수
		request.setAttribute("endpage", endpage);// 현재 페이지에 표시할 끝 페이지 수

		request.setAttribute("listcount", listcount);
		request.setAttribute("wordList", wordList);

		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("index/index.jsp");
		return forward;
	}

}
