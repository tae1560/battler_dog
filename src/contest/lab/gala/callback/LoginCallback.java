package contest.lab.gala.callback;

public interface LoginCallback {
	public void didSuccessLogin();
	public void didFailedLogin(String message);
}
