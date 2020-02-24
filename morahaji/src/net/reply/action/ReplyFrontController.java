// ���� (11/30) �׳� �������
package net.reply.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;
import net.reply.action.ReplyAddAction;
import net.reply.action.ReplyListAction;
import net.reply.action.ReplyReplyAddAction;

@WebServlet("*.rp")
public class ReplyFrontController extends HttpServlet {

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("utf-8");
		request.setCharacterEncoding("utf-8");
		/*
		 * ��û�� ��ü URL�߿��� ��Ʈ ��ȣ ���� ���� ������ ���ڿ����� ��ȯ�˴ϴ�. ��)
		 * http://localhost:8088/JspProject/login.net�� ��� "JspProject/login.net" ��ȯ�˴ϴ�.
		 */
		String RequestURI = request.getRequestURI();
		System.out.println("RequestURI = " + RequestURI);

		// get ContextPath() : ���ؽ�Ʈ ��ΰ� ��ȯ�˴ϴ�.
		// contextPath�� "/JspProject"�� ��ȯ�˴ϴ�.
		String contextPath = request.getContextPath();
		System.out.println("contextPath = " + contextPath);

		// RequestURI���� ���ؽ�Ʈ ��� ���� ���� �ε��� ��ġ�� ���ں���
		// ������ ��ġ ���ڱ��� �����մϴ�.
		// command�� "/login.net" ��ȯ�˴ϴ�.
		String command = RequestURI.substring(contextPath.length());
		System.out.println("command = " + command);

		// �ʱ�ȭ
		ActionForward forward = null;
		Action action = null;

		if (command.equals("/ReplyAdd.rp")) {
			action = new ReplyAddAction();// �������� ���� ��ĳ����
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/ReplyList.rp")) {
			action = new ReplyListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/ReplyReplyAdd.rp")) {
			action = new ReplyReplyAddAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (command.equals("/ReplyUpdate.rp")) {
			action = new ReplyUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}/*else if (command.equals("/CommentReplyAdd.bo")) {
			action = new ReplyReplyAdd();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} /*else if (command.equals("/CommentDelete.bo")) {
			action = new CommentDelete();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/CommentUpdate.bo")) {
			action = new CommentUpdate();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/

		if (forward != null) {
			if (forward.isRedirect()) { // �����̷�Ʈ �˴ϴ�.
				response.sendRedirect(forward.getPath());
			} else {// �������˴ϴ�.
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}

	private static final long serialVersionUID = 1L;

	public ReplyFrontController() {
		super();
	}

	// login.net => FrontController
	// doProcess(request,response)�޼��带 �����Ͽ� ��û�� GET����̵�
	// POST������� ���۵Ǿ� ���� ���� �޼��忡�� ��û�� ó���� �� �ֵ��� �Ͽ����ϴ�.
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("utf-8");
		request.setCharacterEncoding("utf-8");
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("utf-8");
		request.setCharacterEncoding("utf-8");
		doProcess(request, response);
	}

}
