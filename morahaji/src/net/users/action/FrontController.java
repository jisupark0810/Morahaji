package net.users.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;

@WebServlet("*.net")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontController() {
		super();
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * ��û�� ��ü URL �߿��� ��Ʈ ��ȣ �������� ������ ���ڿ����� ��ȯ�� Ex)
		 * http://localhost:8088/JspProject/login.net�� ��� "/JspProject/login.net" ��ȯ��
		 */
		String RequestURI = request.getRequestURI();
		System.out.println("RequestURI = " + RequestURI);

		// getContextPath() : ���ؽ�Ʈ ��� ��ȯ
		// contextPath�� "/JspProject"�� ��ȯ ��
		String contextPath = request.getContextPath();
		System.out.println("contextPath = " + contextPath);

		// RequestURI���� ���ؽ�Ʈ ��� ���� ���� �ε��� ��ġ�� ���ں���
		// ������ ��ġ ���ڱ��� ����
		String command = RequestURI.substring(contextPath.length());
		System.out.println("command = " + command);

		// �ʱ�ȭ
		ActionForward forward = null;
		Action action = null;
		if (command.equals("/login.net")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("users.login/index.jsp");
		} else if (command.equals("/join.net")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("users.join/index.jsp");
		} else if (command.equals("/findPw.net")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("users.password/index.jsp");
		} else if (command.equals("/mypage.net")) {
			forward = new ActionForward();
			forward.setRedirect(false);
			forward.setPath("users.info/mypage.jsp");
		} else if (command.equals("/idcheck.net")) {
			action = new IdCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/mypageLoad.net")) {
			action = new MypageLoadAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/emailcheck.net")) {
			action = new EmailCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/joinProcess.net")) {
			action = new JoinProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/loginProcess.net")) {
			action = new LoginProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/findPwProcess.net")) {
			action = new FindPwProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/logout.net")) {
			action = new LogoutAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/getUserProfilePic.net")) {
			action = new GetUserProfilePic();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/** ���������� ���� ���� - �ڼ��� */
		else if (command.equals("/update.net")) {
			action = new updateViewAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // update.net end, ���� ���� -> ���� ���� ����

		else if (command.equals("/updateProcessAction.net")) {
			action = new updateProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // updateProcesss.net end

		else if (command.equals("/updateEmailProcessAction.net")) {
			action = new updateEmailProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // updateEmailProcessAction.net end

		else if (command.equals("/updatePassProcessAction.net")) {
			action = new updatePassProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // updatePassProcessAction.net end

		else if (command.equals("/updateAgeProcessAction.net")) {
			action = new updateAgeProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // updateAgeProcessAction.net end

		else if (command.equals("/updatePhotoAction.net")) {
			action = new updatePhotoAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // updateAgeProcessAction.net end

		else if (command.equals("/member_delete.net")) {
			action = new memberDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // member_delete.net end, ȸ�� Ż��
		/** ���������� ���� �� - �ڼ��� */
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);

			}
		}

	}

	// doProcess(request, response) �޼��带 �����Ͽ� ��û�� GET ����̵�
	// POST ������� ���۵Ǿ� ���簰�� �޼��忡�� ��û�� ó���� �� �ֵ��� ��
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}
}
