package contest.lab.gala.callback;

import contest.lab.gala.data.User;

public interface LoginCallback {
	public void didSuccessLogin(User user);
	public void didFailedLogin(String message);
}
