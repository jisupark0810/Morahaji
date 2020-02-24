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

		if (request.getParameter("word_tag") != null) { // �ؽ��±� ���� ���
			hashtagDAO.deleteHashtagWord(wordKey); // ���� ��ϵ� �ؽ��±� ����
			System.out.println("�ؽ��±� ���� : [" + request.getParameter("word_tag") + "]");
			String hashtags[] = request.getParameter("word_tag").split("#");
			for (int i = 0; i < hashtags.length; i++) {
				String hashtag = hashtags[i].trim();
				if (hashtag == null || hashtag.equals("") || hashtag.length() == 0) {
					continue;
				} else {
					boolean isHashtag = hashtagDAO.isHashtag(hashtag);
					System.out.println("hashtag �ִ�? " + isHashtag);
					if (isHashtag) { // �ؽ��±װ� �̹� ��ϵǾ� �ִ� ���
						int result = hashtagDAO.insertHashtagWord(hashtag, wordKey);
						if (result == -1)
							System.out.println("�ؽ��±� �Է� �� ���� �߻�");
					} else { // �ؽ��±װ� ��ϵ� �� ���� ���
						int hashtagKey = hashtagDAO.insertHashtag(hashtag);
						if (hashtagKey == -1)
							System.out.println("�ؽ��±� �Է� �� ���� �߻�");
						else {
							int result = hashtagDAO.insertHashtagWord(hashtag, wordKey);
							if (result == -1)
								System.out.println("�ؽ��±�-�ܾ� �Է� �� ���� �߻�");
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
