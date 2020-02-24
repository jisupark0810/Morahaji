package net.users.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.Action.Action;
import net.Action.ActionForward;
import net.users.db.UserDAO;

public class GetUserProfilePic implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserDAO userDAO = new UserDAO();
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		int userKey = 0;
		if (session.getAttribute("userKey") != null)
			userKey = Integer.parseInt(session.getAttribute("userKey").toString());
		
//		request.setAttribute("userProfile", userDAO.getProfiles(userKey));

		response.getWriter().append(userDAO.getProfiles(userKey));

		return null;
	}

}
