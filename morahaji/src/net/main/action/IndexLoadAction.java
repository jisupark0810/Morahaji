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
			hash_wordKeyList = hashtagDAO.getWordList(tag); // �ؽ��±׸� ������ �ִ� �ܾ��ϵ��� Ű���� ������
		} else {
			tag = null;
			session.removeAttribute("tag");
		}
		int page = 1;
		int limit = 5;

		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		System.out.println("�Ѿ�� ������ = " + page);
		System.out.println("�Ѿ�� �ܾ� = " + keyword);

		// �� ����Ʈ ��
		int listcount = 0;
		if (tag != null && tag.length() > 0) {
			listcount = hash_wordKeyList.size();
			wordList = wordDAO.getWordList(page, limit, userKey, hash_wordKeyList);
		} else {
			listcount = wordDAO.getListCount(keyword);
			wordList = wordDAO.getWordList(page, limit, userKey, keyword);
		}
//		/*
//		 �� ������ �� = DB�� ����� �� ����Ʈ�� �� + �� ���������� �����ִ� ������Ʒ �� -1) / �� ���������� �����ִ� ����Ʈ�� ��
//		 
//		 Ex)
//		 limit = 10�� ���
//		 DB�� ���� �� �� ����Ʈ�� ���� 0�̸� �� ������ ���� 0 
//		 DB�� ���� �� �� ����Ʈ�� ���� (1-10)�̸� �� ������ ���� 1 
//		 DB�� ���� �� �� ����Ʈ�� ���� (11-20)�̸� �� ������ ���� 2 
//		 DB�� ���� �� �� ����Ʈ�� ���� (21-30)�̸� �� ������ ���� 3 
//		 */

		int maxpage = (listcount + limit - 1) / limit;
		System.out.println("�� ������ �� = " + maxpage);

//		/*
//		  startpage : ���� ������ �׷쿡�� �� ó���� ǥ�õ� ������ ���� �ǹ�
//		  
//		  ������ �������� 30���� ��� 10�� ������ ���� 
//		  ex) ������ �׷��� �Ʒ��� ���� ���
//		  [1][2][3][4][5][6][7][8][9][10]
//		  ������ �׷��� ���� ������ [1]�� startpage��
//		  ������ ������ [10]�� endpage�� ����
//		  
//		  ���� 1~10 �������� ������ ��Ÿ�� ��� ������ �׷���
//		  [1][2][3]...[10]���� ǥ�õǰ� 
//		  11~20 �������� ������ ��Ÿ�� ���� 
//		  [11][12][13]...[20]���� ǥ�õ�
//		 */
		int startpage = ((page - 1) / 10) * 10 + 1;
		//
		int endpage = startpage + 10 - 1;

		if (endpage > maxpage) {
			endpage = maxpage;
		}
		
		request.setAttribute("page", page);// ���� ������ ��
		request.setAttribute("maxpage", maxpage);// ���� ������ ��

		request.setAttribute("startpage", startpage);// ���� �������� ǥ���� ù ������ ��
		request.setAttribute("endpage", endpage);// ���� �������� ǥ���� �� ������ ��

		request.setAttribute("listcount", listcount);
		request.setAttribute("wordList", wordList);

		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("index/index.jsp");
		return forward;
	}

}
