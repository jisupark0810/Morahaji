package net.Action;

//ActionForward Ŭ������ Action �������̽����� ����� �����ϰ� ��� ����
//������ ������ �Ҷ� ���Ǵ� Ŭ���� �Դϴ�.
//�� Ŭ������ Redirect ���� ���� �������� �������� ��ġ�� ������ �ֽ��ϴ�.
//�� ������ FrontController���� ActionForward Ŭ���� Ÿ������ ��ȯ����
//�������� �� ���� Ȯ���Ͽ� �ش��ϴ� ��û �������� ������ ó���� �մϴ�.
public class ActionForward {
	private boolean redirect = false;
	private String path = null;

	// property Redirect�� is �޼ҵ�
	public boolean isRedirect() {
		return redirect;
	}

	// property path�� get�޼ҵ�
	public String getPath() {
		return path;
	}

	// property path�� set�޼ҵ�
	public void setPath(String path) {
		this.path = path;
	}

	// property redirect�� set�޼ҵ�
	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

}
