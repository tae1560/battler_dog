package contest.lab.gala.callback;

import contest.lab.gala.data.User;

public interface JoinCallback {
	public void didSuccessJoin(User user);
	public void didFailedJoin(String message);
}
