package net.users.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.USERS;
import net.users.db.UserDAO;

public class updateViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();		
		UserDAO dao = new UserDAO();
		USERS u = new USERS();
		
		int userKey = Integer.parseInt(session.getAttribute("userKey").toString());
		u = dao.getDetailForUpdate(userKey);
		
		String profilePic = "img/profile.png";
		if(u.getUSER_PROFILEPHOTO() != null  && u.getUSER_PROFILEPHOTO().length() > 0) {
			profilePic = "boardupload/"+ u.getUSER_PROFILEPHOTO();
		}
		u.setUSER_PROFILEPHOTO(profilePic);
		System.out.println("수정 페이지 이동 성공");
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("userinfo", u);
		forward.setRedirect(false);
        forward.setPath("users.updateForm/index.jsp");
		return forward;
	}

}
