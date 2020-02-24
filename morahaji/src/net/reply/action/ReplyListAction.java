package net.reply.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;

import net.Action.Action;
import net.Action.ActionForward;
import net.reply.db.ReplyDAO;

public class ReplyListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReplyDAO dao = new ReplyDAO();
		int board_key = Integer.parseInt(request.getParameter("board_key"));
		JsonArray json = dao.getReplyList(board_key);
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("cache-control", "no-cache,no-store");
		PrintWriter out = response.getWriter();
		out.print(json);
		System.out.println(json);
		return null;

	}
}
