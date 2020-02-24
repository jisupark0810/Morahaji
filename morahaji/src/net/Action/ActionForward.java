package net.Action;

//ActionForward 클래스는 Action 인터페이스에서 명령을 수행하고 결과 값을
//가지고 포워딩 할때 사용되는 클래스 입니다.
//이 클래스는 Redirect 여부 값과 포워딩할 페이지의 위치를 가지고 있습니다.
//이 값들은 FrontController에서 ActionForward 클래스 타입으로 반환값을
//가져오면 그 값을 확인하여 해당하는 요청 페이지로 포워딩 처리를 합니다.
public class ActionForward {
	private boolean redirect = false;
	private String path = null;

	// property Redirect의 is 메소드
	public boolean isRedirect() {
		return redirect;
	}

	// property path의 get메소드
	public String getPath() {
		return path;
	}

	// property path의 set메소드
	public void setPath(String path) {
		this.path = path;
	}

	// property redirect의 set메소드
	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

}
