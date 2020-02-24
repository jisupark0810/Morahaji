package net.word.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.hashtag.db.HashTagDAO;
import net.word.db.WORD;
import net.word.db.WordDAO;

public class WordModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WORD w = new WORD();
		WordDAO wordDAO = new WordDAO();
		HashTagDAO hashtagDAO = new HashTagDAO();
		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");

		w.setWORD_KEY(Integer.parseInt(request.getParameter("word_key").toString()));
		w.setUSER_KEY(Integer.parseInt(session.getAttribute("userKey").toString()));
		w.setWORD_TITLE(request.getParameter("word_title"));
		w.setWORD_CONTENT(request.getParameter("word_content"));
		w.setWORD_EXSENTENCE(request.getParameter("word_example"));
		w.setWORD_GIF(request.getParameter("word_gif"));

		System.out.println("word_title = " + w.getWORD_TITLE());
		System.out.println("word_content = " + w.getWORD_CONTENT());
		System.out.println("word_gif = " + w.getWORD_GIF());
		int wordKey = wordDAO.update(w);

		if (request.getParameter("word_tag") != null) { // 해시태그 있을 경우
			hashtagDAO.deleteHashtagWord(wordKey); // 현재 등록된 해시태그 삭제
			System.out.println("해시태그 있음 : [" + request.getParameter("word_tag") + "]");
			String hashtags[] = request.getParameter("word_tag").split("#");
			for (int i = 0; i < hashtags.length; i++) {
				String hashtag = hashtags[i].trim();
				if (hashtag == null || hashtag.equals("") || hashtag.length() == 0) {
					continue;
				} else {
					boolean isHashtag = hashtagDAO.isHashtag(hashtag);
					System.out.println("hashtag 있니? " + isHashtag);
					if (isHashtag) { // 해시태그가 이미 등록되어 있는 경우
						int result = hashtagDAO.insertHashtagWord(hashtag, wordKey);
						if (result == -1)
							System.out.println("해시태그 입력 중 오류 발생");
					} else { // 해시태그가 등록된 적 없는 경우
						int hashtagKey = hashtagDAO.insertHashtag(hashtag);
						if (hashtagKey == -1)
							System.out.println("해시태그 입력 중 오류 발생");
						else {
							int result = hashtagDAO.insertHashtagWord(hashtag, wordKey);
							if (result == -1)
								System.out.println("해시태그-단어 입력 중 오류 발생");
						}
					}
				}
			}
		}

		ActionForward forward = new ActionForward();

		forward.setRedirect(true);
		forward.setPath("main.index");
		return forward;
	}
}
