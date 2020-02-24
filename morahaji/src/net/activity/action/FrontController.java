package net.activity.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.Action.Action;
import net.Action.ActionForward;

@WebServlet("*.action")
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
		System.out.println("Activity RequestURI = " + RequestURI);

		// getContextPath() : ���ؽ�Ʈ ��� ��ȯ
		// contextPath�� "/JspProject"�� ��ȯ ��
		String contextPath = request.getContextPath();
		System.out.println("Activity contextPath = " + contextPath);

		// RequestURI���� ���ؽ�Ʈ ��� ���� ���� �ε��� ��ġ�� ���ں���
		// ������ ��ġ ���ڱ��� ����
		String command = RequestURI.substring(contextPath.length());
		System.out.println("Activity command = " + command);

		// �ʱ�ȭ
		ActionForward forward = null;
		Action action = null;
		if (command.equals("/countAdd.action")) {
			action = new AddCountProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/countRemove.action")) {
			action = new RemoveCountProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/reportAdd.action")) {
			action = new AddReportProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/reportRemove.action")) {
			action = new RemoveReportProcessAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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
